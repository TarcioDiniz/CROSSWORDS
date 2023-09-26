package org.cross.words.core.controller;


import org.cross.words.Utilities.Difficulty;
import org.cross.words.core.match.Matches;
import org.cross.words.core.matrix.Matrix;
import org.cross.words.core.matrix.MatrixState;
import org.cross.words.core.word.Word;
import org.cross.words.design.MatrixDesign;
import org.cross.words.design.PositionValue;

import java.util.ArrayList;
import java.util.List;
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
        List<PositionValue> positionValues = new ArrayList<>();
        var WordsEntered = matrixState.getCurrentState().getWordsEntered();

        for (int i = 0; i < WordsEntered.size(); i++) {
            var word = WordsEntered.get(i);
            var positionWord = word.getVector2DArrayList().get(0);
            PositionValue positionValue = new PositionValue((int) positionWord.x, (int) positionWord.y, i);
            positionValues.add(positionValue);
        }

        var matrixInitCopy = Matrix.copyMatrixChar(matrixInit.getMatrix());
        Matrix.replaceNonZeroCharsWithSpace(matrixInitCopy, Difficulty.EASY);

        MatrixDesign.printBoard(matrixInitCopy, positionValues);




    }

    public MatrixState getMatrixState() {
        return matrixState;
    }

    public Matches getMatches() {
        return matches;
    }
}
