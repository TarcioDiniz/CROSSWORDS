package org.cross.words.settings;

import com.theokanning.openai.service.OpenAiService;

public class GPT_Config {

    public static boolean isValidApiKey(String apiKey) {
        OpenAiService service = new OpenAiService(apiKey);
        try {
            service.listModels();
            //System.out.println("Token is valid");
            return true;
        } catch (Exception e) {
            //System.out.println("The token is not valid: " + e.getMessage());
            return false;
        }
    }

}
