package com.khelacademy.dao;

import com.khelacademy.www.pojos.User;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

public interface UserDao {
    Response getUserById(Integer userId) throws SQLException;

    Response getUserByEmailId(Integer emailId);

    boolean registerUser(User userDetails);
}
