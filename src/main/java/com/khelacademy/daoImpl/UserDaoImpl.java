package com.khelacademy.daoImpl;

import com.khelacademy.dao.UserDao;
import com.khelacademy.www.api.ApiEndpoint;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.DBArrow;
import com.khelacademy.www.utils.UserUtils;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    DBArrow SQLArrow = DBArrow.getArrow();
    
    @Override
    public Response getUserById(Integer userId) throws SQLException {
    	LOGGER.info("GET USER IS CALLED FOR ID: " + userId);
       // ApiFormatter<List<User>> userResponse=null;
        PreparedStatement statement;
        List<User> allUser = new ArrayList<User>();
        if(userId==-1) {
        	statement = SQLArrow.getPreparedStatement("SELECT * FROM user");
        } else{
        	statement = SQLArrow.getPreparedStatement("SELECT * FROM user where id= ?");	
        	statement.setInt(1, userId);
        }
        try (ResultSet rs = SQLArrow.fire(statement)) {
            User user = new User();
            while (rs.next()) {
                user.setUserId(rs.getInt("id"));
                user.setFirstName(rs.getString("firstname"));
                user.setLastName(rs.getString("lastname"));
                user.setEmail(rs.getString("email"));
                user.setCity(rs.getString("city"));
                user.setStatus(rs.getString("status"));
                user.setContactNumber(rs.getString("phone"));
                allUser.add(user);
            }
        }catch(Exception e){
        	LOGGER.error("ERROR IN GETTING USER DETAILE FOR ID: " + userId);
        	MyErrors error = new MyErrors(e.getMessage());
        	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 500);
            return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
            }).build();
        }
        if(allUser.size() == 1){
        	ApiFormatter<User> userResponse = ServiceUtil.convertToSuccessResponse(allUser.get(0));
            return Response.ok(new GenericEntity<ApiFormatter<User>>(userResponse) {
            }).build();
        }else{
        	LOGGER.debug("TOTAL USER RETRIEVED: " + allUser.size());
        	ApiFormatter<List<User>> userResponse = ServiceUtil.convertToSuccessResponse(allUser);
            return Response.ok(new GenericEntity<ApiFormatter<List<User>>>(userResponse) {
            }).build();
        }
    }

    @Override
    public Response getUserByEmailId(Integer emailId) {
        return null;
    }

    @Override
    public boolean registerUser(User userDetails) {
        PreparedStatement statement = SQLArrow.getPreparedStatement("INSERT INTO user  (firstname, lastname, email, passcode,phone,city,status,welcomedate ) values (?, ?, ?, ?, ?, ?, ?,NOW())");
        try {
            statement.setString(1, userDetails.getFirstName());
            statement.setString(2, userDetails.getLastName());
            statement.setString(3, userDetails.getEmail());
            if(userDetails.getPasscode() == null)
                statement.setString(4, UserUtils.getSaltString(5));
            else
                statement.setString(4, userDetails.getPasscode());
            statement.setString(5, userDetails.getContactNumber());
            statement.setString(6, userDetails.getCity());
            statement.setInt(7, 1);
            return SQLArrow.fireBowfishing(statement) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
