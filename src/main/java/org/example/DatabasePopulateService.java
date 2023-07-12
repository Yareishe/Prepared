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
                                preparedStatement.setString(1, "John26.0");
                                ResultSet rs = preparedStatement.executeQuery();
                                String name = rs.getString("name");
                                System.out.println(name);
                                preparedStatement.execute();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        else if (query.contains("INSERT INTO client")) {
                        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                            preparedStatement.setString(1, "John26.0");
                            preparedStatement.setString(2, "1990-05-15");
                            preparedStatement.setString(3, "Senior");
                            preparedStatement.setString(4, "82786");

                            preparedStatement.execute();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        }else if (query.contains("INSERT INTO project")) {
                            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                                preparedStatement.setString(1, "1");
                                preparedStatement.setString(2, "1");
                                preparedStatement.setString(3, "2023-01-01");
                                preparedStatement.setString(4, "2023-03-31");
                                preparedStatement.setString(5, "Project 1");

                                preparedStatement.execute();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }else if (query.contains("INSERT INTO project_worker")) {
                            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                                preparedStatement.setString(1, "1");
                                preparedStatement.setString(2, "1");

                                preparedStatement.execute();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }

                        sql.setLength(0);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}