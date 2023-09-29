package org.cross.words.core.controller;

import org.cross.words.Utilities.InputUtil;
import org.cross.words.core.match.Matches;
import org.cross.words.core.word.Word;
import org.cross.words.design.Colors;
import org.cross.words.design.MatrixDesign;
import org.cross.words.settings.Settings;
import org.cross.words.system.GPT.ChatGPT;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GameController {

    private static GameController instance = null;
    private final ChatGPT chatGPT;
    private ArrayList<Word> words;

    private GameController() {
        Settings settings = new Settings();
        String apiKey = settings.getTokenGPT();
        if (apiKey == null) {
            System.out.println("Não foi possível obter uma API_KEY válida.");
        }
        this.chatGPT = new ChatGPT(apiKey);
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void start() throws IOException, InterruptedException {
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        System.out.println("Seja Bem-Vindo!");
        String theme = InputUtil.getInputString("Para Gerar sua Palavra Cruzada escolha um tema de sua preferência: ");

        Thread thread = new Thread(() -> {
            this.words = chatGPT.Search(theme, 5);
            Controller.getInstance().init(words);
        });

        thread.start();

        while (thread.isAlive()) {
            displayLoadingAnimation(terminal);
        }
        System.out.println(Colors.RESET);

        do {
            displayMatrix();
            displayHints();
            yourInput();
        } while (!theEndGame());

        InputUtil.closeScanner();

        clearConsole();
        displayMatrix();
        System.out.println(Colors.GREEN_BOLD + "OBRIGADO POR JOGAR O PALAVRA CRUZADA.");
    }

    private void yourInput() {
        var input = InputUtil.getInputInt(Colors.WHITE_BOLD + "Escolha de 0 a " + (words.size() - 1) + ": ");
        while (input < 0 || input > (words.size() - 1)) {
            input = InputUtil.getInputInt(Colors.WHITE_BOLD + "Escolha de 0 a " + (words.size() - 1) + ": ");
        }
        var input2 = InputUtil.getInputString(Colors.WHITE_BOLD + "Digite uma letra ou escreva a palavra: ").toUpperCase();
        if (input2.length() == 1) {
            if (words.get(input).containsChar(input2.charAt(0))) {

                var positions = Matches.findPositionsOfMatchingCharacters(words.get(input).getWord(), String.valueOf(input2.charAt(0)));
                var vectors2d = words.get(input).getVector2DArrayList();

                positions.forEach(integer -> {
                    var row = (int) vectors2d.get(integer).x;
                    var colum = (int) vectors2d.get(integer).y;
                    char charMatrix = Controller.getInstance().getMatrixState().getCurrentState().getMatrix()[row][colum];
                    Controller.getInstance().getMatrixInitCopy()[row][colum] = charMatrix;

                });

            } else {
                System.out.println(Colors.RED + "Não foi possivel adcionar.");
            }
        } else if (Objects.equals(words.get(input).getWord(), input2)) {
            var vectors2d = words.get(input).getVector2DArrayList();
            vectors2d.forEach(vector2D -> {
                var row = (int) vector2D.x;
                var colum = (int) vector2D.y;
                char charMatrix = Controller.getInstance().getMatrixState().getCurrentState().getMatrix()[row][colum];
                Controller.getInstance().getMatrixInitCopy()[row][colum] = charMatrix;
            });
        } else {
            System.out.println(Colors.RED + "Não foi possivel adcionar.");
        }

        System.out.print(Colors.RESET);

    }

    private static void displayLoadingAnimation(Terminal terminal) throws InterruptedException, IOException {
        for (int i = 0; i < 3; i++) {
            terminal.writer().print("\r" + Colors.YELLOW + "Gerando" + ".".repeat(i + 1));
            terminal.flush();
            Thread.sleep(1000); // Espera por um segundo
        }

    }

    private void displayHints() {
        System.out.println("Dicas: ");
        for (int i = 0; i < words.size(); i++) {
            if (checkWordInGameFromMatrixCopy(words.get(i))) {
                System.out.println(Colors.GREEN_UNDERLINED + "" + i + " -> " + words.get(i).getQuestion() + " (" + words.get(i).getSize() + " Letras)." + Colors.RESET);
            } else {
                System.out.println(i + " -> " + words.get(i).getQuestion() + " (" + words.get(i).getSize() + " Letras).");
            }
        }
    }

    private void displayMatrix() {
        var matrix = Controller.getInstance().getMatrixInitCopy();
        var position = Controller.getInstance().getPositionValues();
        MatrixDesign.printBoard(matrix, position);
    }


    public static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private boolean theEndGame() {
        var originalMatrix = Controller.getInstance().getMatrixState().getCurrentState().getMatrix();
        var copiedMatrix = Controller.getInstance().getMatrixInitCopy();
        // Check if the matrices have the same number of rows and columns
        if (originalMatrix.length != copiedMatrix.length || originalMatrix[0].length != copiedMatrix[0].length) {
            return false;
        }

        // Compare each element of the matrices
        for (int i = 0; i < originalMatrix.length; i++) {
            for (int j = 0; j < originalMatrix[0].length; j++) {
                if (originalMatrix[i][j] != copiedMatrix[i][j]) {
                    return false; // Different elements found, matrices are not equal
                }
            }
        }

        // If no different elements are found, the matrices are equal
        return true;
    }

    private boolean checkWordInGameFromMatrixCopy(Word word) {
        var matrix = Controller.getInstance().getMatrixInitCopy();
        var positions = word.getVector2DArrayList();


        if (matrix == null || positions == null || word == null) {
            return false;
        }

        if (positions.size() != word.getWord().length()) {
            return false;
        }

        for (int i = 0; i < positions.size(); i++) {
            int row = (int) positions.get(i).x;
            int col = (int) positions.get(i).y;

            if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length) {
                return false; // Check if the position is within the matrix bounds
            }

            if (matrix[row][col] != word.getWord().charAt(i)) {
                return false; // Check if the character at the matrix position matches the character in the word
            }
        }

        return true;
    }

}
