package org.cross.words.core.matrix;

import java.util.ArrayList;

public class MatrixState {
    ArrayList<Matrix> matrices;
    private int currentIndex;

    public MatrixState() {
        matrices = new ArrayList<>();
        currentIndex = -1;
    }

    public void setState(Matrix matrix) {
        this.matrices.add(matrix);
        this.currentIndex = matrices.size() - 1;
    }

    public Matrix getCurrentState() {
        if (currentIndex >= 0 && currentIndex < matrices.size()) {
            return matrices.get(currentIndex);
        } else {
            return null;
        }
    }

    public Matrix getPreviousState() {
        if (currentIndex > 0) {
            currentIndex--;
            return matrices.get(currentIndex);
        } else {
            return null;
        }
    }

    public Matrix getNextState() {
        if (currentIndex < matrices.size() - 1) {
            currentIndex++;
            return matrices.get(currentIndex);
        } else {
            return null;
        }
    }

    public int getStateNumber() {
        return this.matrices.size();
    }
}
