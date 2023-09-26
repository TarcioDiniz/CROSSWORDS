package org.cross.words.design;

import java.util.List;

public class MatrixDesign {
    public static void printBoard(char[][] matrix, List<PositionValue> positions) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        boolean[] printRow = new boolean[matrix.length];
        boolean[] printColumn = new boolean[matrix[0].length];

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
        System.out.print(Characters.ulcorner.getValue());
        // Dentro do loop para imprimir a moldura superior
        for (int j = 0; j < numCols; j++) {
            if (printColumn[j]) {
                System.out.print(Characters.hline.getValue() + Characters.hline.getValue() + Characters.hline.getValue());
                if (j == ultimoColumn) {
                    System.out.print(Characters.urcorner.getValue());
                } else if (j < numCols - 1) {
                    System.out.print(Characters.ttee.getValue());
                }
            }
        }
        System.out.println();

        for (int j = 0; j < numCols; j++) {
            if (printColumn[j]) {
                System.out.print(Characters.vline.getValue() + "   ");
            }
        }
        System.out.print(Characters.vline.getValue());
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
                    System.out.print(Characters.ltee.getValue());


                    for (int j = 0; j < numCols; j++) {
                        if (printColumn[j]) {

                            int finalI = i;
                            int finalJ = j;
                            var ref = new Object() {
                                boolean normal = true;
                            };
                            positions.forEach(positionValue -> {
                                if (finalI == positionValue.getRow() && finalJ == positionValue.getColumn()) {
                                    ref.normal = false;
                                    if (positionValue.getValue() > 9) {
                                        System.out.print(SmallFontConverter.convertToSmallFont(positionValue.getValue()) + Characters.hline.getValue());
                                    } else {
                                        System.out.print(SmallFontConverter.convertToSmallFont(positionValue.getValue()) + Characters.hline.getValue() + Characters.hline.getValue());
                                    }
                                }
                            });

                            if (ref.normal) {
                                System.out.print(Characters.hline.getValue() + Characters.hline.getValue() + Characters.hline.getValue());
                            }

                            if (j == ultimoColumn2) {
                                System.out.print(Characters.vline.getValue());
                            } else if (j < numCols - 1) {
                                System.out.print(Characters.bigplus.getValue());

                            }
                        }
                    }
                    System.out.println(" ");
                }
                System.out.print(Characters.vline.getValue());
                for (int j = 0; j < numCols; j++) {
                    if (printColumn[j]) {
                        if (matrix[i][j] == '0') {
                            // Use três espaços para centralizar no meio do quadrado
                            System.out.print("   ");
                            //System.out.print(" "+ Characters.fullblock.getValue()+" ");
                        } else {
                            // Use dois espaços à esquerda para centralizar no meio do quadrado
                            System.out.print(" " + matrix[i][j] + " ");
                        }
                        if (j < numCols - 1) {
                            System.out.print(Characters.vline.getValue());
                        }
                    }
                }

                System.out.println();

            }
        }
        System.out.print(Characters.llcorner.getValue());
        // Dentro do loop para imprimir a moldura inferior
        for (int j = 0; j < numCols; j++) {
            if (printColumn[j]) {
                System.out.print(Characters.hline.getValue() + Characters.hline.getValue() + Characters.hline.getValue());
                if (j == ultimoColumn) {
                    System.out.print(Characters.lrcorner.getValue());
                } else if (j < numCols - 1) {
                    System.out.print(Characters.btee.getValue());
                }
            }
        }
        System.out.println();

    }
}
