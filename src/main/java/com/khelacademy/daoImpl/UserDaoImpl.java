package com.khelacademy.daoImpl;

import com.khelacademy.dao.UserDao;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.DBArrow;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    DBArrow SQLArrow = DBArrow.getArrow();
    @Override
    public Response getUserById(Integer userId) throws SQLException {
        ApiFormatter<User> userResponse;
        PreparedStatement statement = SQLArrow.getPreparedStatement("SELECT * FROM user where id= ?");
        statement.setInt(1, 1);
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
            }
            userResponse = ServiceUtil.convertToSuccessResponse(user);
            SQLArrow.relax(rs);
        }
        return Response.ok(new GenericEntity<ApiFormatter<User>>(userResponse) {
        }).build();
    }

    @Override
    public Response getUserByEmailId(Integer emailId) {
        return null;
    }

    @Override
    public boolean registerUser(User userDetails) {
        return false;
    }
}
