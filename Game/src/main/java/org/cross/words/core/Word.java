package org.cross.words.core;

import org.cross.words.Utilities.Vector2D;

import java.util.ArrayList;

public record Word(String word, ArrayList<Vector2D> vector2DS) {

    public void printWordObject() {
        System.out.print(word() + ": ");
        for (Vector2D vector : vector2DS) {
            double[] components = vector.getX_Y();
            System.out.print("(" + components[0] + ", " + components[1] + ") ");
        }
        System.out.println(); // Print a newline after all vectors are printed.
    }

}
