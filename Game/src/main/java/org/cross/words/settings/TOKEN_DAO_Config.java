package org.cross.words.settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TOKEN_DAO_Config {
    private static TOKEN_DAO_Config instance;
    private Connection connection;

    private TOKEN_DAO_Config() {
        // Initialize the database connection (SQLite in this example)
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:ACCESS.db");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized TOKEN_DAO_Config getInstance() {
        if (instance == null) {
            instance = new TOKEN_DAO_Config();
        }
        return instance;
    }


    public void storeToken(String token) {
        try {
            // Create the table if it doesn't exist (can be a setup step)
            String createTableSQL = "CREATE TABLE IF NOT EXISTS tokens (id INTEGER PRIMARY KEY, token TEXT)";
            try (PreparedStatement createTableStatement = connection.prepareStatement(createTableSQL)) {
                createTableStatement.executeUpdate();
            }

            // Insert the token into the database
            String insertTokenSQL = "INSERT INTO tokens (token) VALUES (?)";
            try (PreparedStatement insertTokenStatement = connection.prepareStatement(insertTokenSQL)) {
                insertTokenStatement.setString(1, token);
                insertTokenStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String retrieveToken() throws TokenDaoException {
        try {
            // Retrieve the token from the database (assuming there is only one token)
            String retrieveTokenSQL = "SELECT token FROM tokens LIMIT 1";
            try (PreparedStatement retrieveTokenStatement = connection.prepareStatement(retrieveTokenSQL);
                 ResultSet resultSet = retrieveTokenStatement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString("token");
                }
            }
            throw new TokenDaoException("Não foi possível encontrar um token no banco de dados.");
        } catch (SQLException e) {
            throw new TokenDaoException("Erro ao recuperar o token do banco de dados.");
        }
    }

    public class TokenDaoException extends Exception {
        public TokenDaoException(String message) {
            super(message);
        }
    }


    public void updateToken(String newToken) {
        try {
            // Update the token in the database (assuming there is only one token)
            String updateTokenSQL = "UPDATE tokens SET token = ?";
            try (PreparedStatement updateTokenStatement = connection.prepareStatement(updateTokenSQL)) {
                updateTokenStatement.setString(1, newToken);
                updateTokenStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
