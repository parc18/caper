package com.khelacademy.www.utils;

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

    public static DBArrow getArrow() {
        return dbArrow;
    }

    public PreparedStatement getPreparedStatement(String s) {
        try {
            dbConnection = getConnection();
            return dbConnection.prepareStatement(s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet fire(PreparedStatement statement) throws SQLException {
        try {
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public int fireBowfishing(PreparedStatement statement) throws SQLException {
        try {
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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
