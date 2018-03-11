package com.khelacademy.dao;
import com.khelacademy.www.pojos.User;
public interface UserDao {
    User getUserById(Integer userId);
    User getUserByEmailId(Integer emailId);
    boolean registerUser(User userDetails);
}
