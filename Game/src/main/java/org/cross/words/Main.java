package org.cross.words;


import org.cross.words.core.controller.Controller;
import org.cross.words.core.controller.GameController;
import org.cross.words.settings.Settings;
import org.cross.words.system.GPT.ChatGPT;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        GameController.getInstance().start();

    }
}


