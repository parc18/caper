package com.khelacademy.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.protocol.HTTP;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.khelacademy.config.JwtTokenUtil;
import com.khelacademy.dao.UserDao;
import com.khelacademy.dto.UserDto;
import com.khelacademy.model.BasicUserDetails;
import com.khelacademy.model.JwtResponse;
import com.khelacademy.service.JwtUserDetailsService;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.BookingRequestObject;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.pojos.PriceDetails;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.services.PresenceStatus;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.DBArrow;
import com.khelacademy.www.utils.GameCategory;
import com.khelacademy.www.utils.UserUtils;


@Component
@Transactional
public class UserDaoImpl implements UserDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    DBArrow SQLArrow = DBArrow.getArrow();
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
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
    public boolean updateStatus(String phone, Integer status) {
    	PreparedStatement statement = SQLArrow.getPreparedStatement("UPDATE user SET status=? where phone=?");
    	try {
			statement.setInt(1, status);
			statement.setString(2, phone);
			if(SQLArrow.fireBowfishing(statement) == 1){
				return true;
			}
		} catch (SQLException e) {
			LOGGER.debug("ERROR AFTER VERIFYING STATUS FOR PHONE : " + phone + "WITH ERROR " + e.getMessage());
			return false;
		}
    	
        return true;
    }
    @Override
    public String registerUser(User userDetails) {
    	PreparedStatement statement = SQLArrow.getPreparedStatement("SELECT  * from user  WHERE phone =?");
    	try {
    		statement.setString(1, userDetails.getContactNumber());
    		ResultSet rs = SQLArrow.fire(statement);
    		if(rs.next()) {
    			return PresenceStatus.EXISTS.toString();
    		}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        statement = SQLArrow.getPreparedStatement("INSERT INTO user  (firstname, lastname, email, passcode,phone,city,status,welcomedate ) values (?, ?, ?, ?, ?, ?, ?,NOW())");
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
            statement.setString(7, userDetails.getStatus());
            if(SQLArrow.fireBowfishing(statement) == 1){
//            	SMSService msg = new SMSService();
//            	String a= msg.sendSMS(userDetails.getContactNumber());
//            	return a;
            	SQLArrow.relax(null);
            	return PresenceStatus.REGISTRED_SUCCESSFULLY_BUT_NOT_OTP_VERIFIED.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return PresenceStatus.COUDNT_REGISTER.toString();
    }

	@Override
	public String recordTempUsers(BookingRequestObject bookingRequestObject) throws SQLException{
    	StringBuffer SQLString = new StringBuffer("INSERT INTO temp_users  (NAME, USER_ID, PRICE_ID, booking_id ,game_user_id)  values ");
    	BookEventDaoImpl bookEventDaoImpl = new BookEventDaoImpl();
    	int gameId = bookEventDaoImpl.getLastUserGameId();
    	gameId++;
    	
    	StringBuffer vals = new StringBuffer("");
    	boolean flag = true;
		for (PriceDetails p : bookingRequestObject.getPriceDetail()) {
			if(p.getQuantity().intValue() > 0){
				if(bookEventDaoImpl.getCategoryId(p.getPriceId()).intValue() < GameCategory.BOYS_DOUBLES || bookEventDaoImpl.getCategoryId(p.getPriceId()).intValue() == GameCategory.TEAM_EVENTS) {
					for(int y=1 ; y<= p.getQuantity(); y++){
						if(flag) {
							vals.append("(\""+ p.getPlayerNames().get(y).toString() + "\"," +  bookingRequestObject.getUserId() + ","+ p.getPriceId() + ","+ bookingRequestObject.getBookingId() + ","+ gameId +")");
						}else{
							vals.append(", (\""+ p.getPlayerNames().get(y).toString() + "\"," +  bookingRequestObject.getUserId() + ","+  p.getPriceId() +  ","+ bookingRequestObject.getBookingId() + ","+ gameId +")");
						}
						flag = false;
						gameId++;
					}
				}else {
					for(int y=1 ; y<= p.getQuantity(); y*=2){
						if(flag) {
							vals.append("(\""+ p.getPlayerNames().get(y).toString() + "\"," +  bookingRequestObject.getUserId() + ","+ p.getPriceId() + ","+ bookingRequestObject.getBookingId() + ","+ gameId +")");
							vals.append(",(\""+ p.getPlayerNames().get(y+1).toString() + "\"," +  bookingRequestObject.getUserId() + ","+ p.getPriceId() + ","+ bookingRequestObject.getBookingId() + ","+ gameId +")");
						}else{
							vals.append(", (\""+ p.getPlayerNames().get(y).toString() + "\"," +  bookingRequestObject.getUserId() + ","+  p.getPriceId() + ","+ bookingRequestObject.getBookingId() + ","+ gameId +")");
							vals.append(",(\""+ p.getPlayerNames().get(y+1).toString() + "\"," +  bookingRequestObject.getUserId() + ","+ p.getPriceId() + ","+ bookingRequestObject.getBookingId() + ","+ gameId +")");
						}
						flag = false;
						gameId++;
					}
				}
			}
		}
    	SQLString.append(vals);

		System.out.println(SQLString);
    	PreparedStatement statement = SQLArrow.getPreparedStatement(SQLString.toString());
    	try {
			if(SQLArrow.fireBowfishing(statement) >= 1) {
				return PresenceStatus.ALL_TEMP_USER_SUCCESS.toString();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PresenceStatus.UNKNOWN_ERROR.toString();
	}

	@Override
	public BasicUserDetails getJwt(String username, String password) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM BasicUserDetails E WHERE E.username =:uname";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("uname", username);
		List<BasicUserDetails> results = query.list();
		return results.get(0);
		//return null;
	}

	@Override
	public ResponseEntity<?> FirstTimeRegistration(UserDto userReq) throws Exception {
		if(!StringUtils.isEmpty((userReq.getEmail()))) {
			return signUpUsingEmail(userReq);
		}else if(!StringUtils.isEmpty((userReq.getPhone()))) {
			 return signUpUsingPhone(userReq);
		}
		MyErrors error = new MyErrors("User already registered, Please login");
    	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 406);
    	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	}

	private ResponseEntity<?> signUpUsingPhone(UserDto userReq) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	private ResponseEntity<?> signUpUsingEmail(UserDto userReq) throws Exception {
		if(userReq.getPassWord().equals(userReq.getPassWordVerify()) && UserUtils.isValid(userReq.getEmail())) {
			Session session = this.sessionFactory.getCurrentSession();
			String passWord = UserUtils.getEncryptedPass(userReq.getPassWord());
			String hql = "FROM BasicUserDetails E WHERE E.email =:email";
			@SuppressWarnings("unchecked")
			Query<BasicUserDetails> query = session.createQuery(hql);
			query.setString("email", userReq.getEmail());
			List<BasicUserDetails> results = query.list();
			if(results != null && results.size()> 0) {
				MyErrors error = new MyErrors("User already registered, Please login");
	        	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 406);
	        	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
			}
			BasicUserDetails user = new BasicUserDetails(userReq.getEmail(), passWord);
			
			session.save(user);
		}else {
			MyErrors error = new MyErrors("Password mismatch or invalid email id");
        	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 406);
        	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
		}
		ApiFormatter<String>  success= ServiceUtil.convertToSuccessResponse("You have been successfully registered");
		return ResponseEntity.status(HttpStatus.OK).body(success);
	}

	@Override
	public ResponseEntity<?> userLogin(UserDto userReq) throws Exception {
		if(!StringUtils.isEmpty((userReq.getEmail()))) {
			return loginUsingEmail(userReq);
		}else if(!StringUtils.isEmpty((userReq.getPhone()))) {
			 return signUpUsingPhone(userReq);
		}
		return null;
	}
	@SuppressWarnings("deprecation")
	private ResponseEntity<?> loginUsingEmail(UserDto userReq) {
		//final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		Session session = this.sessionFactory.getCurrentSession();
		String passWord = UserUtils.getEncryptedPass(userReq.getPassWord());
		String hql = "FROM BasicUserDetails E WHERE E.email =:email and E.passWord = :pass";
		@SuppressWarnings("unchecked")
		Query<BasicUserDetails> query = session.createQuery(hql);
		query.setString("email", userReq.getEmail());
		query.setString("pass", UserUtils.getEncryptedPass(userReq.getPassWord()));
		List<BasicUserDetails> results = query.list();
		if(results != null && results.size() == 1) {
			final String token = jwtTokenUtil.generateToken(results.get(0));
			ApiFormatter<String>  success= ServiceUtil.convertToSuccessResponse(token);
			return ResponseEntity.status(HttpStatus.OK).body(success);
			//return ResponseEntity.ok(new JwtResponse(token));
		}
		MyErrors error = new MyErrors("Invalid email/password");
    	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 406);
    	return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err);
	}
}
