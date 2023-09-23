package org.cross.words.system.GPT;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.cross.words.core.word.Word;
import org.cross.words.settings.GPT_Config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        var x = (numQuestions * ERROR_RATE)/100;

        if (responses.size() < x){
            return Search(knowledgeArea, numQuestions);
        }

        ArrayList<Word> words = new ArrayList<>();
        responses.forEach(response -> {
            Word word = new Word(response);
            words.add(word);
        });

        return words;
    }


    private String cleanString(String text) {

        return text;
    }

}