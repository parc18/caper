package com.khelacademy.www.utils;

import org.jetbrains.annotations.Contract;
import org.springframework.context.annotation.Bean;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class DBArrow {
    public static DBArrow dbArrow;
    PreparedStatement preparedStatement = null;
    static {
        try {
            dbArrow = new DBArrow();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  Connection dbConnection;

    static {
        try {
            dbConnection = getConnection(
                        "jdbc:mysql://localhost/caper?" +
                                "user=root&password=Wsquare");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBArrow() throws SQLException {}
    @Contract(pure = true)
    public static DBArrow getArrow(){
     return dbArrow;
    }
    public PreparedStatement fire(String query) throws SQLException {
        try {
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

}
