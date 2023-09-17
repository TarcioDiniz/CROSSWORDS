package org.cross.words.system.GPT;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.cross.words.settings.GPT_Config;

import java.util.ArrayList;

public class ChatGPT {

    private String API_KEY;

    public ChatGPT(String API_KEY){
        if (GPT_Config.isValidApiKey(API_KEY)){
            this.API_KEY = API_KEY;
        }
    }

    public String Search(String knowledgeArea, int numQuestions) {

        OpenAiService service = new OpenAiService(this.API_KEY);

        String researchStructure = String.format("gere " + numQuestions + " palavras aleatorias sobre " + knowledgeArea);

        CompletionRequest request = CompletionRequest.builder()
                .prompt(researchStructure)
                .model("text-davinci-003")
                .maxTokens(700)
                .build();

        ArrayList<String> responses = new ArrayList<>();

        service.createCompletion(request).getChoices().forEach(choice -> responses.add(cleanString(choice.getText())));
        responses.forEach(System.out::println);
        System.out.println(responses);

        return "response";
    }


    private static String cleanString(String text) {
        // Use uma expressão regular para encontrar e remover o número seguido de um ponto ou hífen e um espaço no início da string
        text = text.replaceFirst("^(\\d+\\.\\s*|\\d+\\-\\s*)", "");
        return text;
    }

    public static String[] transformarEmArray(String listaPalavras) {
        // Use o método split para dividir a lista de palavras em um array
        return listaPalavras.split(",\\s*");
    }

}
