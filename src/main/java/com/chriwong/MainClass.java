package com.chriwong;

import com.chriwong.database.CommandExecutor;
import com.chriwong.database.DatabaseConnector;
import com.chriwong.database.MySQLDatabaseConnector;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainClass {

    public static void main(String[] args) {
        InputGatherer inputGatherer = new InputGatherer(System.in);

        System.out.println("Attempting to connect to database...");
        DatabaseConnector connector = new MySQLDatabaseConnector();

        Connection connection = connector.getDataSourceConnection("localhost", 3306,
                "root", "rootroot", "applesauceDB");
        // alternatively, you could use DriverManager

//        CommandExecutor executor = new CommandExecutor(connection);

        System.out.print("Enter 'SELECT' or 'INSERT' to begin building a command: ");
        String selection = inputGatherer.getSQLCommand();

        if (selection.equalsIgnoreCase("SELECT")) {
            int x = 0;
            // begin building query
        } else {
            int y = 1;
            //begin building insert statement
        }

    }

}
