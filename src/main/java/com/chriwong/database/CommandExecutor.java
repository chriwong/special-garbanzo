package com.chriwong.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandExecutor {

    private final Connection connection;

    public CommandExecutor(Connection connection) {
        this.connection = connection;
    }


}
