package org.cross.words.system.GPT;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.cross.words.core.word.Word;
import org.cross.words.settings.GPT_Config;

import java.util.*;

/*
 *
 * This code integrates the functionality of the GPT-3.5 model to
 * generate random words and their respective questions with
 * based on a given area of knowledge. The class uses
 * the OpenAI API for requesting text completions and handling
 * answers to create words and questions related to
 * specified topic.
 *
 */

public class ChatGPT {

    private String API_KEY;

    public ChatGPT(String API_KEY) {
        if (GPT_Config.isValidApiKey(API_KEY)) {
            this.API_KEY = API_KEY;
        }
    }

    public ArrayList<Word> Search(String knowledgeArea, int numQuestions) {

        OpenAiService service = new OpenAiService(this.API_KEY);

        String researchStructure = String.format("gere " + numQuestions + " palavras aleatorias sobre " + knowledgeArea);

        CompletionRequest request = CompletionRequest.builder()
                .prompt(researchStructure)
                .model("text-davinci-003")
                .maxTokens(700)
                .build();

        ArrayList<String> responses = new ArrayList<>();
        service.createCompletion(request).getChoices().forEach(choice -> {
            List<String> words = Arrays.asList(cleanString(choice.getText()).split("\\s+"));
            responses.addAll(words);
        });
        responses.removeIf(String::isEmpty);
        responses.removeIf(string -> !Word.isWord(string));
        responses.removeIf(string -> string.length() <= 2);

        int ERROR_RATE = 80;
        var x = (numQuestions * ERROR_RATE) / 100;

        if (responses.size() < x) {
            return Search(knowledgeArea, numQuestions);
        }

        Comparator<String> lengthComparator = new Comparator<String>() {
            @Override
            public int compare(String str1, String str2) {
                // Comparar pelo comprimento das strings em ordem decrescente
                return Integer.compare(str2.length(), str1.length());
            }
        };

        // Ordenando a ArrayList usando o comparador personalizado
        Collections.sort(responses, lengthComparator);

        ArrayList<Word> words = new ArrayList<>();
        responses.forEach(response -> {
            Word word = new Word(response);
            generateQuestion(word, knowledgeArea);
            words.add(word);
        });

        return words;
    }

    private void generateQuestion(Word word, String knowledgeArea) {

        if (word.getQuestion() == null) {

            OpenAiService service = new OpenAiService(this.API_KEY);

            String researchStructure = String.format("sobre o assunto " + knowledgeArea + " gere uma pergunta direta em que sua resposta sera a palavra '" + word.getWord() + "'.");

            CompletionRequest request = CompletionRequest.builder()
                    .prompt(researchStructure)
                    .model("text-davinci-003")
                    .maxTokens(700)
                    .build();

            String responseFromService = service.createCompletion(request).getChoices().get(0).getText();
            word.setQuestion(responseFromService.replaceAll("\\n", ""));
//            var responseFromService = service.createCompletion(request).getChoices();
//
//            for (int i = 0; i < responseFromService.size(); i++) {
//
//                if (responseFromService.get(i).getText() != null) {
//                    word.setQuestion(responseFromService.get(i).getText());
//                }
//
//            }


        }

    }


    private String cleanString(String text) {
        return text;
    }

}