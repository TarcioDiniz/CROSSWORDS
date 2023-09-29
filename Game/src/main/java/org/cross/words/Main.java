package org.cross.words;


import org.cross.words.core.controller.GameController;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        GameController.getInstance().start();
    }
}


