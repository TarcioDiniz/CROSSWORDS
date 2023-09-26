package org.cross.words.core.controller;

import org.cross.words.Utilities.InputUtil;
import org.cross.words.settings.Settings;
import org.cross.words.system.GPT.ChatGPT;

public class GameController {

    private Settings settings;
    private ChatGPT chatGPT;


    private static GameController instance;

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private GameController() {
        this.settings = new Settings();
        String apiKey = settings.getTokenGPT();
        if (apiKey != null) {
            // Use a apiKey para fazer chamadas à API do Chat GPT
            //System.out.println("API_KEY: " + apiKey);
        } else {
            System.out.println("Não foi possível obter uma API_KEY válida.");
        }
        this.chatGPT = new ChatGPT(apiKey);

    }

    public void start(){
        System.out.println("Seja Bem-Vindo!");
        String theme = InputUtil.getInputString("Para Gerar sua Palavra Cruzada escolha um tema de sua preferência: ");


        var words = chatGPT.Search(theme, 5);
        Controller.getInstance().init(words);

        System.out.println("Dicas: ");
        for (int i = 0; i < words.size(); i++) {
            System.out.println(i + " -> " + words.get(i).getQuestion() + " (" + words.get(i).getSize() + " Letras).");
        }


        InputUtil.closeScanner();
    }





}
