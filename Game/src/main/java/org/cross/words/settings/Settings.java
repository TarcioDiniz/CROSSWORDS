package org.cross.words.settings;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Settings {
    private final TOKEN_DAO_Config tokenDaoConfig = TOKEN_DAO_Config.getInstance();

    public Settings() {
        initializeToken();
    }

    private void initializeToken() {
        String retrievedToken = getTokenGPT();
        if (retrievedToken == null) {
            System.out.println("Baixando Token de Acesso...");
            JsonNode jsonNode = getJsonNode();
            if (jsonNode != null) {
                AWS_Config awsConfig = new AWS_Config(jsonNode);
                tokenDaoConfig.storeToken(awsConfig.getTOKEN());
                System.out.println("Token de Acesso baixado e armazenado com sucesso.");
            } else {
                System.out.println("Não foi possível baixar o Token de Acesso.");
            }
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
                return jsonNode;
            } else {
                System.out.println("Falha ao acessar o arquivo. Código de status: " + connection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTokenGPT() {
        try {
            String retrievedToken = tokenDaoConfig.retrieveToken();
            if (retrievedToken != null) {
                return retrievedToken;
            } else {
                System.out.println("Nenhum token encontrado no banco de dados.");
                return null;
            }
        } catch (TOKEN_DAO_Config.TokenDaoException e) {
            System.err.println("Erro ao recuperar o token: " + e.getMessage());
            // Você pode relançar a exceção aqui ou tratar de outra forma, dependendo dos requisitos.
            // Para relançar a exceção, use "throw e;".
            return null; // Ou outra ação apropriada em caso de exceção.
        }
    }


    public void updateTokenGPT() {
        JsonNode jsonNode = getJsonNode();
        if (jsonNode != null) {
            AWS_Config awsConfig = new AWS_Config(jsonNode);
            tokenDaoConfig.updateToken(awsConfig.getTOKEN());
            System.out.println("Token de Acesso atualizado com sucesso.");
        } else {
            System.out.println("Não foi possível atualizar o Token de Acesso.");
        }
    }
}
