package org.cross.words;


import org.cross.words.core.Controller;
import org.cross.words.core.word.Word;
import org.cross.words.settings.Settings;
import org.cross.words.system.GPT.ChatGPT;

import java.io.IOException;
import java.util.*;


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
        var responses = chatGPT.Search("futebol brasileiro", 20);


//        words.forEach(word -> {
//            System.out.println(word.getWord());
//        });

        //String[] strings = {"Tarcio", "paralelepipedo", "clara", "alberto", "marcia", "tita", "lipideos", "fumaça", "abridor", "garrafa", "pedro", "joao", "marcos", "allyne"};

//        Arrays.sort(strings, new Comparator<String>() {
//           @Override
//            public int compare(String str1, String str2) {
//               // Compara as strings em ordem decrescente com base no comprimento delas
//               return Integer.compare(str2.length(), str1.length());
//            }
//        });

        //ArrayList<Word> words = new ArrayList<>();
//        for (String string : strings) {
//            Word word = new Word(string);
//            words.add(word);
//        }

        Controller.getInstance().init(responses);


    }
}


