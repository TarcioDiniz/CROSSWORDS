package org.cross.words.settings;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class AWS_Config {

    private final String API_KEY;
    private final String ACCESS_KEY_ID;
    private final String SECRET_ACCESS_KEY;
    private String TOKEN;

    public AWS_Config(JsonNode jsonNode) {
        if (jsonNode != null) {
            this.API_KEY = jsonNode.get("API_KEY").asText();
            this.ACCESS_KEY_ID = jsonNode.get("ACCESS_KEY_ID").asText();
            this.SECRET_ACCESS_KEY = jsonNode.get("SECRET_ACCESS_KEY").asText();
            setTOKEN();
        } else {
            // Initialize with default values or handle the case where jsonNode is null.
            this.API_KEY = null;
            this.ACCESS_KEY_ID = null;
            this.SECRET_ACCESS_KEY = null;
            this.TOKEN = null;
        }
    }

    public String getTOKEN() {
        return this.TOKEN;
    }

    private void setTOKEN() {
        Region region = Region.of("us-east-2");
        // Create your AWS credentials
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(ACCESS_KEY_ID, SECRET_ACCESS_KEY);
        // Create a Secrets Manager client
        SecretsManagerClient client = SecretsManagerClient.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .region(region)
                .build();
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder()
                .secretId(API_KEY)
                .build();

        GetSecretValueResponse getSecretValueResponse;

        try {
            getSecretValueResponse = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            // For a list of exceptions thrown, see
            // https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
            throw e;
        }
        String secret = getSecretValueResponse.secretString();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(secret);
            this.TOKEN = jsonNode.get("GPT_KEY_API").asText();
        } catch (Exception e) {
            this.TOKEN = null;
        }
    }
}
