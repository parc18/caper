package com.khelacademy.daoImpl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import com.khelacademy.dao.BookEventDao;
import com.khelacademy.dao.UserDao;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.BookingRequestObject;
import com.khelacademy.www.pojos.PriceDetails;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.services.BookingStatus;
import com.khelacademy.www.services.PresenceStatus;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.services.UserStatus;
import com.khelacademy.www.utils.Constants;
import com.khelacademy.www.utils.DBArrow;
import com.khelacademy.www.utils.InstamojoPaymentHelper;
import com.khelacademy.www.utils.NoOfTickerCalculator;

public class BookEventDaoImpl implements BookEventDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    DBArrow SQLArrow = DBArrow.getArrow();
	@Override
	public Response bookSingleTicket(BookingRequestObject bookingRequestObject, boolean isSingle) throws UnsupportedEncodingException {
		User user = new User();
		UserDao userDao = new UserDaoImpl();
		if(isSingle)
			user.setFirstName(bookingRequestObject.getPriceDetail().get(0).getPlayerNames().get(1).toString());
		else
			user.setFirstName("khelacademyPlayer");
		user.setLastName("");
		user.setCity("");
		user.setAddress("");
		user.setContactNumber(bookingRequestObject.getPhone());
		user.setEmail(bookingRequestObject.getEmail());
		user.setStatus(UserStatus.INITIATED.toString());
		CreatePaymentOrderResponse response = null;
		try {
			
			if(userDao.registerUser(user).equalsIgnoreCase(PresenceStatus.REGISTRED_SUCCESSFULLY.toString()) || userDao.registerUser(user).equalsIgnoreCase(PresenceStatus.EXISTS.toString())){
		        String TXN_ID = Long.toString(System.nanoTime());
		        byte[] bytesOfMessage = TXN_ID.getBytes("UTF-8");

		        MessageDigest md = null;
				try {
					md = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        byte[] thedigest = md.digest(bytesOfMessage);
		        TXN_ID = thedigest.toString();
		        
		        PreparedStatement statement = SQLArrow.getPreparedStatementForId("INSERT INTO booking  (booking_date, no_of_tickets, txn_id, status ) values (NOW(), ?, ?, ?)	");
		        statement.setInt(1,(NoOfTickerCalculator.totalTicket(bookingRequestObject)));
		        statement.setString(2, TXN_ID);
		        statement.setString(3, BookingStatus.INITIATED.toString());
		        int bookingTableUpdate = SQLArrow.fireBowfishing(statement);
		        int bookingId = 0;
                ResultSet rs = statement.getGeneratedKeys();
                if(rs.next())
                {
                	bookingId = rs.getInt(1);
                }
	            if(bookingTableUpdate > 0 && bookingId > 0){
	            	StringBuffer SQLString = new StringBuffer("INSERT INTO booking_prices  (booking_id, price_id, quantity ) values ");
	            	
	            	StringBuffer vals = new StringBuffer("");
	            	int i=0;
	            	for (PriceDetails p : bookingRequestObject.getPriceDetail()) {
	            		if(i==0) {
	            			vals.append("("+ bookingId + "," +  p.getPriceId() + ","+ p.getQuantity()+")");
	            		}
	            		else{
	            			vals.append(", ("+ bookingId + ","  +  p.getPriceId() + ","+ p.getQuantity()+")");
	            		}
	            		i++;
	            		 
	            	}
	            	SQLString.append(vals);
	            	
	            	
	            	statement = SQLArrow.getPreparedStatement(SQLString.toString());
	            	if(SQLArrow.fireBowfishing(statement) >= 1) {
	            		int userId = 0;
	            		statement = SQLArrow.getPreparedStatement("SELECT id FROM user where phone = ? or email = ?");
	            		statement.setString(1, bookingRequestObject.getPhone());
	            		statement.setString(2, bookingRequestObject.getEmail());
	                    try (ResultSet rs1 = SQLArrow.fire(statement)) {
	                        while (rs1.next()) {
	                        	userId = rs1.getInt("id");
	                        }
	                    }catch(Exception e) {
	                    	e.printStackTrace();
	                    }
	            		statement = SQLArrow.getPreparedStatementForId("INSERT INTO ticket  (event_id, booking_id, user_id ) values ( ?, ?, ?)");
	            		statement.setInt(1, bookingRequestObject.getEventId());
	            		statement.setInt(2, bookingId);
	            		statement.setInt(3, userId);
	            		if(SQLArrow.fireBowfishing(statement) >= 1) {
	            			
	        				PaymentOrder order = new PaymentOrder();
	        				//order.setId(Integer.toString(bookingId));
	        		        order.setName(user.getFirstName());
	        		        order.setEmail(user.getEmail());
	        		        order.setPhone(user.getContactNumber());
	        		        order.setDescription("Booking a event for user with email: " + user.getEmail() + "and evemtid" + bookingRequestObject.getEventId());
	        		        order.setCurrency("INR");
	        		        order.setAmount((double) bookingRequestObject.getTotalAmount());
	        		        order.setRedirectUrl(Constants.REDIRECT_URL);
	        		        order.setWebhookUrl(Constants.WEBHOOK_URL);
	        		        order.setTransactionId(Integer.toString(bookingId));
	        		    	InstamojoPaymentHelper instamojoPaymentHelper = new InstamojoPaymentHelper();
	        		    	instamojoPaymentHelper.setOrder(order);
	        		    	response = instamojoPaymentHelper.initiatePayment(order);
	            			
	            		}else{
	            			
	            		}
	            		
	            	}
	            	SQLArrow.relax(null);
	        
	            }
		        
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	ApiFormatter<CreatePaymentOrderResponse>  res= ServiceUtil.convertToSuccessResponse(response);
        return Response.ok(new GenericEntity<ApiFormatter<CreatePaymentOrderResponse>>(res) {
        }).build();
	}

	@Override
	public Response bookMultipleTicket(BookingRequestObject bookingRequestObject) {

		User user = new User();
		UserDao userDao = new UserDaoImpl();
		user.setFirstName("Khelacademy Player");
		user.setLastName("");
		user.setCity("");
		user.setAddress("");
		user.setContactNumber(bookingRequestObject.getPhone());
		user.setEmail(bookingRequestObject.getEmail());
		user.setStatus(UserStatus.INITIATED.toString());
		
		try {
			if(userDao.registerUser(user).equalsIgnoreCase(PresenceStatus.REGISTRED_SUCCESSFULLY.toString())){
				System.out.println(PresenceStatus.REGISTRED_SUCCESSFULLY);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return null;
	
	}

}
