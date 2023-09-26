package org.cross.words;


import org.cross.words.core.controller.Controller;
import org.cross.words.settings.Settings;
import org.cross.words.system.GPT.ChatGPT;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        Settings settings = new Settings();
        String apiKey = settings.getTokenGPT();
        if (apiKey != null) {
            // Use a apiKey para fazer chamadas à API do Chat GPT
           //System.out.println("API_KEY: " + apiKey);
        } else {
            System.out.println("Não foi possível obter uma API_KEY válida.");
        }

        ChatGPT chatGPT = new ChatGPT(apiKey);
        var words = chatGPT.Search("futebol brasileiro", 5);
        Controller.getInstance().init(words);

        for (int i = 0; i < words.size(); i++) {
            System.out.println(i + " -> " + words.get(i).getQuestion());
        }



    }
}


