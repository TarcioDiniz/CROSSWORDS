package org.cross.words.core;


import org.cross.words.core.match.Matches;
import org.cross.words.core.matrix.Matrix;
import org.cross.words.core.matrix.MatrixState;
import org.cross.words.core.word.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Controller {

    private Matches matches;
    private final MatrixState matrixState = new MatrixState();
    private static Controller instance;

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    private Controller() {
    }

    public void init(ArrayList<Word> newWords) {
        Controller controller = getInstance();
        controller.matches = new Matches(newWords);
        controllerInit();

    }

    private void controllerInit() {

        Matrix matrixInit = new Matrix();
        matrixInit.createMatrix();
        matrixInit.build(matches.getWordMaxSize());
        matrixState.setState(matrixInit);


        matches.getWords().forEach(word -> {
            matches.getMatchIndices().forEach(matchIndex -> {
                if (Objects.equals(word, matchIndex.getWord1())) {
                    Matrix matrix = new Matrix();
                    matrix.createMatrix(matrixState.getCurrentState());
                    if (matrix.build(matchIndex.getWord1())) {
                        matrixState.setState(matrix);
                    }
                }

            });
        });

        Matrix.printBoard(matrixState.getCurrentState().getMatrix());

//       matrixState
//               .getCurrentState()
//               .getWordsEntered()
//                .get(0)
//               .getVector2DArrayList()
//                .forEach(vector2D -> {
//                    System.out.println(Arrays.toString(vector2D.getX_Y()));
//                });


    }

    public MatrixState getMatrixState() {
        return matrixState;
    }

    public Matches getMatches() {
        return matches;
    }
}
