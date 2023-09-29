package org.cross.words.design;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatrixDesign {
    public static void printBoard(char[][] matrix, List<PositionValue> positions) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        boolean[] printRow = new boolean[matrix.length];
        boolean[] printColumn = new boolean[matrix[0].length];
        ArrayList<Integer[]> positionAdd = new ArrayList<>();
        var ref_Pst = new Object() {
            PositionValue positionValueExtra;
        };

        // Verifica quais linhas e colunas devem ser impressas
        for (int i = 0; i < matrix.length; i++) {
            printRow[i] = false;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != '0') {
                    printRow[i] = true;
                    printColumn[j] = true;
                }
            }
        }
        int ultimoColumn = 0;
        for (int j = 0; j < numCols; j++) {
            if (printColumn[j]) {
                if (j < numCols - 1) {
                    ultimoColumn = j;
                }
            }
        }

        // Imprime a moldura superior
        System.out.print(Colors.WHITE_BOLD + Characters.ulcorner.getValue());
        // Dentro do loop para imprimir a moldura superior
        for (int j = 0; j < numCols; j++) {
            if (printColumn[j]) {
                System.out.print(Colors.WHITE_BOLD + Characters.hline.getValue() + Characters.hline.getValue() + Characters.hline.getValue());
                if (j == ultimoColumn) {
                    System.out.print(Colors.WHITE_BOLD + Characters.urcorner.getValue());
                } else if (j < numCols - 1) {
                    System.out.print(Colors.WHITE_BOLD + Characters.ttee.getValue());
                }
            }
        }
        System.out.println();

        for (int j = 0; j < numCols; j++) {
            if (printColumn[j]) {

                if (ref_Pst.positionValueExtra != null) {
                    System.out.print(Colors.RESET + SmallFontConverter.convertToSmallFont(ref_Pst.positionValueExtra.getValue()) + "   ");
                    ref_Pst.positionValueExtra = null;
                } else {
                    System.out.print(Colors.WHITE_BOLD + Characters.vline.getValue() + "   ");
                }


            }
        }
        System.out.print(Colors.WHITE_BOLD + Characters.vline.getValue());
        System.out.println();

        // Imprime o conteúdo da matriz com a moldura lateral
        for (int i = 0; i < numRows; i++) {
            if (printRow[i]) {
                if (i < numRows - 1) {

                    int ultimoColumn2 = 0;
                    for (int j = 0; j < numCols; j++) {
                        if (printColumn[j]) {
                            if (j < numCols - 1) {
                                ultimoColumn2 = j;
                            }
                        }
                    }

                    // Imprime a moldura intermediária
                    System.out.print(Colors.WHITE_BOLD + Characters.ltee.getValue());


                    for (int j = 0; j < numCols; j++) {
                        if (printColumn[j]) {

                            int finalI = i;
                            int finalJ = j;
                            var ref = new Object() {
                                boolean normal = true;
                            };
                            positions.forEach(positionValue -> {
                                if (finalI == positionValue.getRow() && finalJ == positionValue.getColumn()) {
                                    Integer[] position = {positionValue.getRow(), positionValue.getColumn()};
                                    if (!containsArray(positionAdd, position)) {
                                        ref.normal = false;
                                        if (positionValue.getValue() > 9) {
                                            System.out.print(Colors.RESET + SmallFontConverter.convertToSmallFont(positionValue.getValue()) + Colors.WHITE_BOLD + Characters.hline.getValue());
                                        } else {
                                            System.out.print(Colors.RESET + SmallFontConverter.convertToSmallFont(positionValue.getValue()) + Colors.WHITE_BOLD + Characters.hline.getValue() + Characters.hline.getValue());
                                        }

                                        positionAdd.add(position);
                                    } else {
                                        ref_Pst.positionValueExtra = positionValue;
                                    }

                                }
                            });

                            if (ref.normal) {
                                System.out.print(Colors.WHITE_BOLD + Characters.hline.getValue() + Characters.hline.getValue() + Characters.hline.getValue());
                            }

                            if (j == ultimoColumn2) {
                                System.out.print(Colors.WHITE_BOLD + Characters.vline.getValue());
                            } else if (j < numCols - 1) {
                                System.out.print(Colors.WHITE_BOLD + Characters.bigplus.getValue());

                            }
                        }
                    }
                    System.out.println(" ");
                }

                System.out.print(Colors.WHITE_BOLD + Characters.vline.getValue());
                for (int j = 0; j < numCols; j++) {
                    if (printColumn[j]) {
                        if (matrix[i][j] == '0') {
                            // Use três espaços para centralizar no meio do quadrado
                            System.out.print("   ");
                            //System.out.print(" "+ Characters.fullblock.getValue()+" ");
                        } else {
                            // Use dois espaços à esquerda para centralizar no meio do quadrado
                            System.out.print(" " + Colors.RESET + matrix[i][j] + " ");
                        }
                        if (j < numCols - 1) {
                            System.out.print(Colors.WHITE_BOLD + Characters.vline.getValue());
                        }
                    }
                }

                System.out.println();

            }
        }
        System.out.print(Colors.WHITE_BOLD + Characters.llcorner.getValue());
        // Dentro do loop para imprimir a moldura inferior
        for (int j = 0; j < numCols; j++) {
            if (printColumn[j]) {
                System.out.print(Colors.WHITE_BOLD + Characters.hline.getValue() + Characters.hline.getValue() + Characters.hline.getValue());
                if (j == ultimoColumn) {
                    System.out.print(Colors.WHITE_BOLD + Characters.lrcorner.getValue());
                } else if (j < numCols - 1) {
                    System.out.print(Colors.WHITE_BOLD + Characters.btee.getValue());
                }
            }
        }
        System.out.println(Colors.RESET);

    }

    public static boolean containsArray(ArrayList<Integer[]> list, Integer[] targetArray) {
        for (Integer[] arr : list) {
            if (Arrays.equals(arr, targetArray)) {
                return true;
            }
        }
        return false;
    }

}
