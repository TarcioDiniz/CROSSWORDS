package org.cross.words.Utilities;

public class Vector2D {

    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double[] getX_Y(){
        return new double[]{this.x, this.y};
    }
}
