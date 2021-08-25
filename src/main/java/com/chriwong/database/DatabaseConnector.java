package com.chriwong.database;

import java.sql.Connection;

/**
 * Provides two methods of getting a vendor-specific Connection.
 */
public interface DatabaseConnector {

    Connection getDriverManagerConnection(String host, int port, String user,
                                          String password, String database);


    Connection getDataSourceConnection(String host, int port, String user,
                                       String password, String database);
}
