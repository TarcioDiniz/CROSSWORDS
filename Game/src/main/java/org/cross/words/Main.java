package org.cross.words;


import org.cross.words.core.controller.GameController;

import java.io.IOException;

/*
 *
 * The Game actually starts in an instance of the GameController, through the "start()" method.
 * In the Main class, it is important that the Main method has throws Exception,
 * signature of the method with the possible exception that it may throw.
 *
 */

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        GameController.getInstance().start();
    }
}


