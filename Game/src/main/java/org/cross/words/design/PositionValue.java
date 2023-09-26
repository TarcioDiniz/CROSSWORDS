package org.cross.words.design;

public class PositionValue {
    private final int row;
    private final int column;
    private final int value;

    public PositionValue(int row, int column, int value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getValue() {
        return value;
    }
}

