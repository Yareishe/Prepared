package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DatabasePopulateService {

    public static void main(String[] args) {
        try {
            Database database = Database.getInstance();
            Connection connection = database.getConnection();
            executeSqlFile(connection, "sql/populate_db.sql");
            database.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void executeSqlFile(Connection connection, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             Statement statement = connection.createStatement()) {
            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sql.append(line);
                if (line.endsWith(";")) {
                    String[] queries = sql.toString().split(";");
                    for (String query : queries) {
                        if (query.contains("SELECT * FROM worker")) {
                            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                                preparedStatement.setString(1,"John26.0");
                                ResultSet rs = preparedStatement.executeQuery();
                                String name = rs.getString("name");
                                System.out.println(name);
                                preparedStatement.execute();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            System.out.println(query);
                            statement.execute(query.trim());
                        }
                    }
                    sql.setLength(0);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}