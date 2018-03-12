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

    private PreparedStatement preparedStatement = null;

    private ResultSet rs = null;

    public DBArrow() throws SQLException {
    }

    @Contract(pure = true)
    public static DBArrow getArrow() {
        return dbArrow;
    }

    public ResultSet fire(String query) throws SQLException {
        try {
            dbConnection = getConnection();
            rs = dbConnection.prepareStatement(query).executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet release() throws SQLException {
        return preparedStatement.executeQuery();
    }

    public void relax(ResultSet rs) throws SQLException {
        dbConnection.close();
        dbConnection = null;
        if (preparedStatement != null)
            preparedStatement.close();
        rs.close();
    }

    private Connection getConnection() throws SQLException {
        if (dbConnection == null) {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost/caper?" +
                            "user=root&password=Wsquare");
        } else {
            return dbConnection;
        }

    }
}
