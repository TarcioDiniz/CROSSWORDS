package org.cross.words.settings;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Settings {
    private final TOKEN_DAO_Config tokenDaoConfig = TOKEN_DAO_Config.getInstance();

    public Settings() {

        if (getTokenGPT() == null) {
            System.out.println("Baixando Token de Acesso.");
            AWS_Config awsConfig = new AWS_Config(getJsonNode());
            tokenDaoConfig.storeToken(awsConfig.getTOKEN());
        }

    }

    private JsonNode getJsonNode() {
        try {
            String url = "https://drive.google.com/uc?id=1TrVbWjPn4Z_5jtA9_ePwrTIFjU3XSaJA";
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(connection.getInputStream());

                // Agora você pode usar a variável 'jsonNode' para acessar os dados do JSON
                //System.out.println(jsonNode.toPrettyString());

                // Retornar o JsonNode, se necessário
                return jsonNode;
            } else {
                System.out.println("Falha ao acessar o arquivo. Código de status: " + connection.getResponseCode());
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTokenGPT() {
        String retrievedToken = tokenDaoConfig.retrieveToken();
        if (retrievedToken != null) {
            //System.out.println("Token recuperado: " + retrievedToken);
            return retrievedToken;
        } else {
            System.out.println("Nenhum token encontrado no banco de dados.");
            return null;
        }
    }

    public void updateTokenGPT() {
        AWS_Config awsConfig = new AWS_Config(getJsonNode());
        tokenDaoConfig.updateToken(awsConfig.getTOKEN());
    }

}
