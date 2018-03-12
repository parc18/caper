package com.khelacademy.www.utils;

import org.jetbrains.annotations.Contract;

import java.sql.*;

public class DBArrow {
    private static DBArrow dbArrow;

    static {
        try {
            dbArrow = new DBArrow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection dbConnection = null;

    PreparedStatement preparedStatement = null;

    public DBArrow() throws SQLException {
    }

    @Contract(pure = true)
    public static DBArrow getArrow() {
        return dbArrow ;
    }

    public PreparedStatement fire(String query) throws SQLException {
        try {
            dbConnection = getConnection();
            preparedStatement = dbConnection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    public ResultSet release() throws SQLException {
        return preparedStatement.executeQuery();
    }

    public void relax() throws SQLException {
        dbConnection.close();
        preparedStatement.close();
    }
    private Connection getConnection() throws SQLException {
        if(dbConnection==null){
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost/caper?" +
                            "user=root&password=Wsquare");
        }else {
            return dbConnection;
        }

    }
}
