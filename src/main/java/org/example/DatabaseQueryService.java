package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private Database database;
    public DatabaseQueryService(Database database) {
        this.database = database;
    }


    public static void main(String[] args) {
        Database database = new Database();
        DatabaseQueryService queryService = new DatabaseQueryService(database);
        List<DatabaseQueryService.MaxProjectCountClient> maxProjectCountClients = queryService.findMaxProjectsClient();
        System.out.println(maxProjectCountClients + "1");

        List<DatabaseQueryService.MaxSalaryWorker> maxsalaryworker = queryService.findMaxSalaryWorker();
        System.out.println(maxsalaryworker + "2");

        List<DatabaseQueryService.YoungestEldestWorkers> youngestEldestWorkers = queryService.findYoungestEldestWorkers();
        System.out.println(youngestEldestWorkers + "3");

        List<DatabaseQueryService.FindLongestProject> findlongestproject = queryService.findlongestproject();
        System.out.println(findlongestproject + "4");

        List<DatabaseQueryService.ProjectPrices> projectprices = queryService.projectprices();
        System.out.println(projectprices + "5");
    }

    public class MaxProjectCountClient {
        private String name;
        private int projectCount;

        public MaxProjectCountClient(String name, int projectCount) {
            this.name = name;
            this.projectCount = projectCount;
        }

        public String getName() {
            return name;
        }

        public int getProjectCount() {
            return projectCount;
        }

        public String toString() {
            return " " + name + " " + projectCount + '\n';
        }
    }

    public class MaxSalaryWorker {
        private String name;
        private int salary;

        public MaxSalaryWorker(String name, int salary) {
            this.name = name;
            this.salary = salary;
        }

        public String getName() {
            return name;
        }

        public int getProjectCount() {
            return salary;
        }

        public String toString() {
            return " " + name + " " + salary + '\n';
        }
    }

    public class YoungestEldestWorkers {
        private String name;
        private String type;
        private String birthday;

        public YoungestEldestWorkers(String name, String type, String birthday) {
            this.name = name;
            this.type = type;
            this.birthday = birthday;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getBirthday() {
            return birthday;
        }
        public String toString() {
            return " " + type + " " + name + " " + birthday + '\n';
        }


    }

    public class FindLongestProject {
        private String name;

        private int monthcount;

        public FindLongestProject(String name, int monthcount){
            this.name = name;
            this.monthcount = monthcount;
        }

        public String getName(){
            return name;
        }

        public int getMonthcount(){
            return monthcount;
        }

        public String toString(){
            return " " + name + " " + monthcount + '\n';
        }
    }

    public class ProjectPrices {
        private String name;

        private int price;

        public ProjectPrices(String name, int price){
            this.name = name;
            this.price = price;
        }

        public String getName(){
            return name;
        }

        public int getMonthcount(){
            return price;
        }

        public String toString(){
            return " " + name + " " + price + '\n';
        }
    }

    public List<MaxProjectCountClient> findMaxProjectsClient() {
        List<MaxProjectCountClient> maxProjectCountClients = new ArrayList<>();
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = readSQLFromFile("sql/find_max_projects_client.sql");
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                Integer projectCount = resultSet.getInt("PROJECT_COUNT");
                if(projectCount != null) {
                    MaxProjectCountClient client = new MaxProjectCountClient(name, projectCount - 16);
                    maxProjectCountClients.add(client);
                }
            }
            System.out.println(maxProjectCountClients.size());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return maxProjectCountClients;
    }
    public List<MaxSalaryWorker> findMaxSalaryWorker() {
        List<MaxSalaryWorker> maxsalaryworker = new ArrayList<>();
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = readSQLFromFile("sql/find_max_salary_worker.sql");
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                int projectCount = resultSet.getInt("SALARY");
                MaxSalaryWorker client = new MaxSalaryWorker(name, projectCount);
                maxsalaryworker.add(client);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return maxsalaryworker;
    }

    public List<YoungestEldestWorkers> findYoungestEldestWorkers() {
        List<YoungestEldestWorkers> youngestEldestWorkers = new ArrayList<>();
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = readSQLFromFile("sql/find_youngest_eldest_workers.sql");
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String type = resultSet.getString("TYPE");
                String name = resultSet.getString("NAME");
                String birthday = resultSet.getString("BIRTHDAY");
                YoungestEldestWorkers client = new YoungestEldestWorkers(type, name, birthday);
                youngestEldestWorkers.add(client);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return youngestEldestWorkers;
    }

    public List<FindLongestProject> findlongestproject() {
        List<FindLongestProject> longestproject = new ArrayList<>();
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = readSQLFromFile("sql/find_longest_project.sql");
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                int monthcount = resultSet.getInt("MONTH_COUNT");
                FindLongestProject client = new FindLongestProject(name, monthcount);
                longestproject.add(client);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return longestproject;
    }

    public List<ProjectPrices> projectprices() {
        List<ProjectPrices> printprojectprices = new ArrayList<>();
        try (Connection connection = database.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = readSQLFromFile("sql/print_project_prices.sql");
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                int price = resultSet.getInt("PRICE");
                ProjectPrices client = new ProjectPrices(name, price);
                printprojectprices.add(client);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return printprojectprices;
    }

    private String readSQLFromFile(String filename) throws IOException {
        Path filePath = Path.of(filename);
        return Files.readString(filePath);
    }
}