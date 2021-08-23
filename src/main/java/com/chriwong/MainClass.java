package com.chriwong;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainClass {

    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/applesaucedb",
                    "root",
                    "rootroot");

            if (connection.isValid(500)) {
                System.out.println("Got a connection using DriverManager!");

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM foods");
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + " scores " + resultSet.getString(3) + "/10");
                }
            }

        } catch (Exception e) {
            System.out.println("Exception using DriverManager: " + e.getMessage());
        }

        try {

            MysqlDataSource ds = new MysqlDataSource();
            ds.setDatabaseName("applesaucedb");
            Connection connection = ds.getConnection("root", "rootroot");

            if (connection.isValid(500)) {
                System.out.println("Got a connection using DataSource!");
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT name FROM foods");

                System.out.println("Here is a list of all foods:");
                while(resultSet.next()) {
                    System.out.println(resultSet.getString(1));
                }
                System.out.println("That's all, Folks!");
            }

        } catch (Exception e) {
            System.out.println("Exception using DriverManager: " + e.getMessage());
        }
    }


}
