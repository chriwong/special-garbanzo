package com.chriwong.database;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;

public class MySQLDatabaseConnector implements DatabaseConnector {

    private final String SUCCESS = "Established connection to MySQL database: ";
    private final String TIMEOUT = "Connection to MySQL database timed out...";
    private final String CONNECTION_FAIL = "Unable to establish connection to MySQL.";


    @Override
    public Connection getDriverManagerConnection(String host, int port, String user,
                                                 String password, String database) {
        String connectionUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            Connection connection = DriverManager.getConnection(connectionUrl, user, password);

            if (connection.isValid(1000)) {
                System.out.println(SUCCESS + database);

                this.displayTables(connection);
                return connection;
            } else {
                System.out.println(TIMEOUT);
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            System.out.println(CONNECTION_FAIL);
            throw new RuntimeException();
        }
    }


    @Override
    public Connection getDataSourceConnection(String host, int port, String user,
                                              String password, String database) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName(database);
        try {
            Connection connection = dataSource.getConnection(user, password);

            if (connection.isValid(1000)) {
                System.out.println(SUCCESS + database);

                this.displayTables(connection);
                return connection;
            } else {
                System.out.println(TIMEOUT);
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            System.out.println(CONNECTION_FAIL);
            throw new RuntimeException();
        }
    }

    private void displayTables(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet tables = statement.executeQuery("SHOW TABLES");

            System.out.println("---TABLES---");
            while (tables.next()) {
                System.out.printf("|%2s|%n", tables.getString(1));
            }
            System.out.println("------------");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

}
