package org.cross.words.core.matrix;

import org.cross.words.Utilities.Difficulty;
import org.cross.words.Utilities.Direction;
import org.cross.words.Utilities.Vector2D;
import org.cross.words.core.controller.Controller;
import org.cross.words.core.word.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 *
 * Class created to generate the board. in it, we can see the behavior
 * using the overloaded method, of which there are 2 ways to create a matrix:
 * 1° or the matrix does not exist.
 * 2° or there is, and you have to pass the matrix you already have.
 *
 * In your next methods we see a structure and logic for crossing words.
 *
 */

public class Matrix {
    private static final int size = Controller.getInstance().getMatches().getMaxNumberOfLettersInWord() + 100;
    private ArrayList<Word> wordsEntered = new ArrayList<>();
    private char[][] matrix = new char[size][size];
    private boolean inserted = false;

    public void createMatrix() {
        for (char[] row : matrix) {
            Arrays.fill(row, '0');
        }
    }

    public void createMatrix(Matrix newMatrix) {
        this.matrix = newMatrix.getMatrix();
        this.wordsEntered = newMatrix.wordsEntered;
    }

    public boolean build(Word word) {
        //System.out.println(wordsEntered.size());

        if (getWordsEntered().isEmpty()) {
            Vector2D vector2D = new Vector2D((double) size / 2, word.getWord().length());
            insertWord(word, vector2D, Direction.VERTICAL);
            wordsEntered.add(word);
            return true;
        } else if (!checkString(word.getWord())) {
            checkInsertWordInMatrix(word);
            wordsEntered.add(word);
        }


        return false;

    }

    private void checkInsertWordInMatrix(Word word) {

        var words = Controller.getInstance().getMatches().getWords();
        var matchIndices = Controller.getInstance().getMatches().getMatchIndices();

        matchIndices.forEach(matchIndex -> {
            var word1 = matchIndex.getWord1();
            var word2 = matchIndex.getWord2();
            var indices = matchIndex.getIndex();

            wordsEntered.forEach(word3 -> {

                if (word3.getWord().equals(word1.getWord())) {

                    if (word.getWord().equals(word2.getWord())) {

                        //System.out.println(word3.getWord() + " > " + word2.getWord());

                        var direction1 = Direction.HORIZONTAL;
                        var direction2 = Direction.VERTICAL;

                        indices.forEach(integer -> {

                            if (!inserted) {
                                var vector2DArrayList = word1.getVector2DArrayList();
                                if (vector2DArrayList != null) {
                                    ArrayList<Vector2D> vector2DS = new ArrayList<>();
                                    var centralPosition = vector2DArrayList.get(integer);
                                    var centralChar = word1.getWord().charAt(integer);

                                    var calculatePositions1 = calculatePositions(copyMatrixChar(matrix), word2.getWord(), (int) centralPosition.x, (int) centralPosition.y, direction1);
                                    var calculatePositions2 = calculatePositions(copyMatrixChar(matrix), word2.getWord(), (int) centralPosition.x, (int) centralPosition.y, direction2);

                                    if (isPositionValid(calculatePositions1, centralPosition.getX_Y())) {
                                        if (calculatePositions1.size() == word2.getSize()) {
                                            for (int i = 0; i < word2.getSize(); i++) {
                                                var row = (int) calculatePositions1.get(i)[0];
                                                var colum = (int) calculatePositions1.get(i)[1];
                                                var _char = word2.getWord().charAt(i);
                                                insertChar(row, colum, _char);

                                                Vector2D vector2D = new Vector2D(row, colum);
                                                vector2DS.add(vector2D);
                                            }

                                            word.setVector2DArrayList(vector2DS);
                                            inserted = true;
                                        }
                                    } else if (isPositionValid(calculatePositions2, centralPosition.getX_Y())) {
                                        if (calculatePositions2.size() == word2.getSize()) {
                                            for (int i = 0; i < word2.getSize(); i++) {
                                                var row = (int) calculatePositions2.get(i)[0];
                                                var colum = (int) calculatePositions2.get(i)[1];
                                                var _char = word2.getWord().charAt(i);
                                                insertChar(row, colum, _char);

                                                Vector2D vector2D = new Vector2D(row, colum);
                                                vector2DS.add(vector2D);
                                            }

                                            word.setVector2DArrayList(vector2DS);
                                            inserted = true;
                                        }
                                    }
                                }
                            }

                        });

                    }

                }

            });

        });

    }

    public static List<int[]> calculatePositions(char[][] matrix, String word, int start_i, int start_j, Direction direction) {
        char matchChar = matrix[start_i][start_j];
        List<int[]> positions = new ArrayList<>();
        int matchIdx = word.indexOf(matchChar);
        if (matchIdx == -1) {
            return positions;  // O caractere não está na palavra
        }
        for (int idx = 0; idx < word.length(); idx++) {
            int i = start_i + (direction == Direction.VERTICAL ? idx - matchIdx : 0);
            int j = start_j + (direction == Direction.HORIZONTAL ? idx - matchIdx : 0);
            if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length) {
                matrix[i][j] = word.charAt(idx);
                positions.add(new int[]{i, j});
            }
        }
        return positions;
    }

    public boolean isPositionValid(List<int[]> positions, double[] centralPosition) {

        var ref = new Object() {
            boolean valid = true;
        };

        var centralChar = matrix[(int) centralPosition[0]][(int) centralPosition[1]];


        positions.forEach(position -> {
            int row = (int) position[0];
            int col = (int) position[1];

            if (row < 0 || row >= matrix.length || col < 0 || col >= matrix.length) {
                ref.valid = false;
            }


            if (matrix[row][col] != '0') {
                if (row == centralPosition[0] && col == centralPosition[1]) {
                    if (!(matrix[row][col] == centralChar)) {
                        ref.valid = false;
                    }
                } else {
                    ref.valid = false;
                }
            }

        });

        return ref.valid;
    }

    private void insertWord(Word word, Vector2D vector2D, Direction direction) {
        int row = (int) vector2D.x;
        int column = (int) (vector2D.y);
        int length = word.getSize();
        String wordString = word.getWord();

        ArrayList<Vector2D> vector2DS = new ArrayList<>();

        if (direction == Direction.HORIZONTAL) {
            // Check if the word fits horizontally
            if (column + length > matrix[0].length) {
                throw new IllegalArgumentException("The word doesn't fit at the specified position and direction.");
            }

            for (int i = 0; i < length; i++) {
                matrix[row][column + i] = wordString.charAt(i);
                vector2DS.add(new Vector2D(row, column + i));
            }
        } else if (direction == Direction.VERTICAL) {
            // Check if the word fits vertically
            if (row + length > matrix.length) {
                throw new IllegalArgumentException("The word doesn't fit at the specified position and direction.");
            }

            for (int i = 0; i < length; i++) {
                matrix[row + i][column] = wordString.charAt(i);
                vector2DS.add(new Vector2D(row + i, column));
            }
        } else {
            throw new IllegalArgumentException("Invalid direction.");
        }

        word.setVector2DArrayList(vector2DS);
    }

//    public static void printBoard(char[][] matrix) {
//        boolean[] printRow = new boolean[matrix.length];
//        boolean[] printColumn = new boolean[matrix[0].length];
//
//        for (int i = 0; i < matrix.length; i++) {
//            printRow[i] = false;
//            for (int j = 0; j < matrix[i].length; j++) {
//                if (matrix[i][j] != '0') {
//                    printRow[i] = true;
//                    printColumn[j] = true;
//                }
//            }
//        }
//
//        for (int i = 0; i < matrix.length; i++) {
//            if (printRow[i]) {
//                for (int j = 0; j < matrix[i].length; j++) {
//                    if (printColumn[j]) {
//                        if (matrix[i][j] == '0') {
//                            System.out.print("  ");
//                        } else {
//                            System.out.print(matrix[i][j] + " ");
//                        }
//                    }
//                }
//                System.out.println();
//            }
//        }
//    }

    public char[][] getMatrix() {
        return matrix;
    }

    public ArrayList<Word> getWordsEntered() {
        return wordsEntered;
    }

    private boolean checkString(String searchString) {
        for (Word word : wordsEntered) {
            if (word.getWord() == searchString) {
                return true;
            }
        }
        return false;
    }

    public void insertChar(int row, int column, char character) {
        if (row >= 0 && row < matrix.length && column >= 0 && column < matrix[0].length) {
            // Check if the position is within the bounds of the matrix
            matrix[row][column] = character;
        } else {
            System.out.println("Invalid position. Out of bounds of the matrix.");
        }
    }

    public static char[][] copyMatrixChar(char[][] matrizOriginal) {
        int linhas = matrizOriginal.length;
        int colunas = matrizOriginal[0].length;

        char[][] novaMatriz = new char[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                novaMatriz[i][j] = matrizOriginal[i][j];
            }
        }

        return novaMatriz;
    }

    public static void replaceNonZeroCharsWithSpace(char[][] matrix, Difficulty difficulty) {
        // Percorre a matriz
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                // Verifica se o caractere não é '0'
                if (matrix[i][j] != '0') {

                    if (difficulty == Difficulty.EASY) {
                        if (!Word.isVowel(matrix[i][j])) {
                            matrix[i][j] = ' ';
                        }
                    }


                }
            }
        }
    }

}
