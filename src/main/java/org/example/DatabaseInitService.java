package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DatabaseInitService {

    public static void main(String[] args) {
        try {
            Database database = Database.getInstance();
            Connection connection = database.getConnection();
            executeSqlFile(connection, "sql/init_db.sql");
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
                    String query = sql.toString();
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
                        statement.execute(sql.toString());
                        ResultSet rs = statement.executeQuery("SELECT * FROM worker");
                        if(rs.next()) {
                            String name = rs.getString("name");
                            System.out.println(name);
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