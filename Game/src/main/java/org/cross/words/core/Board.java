package org.cross.words.core;

import org.cross.words.Utilities.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Board {

    private final ArrayList<String> words = new ArrayList<String>();
    private final ArrayList<Word> wordArrayList = new ArrayList<Word>();
    private static final int size = 100;

    public Board(String[] words) {
        setWords(words);
        Vector2D vector2DInit = new Vector2D(0, 0);
        createBoard(vector2DInit);
    }

    public void setWords(String[] words) {
        for (String word : words) {
            if (isWord(word)) {
                this.words.add(word.toUpperCase());
            }
        }
        //System.out.println(getWords());
    }

    public ArrayList<String> getWords() {
        return words;
    }

    private static boolean isWord(String text) {
        // Remove leading and trailing whitespace from the string
        text = text.trim();

        // Check if the string is empty
        if (text.isEmpty()) {
            return false;
        }

        // Check if the string contains only letters
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isLetter(text.charAt(i))) {
                return false;
            }
        }

        // If it reaches here, the string is a word
        return true;
    }

    private void createBoard(Vector2D vector2DInit) {
        char[][] board = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '0';
            }
        }

        insertWord(board, words.get(0), vector2DInit, Direction.HORIZONTAL);

        for (int i = 0; i < wordArrayList.size(); i++) {
            for (int j = 0; j < words.size(); j++) {

                String word1 = wordArrayList.get(i).word();
                String word2 = words.get(j);

                if (!Objects.equals(word1, word2)) {
                    ArrayList<Integer> positions1_2 = findPositionsOfMatchingCharacters(word1, word2);

                    int finalI = i;
                    positions1_2.forEach(position1_2 -> {
                        ArrayList<Integer> positions2_1 = findPositionsOfMatchingCharacters(word2, word1);
                        positions2_1.forEach(position2_1 -> {

                            boolean charEqualsChar = (wordArrayList.get(finalI).word().charAt(position1_2)) == (word2.charAt(position2_1));
                            if (charEqualsChar) {
                                //System.out.println(word2 +" com relaçao "+word1 +" pegou " + word2.substring(position2_1) + " e sua posição inicial é: " + word2.charAt(0) + " em " + (position1_2));

                                try {
                                    double x=wordArrayList.get(finalI).vector2DS().get(position1_2).x;
                                    double y=wordArrayList.get(finalI).vector2DS().get(position1_2).y;

                                    Vector2D vector2D = wordArrayList.get(finalI).vector2DS().get(position1_2);
                                    Vector2D vector2DH = new Vector2D(x-position2_1,y);
                                    Vector2D vector2DV = new Vector2D(x,y-position2_1);



                                    //System.out.println(Arrays.toString(vector2D.getX_Y()) + (checkTheWordOnTheBoard(board, word2.substring(position2_1), Direction.HORIZONTAL, vector2D)));
                                    //System.out.println(Arrays.toString(vector2D.getX_Y()) + checkTheWordOnTheBoard(board, word2.substring(position2_1), Direction.VERTICAL, vector2D));


                                    if (!(wordExists(wordArrayList, word2))){
                                        if(checkTheWordOnTheBoard(board, word2.substring(position2_1), Direction.HORIZONTAL, vector2D)){
                                            insertWord(board, word2, vector2DH, Direction.HORIZONTAL);
                                        }else if (checkTheWordOnTheBoard(board, word2.substring(position2_1), Direction.VERTICAL, vector2D)){
                                            insertWord(board, word2, vector2DV, Direction.VERTICAL);
                                        }
                                    }
                                }catch (Exception e){}
                            }

                            //if (checkTheWordOnTheBoard(board, word2, Direction.HORIZONTAL, ))

                        });

                    });
                }

            }
        }

        printBoard(board);
    }

    private static boolean checkTheWordOnTheBoard(char[][] matrix, String word, Direction direction, Vector2D vector2D) {
        int row = (int) vector2D.x;
        int column = (int) (vector2D.y);
        int wordLength = word.length();

        if (row < 0 || row >= Board.size || column < 0 || column >= size) {
            // Check if the starting position is within the matrix boundaries.
            return false;
        }

        if (direction == Direction.HORIZONTAL) {
            if (column + wordLength > size) {
                // The word doesn't fit horizontally from the specified position.
                return false;
            }

            for (int i = 0; i < wordLength; i++) {
                if (matrix[row][column + i] != '0' && matrix[row][column + i] != word.charAt(i)) {
                    // Check if the position already contains a non-zero char and doesn't match the word.
                    return false;
                }
            }
        } else if (direction == Direction.VERTICAL) {
            if (row + wordLength > Board.size) {
                // The word doesn't fit vertically from the specified position.
                return false;
            }

            for (int i = 0; i < wordLength; i++) {
                if (matrix[row + i][column] != '0' && matrix[row + i][column] != word.charAt(i)) {
                    // Check if the position already contains a non-zero char and doesn't match the word.
                    return false;
                }
            }
        } else {
            // Invalid direction.
            return false;
        }

        return true;
    }

    private void insertWord(char[][] matrix, String word, Vector2D vector2D, Direction direction) {
        int row = (int) vector2D.x;
        int column = (int) (vector2D.y);
        int length = word.length();

        ArrayList<Vector2D> vector2DS = new ArrayList<Vector2D>();

        if (direction == Direction.HORIZONTAL) {
            // Check if the word fits horizontally
            if (column + length > matrix[0].length) {
                throw new IllegalArgumentException("The word doesn't fit at the specified position and direction.");
            }

            for (int i = 0; i < length; i++) {
                matrix[row][column + i] = word.charAt(i);
                vector2DS.add(new Vector2D(row, column + i));
            }
        } else if (direction == Direction.VERTICAL) {
            // Check if the word fits vertically
            if (row + length > matrix.length) {
                throw new IllegalArgumentException("The word doesn't fit at the specified position and direction.");
            }

            for (int i = 0; i < length; i++) {
                matrix[row + i][column] = word.charAt(i);
                vector2DS.add(new Vector2D(row + i, column));
            }
        } else {
            throw new IllegalArgumentException("Invalid direction.");
        }
        this.wordArrayList.add(new Word(word, vector2DS));
    }

    public void printBoard(char[][] matrix) {
        boolean[] printRow = new boolean[matrix.length];
        boolean[] printColumn = new boolean[matrix[0].length];

        // Verificar quais linhas e colunas devem ser impressas
        for (int i = 0; i < matrix.length; i++) {
            printRow[i] = false;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != '0') {
                    printRow[i] = true;
                    printColumn[j] = true;
                }
            }
        }

        // Imprimir o tabuleiro
        for (int i = 0; i < matrix.length; i++) {
            if (printRow[i]) {
                for (int j = 0; j < matrix[i].length; j++) {
                    if (printColumn[j]) {
                        if (matrix[i][j] == '0') {
                            System.out.print("  ");
                        } else {
                            System.out.print(matrix[i][j] + " ");
                        }
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
        }
    }

    private static ArrayList<Integer> findPositionsOfMatchingCharacters(String word1, String word2) {

        ArrayList<Integer> positions = new ArrayList<>();

        for (int i = 0; i < word1.length(); i++) {
            for (int j = 0; j < word2.length(); j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    positions.add(i);
                }
            }
        }

        HashSet<Integer> elementosUnicos = new HashSet<>();

        positions.removeIf(integer -> !elementosUnicos.add(integer));
        return positions;
    }

    public static boolean wordExists(ArrayList<Word> wordList, String wordToFind) {
        ArrayList<String> wordArrayList = new ArrayList<String>();

        wordList.forEach(word -> {
            wordArrayList.add(word.word());
        });

        //wordArrayList.forEach(System.out::println);

        return wordArrayList.contains(wordToFind);
    }

}