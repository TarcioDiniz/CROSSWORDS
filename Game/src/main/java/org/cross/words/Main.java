package org.cross.words;


import org.cross.words.Utilities.Vector2D;
import org.cross.words.core.Board;
import org.cross.words.core.Direction;
import org.cross.words.core.Word;
import org.cross.words.settings.Settings;
import org.cross.words.system.GPT.ChatGPT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;


public class Main {
    public static void main(String[] args) throws IOException {
//        Settings settings = new Settings();
//        String apiKey = settings.getTokenGPT();
//        if (apiKey != null) {
//            // Use a apiKey para fazer chamadas à API do Chat GPT
//            //System.out.println("API_KEY: " + apiKey);
//        } else {
//            System.out.println("Não foi possível obter uma API_KEY válida.");
//        }
//
//        ChatGPT chatGPT = new ChatGPT(apiKey);
        //String resposta = chatGPT.Search("biologia", 20);

        String[] words = {"Tarcio", "clara", "pedro", "rna", "joao", "maria"};

        Board board = new Board(words);


    }
}



