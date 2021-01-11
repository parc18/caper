/*
 * Copyright 2016-2017 Axioma srl.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.khelacademy.www.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.khelacademy.dao.BookEventDao;
import com.khelacademy.dao.CityDao;
import com.khelacademy.dao.EventDao;
import com.khelacademy.dao.HomeDao;
import com.khelacademy.dao.SportsDao;
import com.khelacademy.dao.UserDao;
import com.khelacademy.daoImpl.CityDaoImpl;
import com.khelacademy.daoImpl.BookEventDaoImpl;
import com.khelacademy.daoImpl.EventDaoImpl;
import com.khelacademy.daoImpl.HomeDaoImpl;
import com.khelacademy.daoImpl.SportsDaoImpl;
import com.khelacademy.daoImpl.UserDaoImpl;
import com.khelacademy.model.BasicUserDetails;
import com.khelacademy.model.JwtResponse;
import com.khelacademy.www.payments.RazorPayPayment;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.BookingRequestObject;
import com.khelacademy.www.pojos.Event;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.pojos.OTPContent;
import com.khelacademy.www.pojos.Order;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.Constants;
import com.khelacademy.www.utils.InstamojoClient;
import com.khelacademy.www.utils.PaymentRequestValidator;
import com.khelacademy.www.utils.RedisBullet;
import com.khelacademy.www.utils.SMSService;

/**
 * JAX-RS API endpoint.
 */
@SuppressWarnings("restriction")
@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class ApiEndpoint {	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiEndpoint.class);
	//@Autowired
	//BasicUserDetailRespository basicUserDetailRespository; 
	
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	RazorPayPayment razorPayPayment;

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
    public ResponseEntity<?> ping() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException {
    	LOGGER.debug("Server is running Fine Check DB credentials");
    	//ßßßUserDao userDao = new UserDaoImpl();
    	//userDao.getJwt("manish", "");
    	razorPayPayment.order(null);
      	ApiFormatter<String>  events= ServiceUtil.convertToSuccessResponse("ok");
        return ResponseEntity.ok(events);
    }

    @ApiOperation("Home request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/home")
    @Produces(MediaType.APPLICATION_JSON)
    public Response home() {
        HomeDao home = new HomeDaoImpl();
        try {
            return home.getHome();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    @ApiOperation("get user request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/user_details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response user_details(@QueryParam("user_id") Integer userId) throws SQLException {
    	UserDao user = new UserDaoImpl();
    	return user.getUserById(userId);
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public ResponseEntity<?> events(@RequestParam("city_id") Integer cityId, @RequestParam("game_id") Integer gameId) throws SQLException {
        EventDao event = new EventDaoImpl();
        return event.getEventByCityId(cityId, gameId);
    }
    
    @RequestMapping(value = "/event_by_id", method = RequestMethod.GET)
    public ResponseEntity<?> eventById(@RequestParam("event_id") Integer eventId) throws SQLException {
        EventDao event = new EventDaoImpl();
        return event.getEventDetailsById(eventId);
    }
    @RequestMapping(value = "/event_prices", method = RequestMethod.GET)
    public ResponseEntity<?> eventsPrices(@RequestParam("event_id") Integer eventId) throws SQLException {
        EventDao event = new EventDaoImpl();
        return event.getEventPrice(eventId);
    }
    
    @ApiOperation("post user request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @POST
    @Path("/user_register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response user_registration(@RequestBody User userDetails) throws SQLException {
        UserDao user = new UserDaoImpl();
        String Register = user.registerUser(userDetails);
        if(Register.equals("EXIST")){
			MyErrors error = new MyErrors("User Exist Try Loggin In");
        	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 500);
            return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
            }).build();
        }
        if(Register.equals("FAILURE")){
			MyErrors error = new MyErrors("Technical Problem");
        	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 500);
            return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
            }).build();
        }
        if(!Register.equals("ERROR"))
        	userDetails.setSessionValue(Register);
    	ApiFormatter<User>  usr= ServiceUtil.convertToSuccessResponse(userDetails);
        return Response.ok(new GenericEntity<ApiFormatter<User>>(usr) {
        }).build();
    }
    @RequestMapping(value = "/book_event", method = RequestMethod.POST)
    public ResponseEntity<?> book_event(@RequestBody BookingRequestObject bookingRequestObject) throws SQLException {
    	SecurityContext context = SecurityContextHolder.getContext();
		String userName = context.getAuthentication().getName();
		BasicUserDetails user = userDao.getUserByUserName(userName);
		bookingRequestObject.setUserId((int) user.getId());
		bookingRequestObject.setEmail(user.getEmail());
		bookingRequestObject.setPhone(user.getPhone());
		bookingRequestObject.setName(userName);
    	PaymentRequestValidator paymentRequestValidator = new PaymentRequestValidator();
    	if(paymentRequestValidator.validate(bookingRequestObject)) {
    		BookEventDao book = new BookEventDaoImpl();
    		if(bookingRequestObject.getPriceDetail().size() == 1 && bookingRequestObject.getPriceDetail().get(0).getQuantity() == 1 && bookingRequestObject.getPriceDetail().get(0).getPlayerNames().size() ==1) {
    			try {
    				//ApiFormatter<String> success = ServiceUtil.convertToSuccessResponse("success");
    				//return ResponseEntity.status(HttpStatus.OK).body(success);
					return book.bookSingleTicket(bookingRequestObject,true);
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}else{
				try {
					return book.bookSingleTicket(bookingRequestObject, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    		
    	}

		MyErrors error = new MyErrors("Technical Problem");
		//ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 500);
       // return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
       // }).build();
        
		ApiFormatter<MyErrors> success = ServiceUtil.convertToSuccessResponse(error);
		return ResponseEntity.status(HttpStatus.OK).body(success);
    }
    
    @ApiOperation("verify OTP request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @POST
    @Path("/verify_otp")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verify_otp(@RequestBody OTPContent otpContent) throws SQLException {
        SMSService smsService = new SMSService();
        String Register = smsService.verifyOTP(otpContent.getSessionDetails(), otpContent.getOtp(), otpContent.getPhone());
        if(Register.equals("ERROR")){
			MyErrors error = new MyErrors("Technical Problem");
        	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 500);
            return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
            }).build();
        }
        otpContent.setStatus(Register);
    	ApiFormatter<OTPContent>  usr= ServiceUtil.convertToSuccessResponse(otpContent);
        return Response.ok(new GenericEntity<ApiFormatter<OTPContent>>(usr) {
        }).build();
    }
    
    
    @ApiOperation("instamojo payment user")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @POST
    @Path("/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response payment(@RequestBody Order orderObj) throws SQLException {
        Instamojo api = null;
        PaymentOrder order = new PaymentOrder();
        order.setName(orderObj.getName());
        order.setEmail(orderObj.getEmail());
        order.setPhone(orderObj.getPhone());
        order.setDescription(orderObj.getDesc());
        order.setCurrency(orderObj.getCurrency());
        order.setAmount(orderObj.getAmount());
        order.setRedirectUrl(orderObj.getRedirectUrl());
        order.setWebhookUrl(orderObj.getWebhook());
        order.setTransactionId(orderObj.getTransactionId().toString());
        
        try {
            // gets the reference to the instamojo api
            api = InstamojoClient.getInstamojoClient();
        } catch (Exception e) {

        }
        boolean isOrderValid = order.validate();

        if (isOrderValid) {
            ApiFormatter<CreatePaymentOrderResponse> paymentOrderResponse;
            try {
                CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);
                // print the status of the payment order.
                //System.out.println(createPaymentOrderResponse.getPaymentOrder().toString());
               // createPaymentOrderResponse.getJsonResponse()
                //paymentOrderResponse = ServiceUtil.convertToSuccessResponse(createPaymentOrderResponse.toString());
                return Response.ok(createPaymentOrderResponse).build();
            } catch (Exception e) {

                if (order.isTransactionIdInvalid()) {
                    System.out.println("Transaction id is invalid. This is mostly due to duplicate  transaction id.");
                }
                if (order.isCurrencyInvalid()) {
                    System.out.println("Currency is invalid.");
                }
            }
        } else {
            // inform validation errors to the user.
            if (order.isTransactionIdInvalid()) {
                System.out.println("Transaction id is invalid.");
            }
            if (order.isAmountInvalid()) {
                System.out.println("Amount can not be less than 9.00.");
            }
            if (order.isCurrencyInvalid()) {
                System.out.println("Please provide the currency.");
            }
            if (order.isDescriptionInvalid()) {
                System.out.println("Description can not be greater than 255 characters.");
            }
            if (order.isEmailInvalid()) {
                System.out.println("Please provide valid Email Address.");
            }
            if (order.isNameInvalid()) {
                System.out.println("Name can not be greater than 100 characters.");
            }
            if (order.isPhoneInvalid()) {
                System.out.println("Phone is invalid.");
            }
            if (order.isRedirectUrlInvalid()) {
                System.out.println("Please provide valid Redirect url.");
            }

            if (order.isWebhookInvalid()) {
                System.out.println("Provide a valid webhook url");
            }
        }
        return Response.ok("{\"message\":\"success\"}").build();
    }

    @RequestMapping(value = "/payment_status", method = RequestMethod.GET)
    public ResponseEntity<?> paymentStatus(@QueryParam("paymentId") String paymentId) throws SQLException {
    	Instamojo api = null;
        try {
            // gets the reference to the instamojo api
            api = InstamojoClient.getInstamojoClient();
        } catch (Exception e) {

        }
        PaymentOrderDetailsResponse paymentOrderDetailsResponse = null;
		try {
			paymentOrderDetailsResponse = api.getPaymentOrderDetails(paymentId);

		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // print the status of the payment order.
//		
//        System.out.println(paymentOrderDetailsResponse.getStatus().toString());
//		MyErrors error = new MyErrors(paymentOrderDetailsResponse.getStatus().toString());
//    	ApiFormatter<MyErrors>  err= ServiceUtil.convertToSuccessResponse(error);
//        return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
//        }).build();
        
		MyErrors err = new MyErrors(paymentOrderDetailsResponse.getStatus().toString());
		ApiFormatter<MyErrors> success = ServiceUtil.convertToSuccessResponse(err);
		return ResponseEntity.status(HttpStatus.OK).body(success);
    }
    @ApiOperation("instamojo webhook")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @POST
    @Path("/instamojo_webhook")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void instamojoWebhook(@FormParam("status") String status, @FormParam("payment_id") String paymentId, @FormParam("id") String id, @FormParam("transaction_id") String transactionId, @FormParam("mac") String mac, @FormParam("amount") String amount, @FormParam("buyer_phone") String phone) {
    	try{
    		BookEventDao book = new BookEventDaoImpl();
    		//
        	Instamojo api = null;
            try {
                // gets the reference to the instamojo api
                api = InstamojoClient.getInstamojoClient();
            } catch (Exception e) {

            }
            PaymentOrderDetailsResponse paymentOrderDetailsResponse = null;
    		try {
    			paymentOrderDetailsResponse = api.getPaymentOrderDetails(id);

    		} catch (ConnectionException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		if(book.UpdateStatusFromWbhook(id,paymentOrderDetailsResponse.getStatus().toString())){
    			LOGGER.info("SUCCESSFULLY UPDATED STATUS FOR ID " + id);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    @ApiOperation("get Cities")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/cities")
    @Produces(MediaType.APPLICATION_JSON)
    public Response city_details() throws SQLException {
    	CityDao ct = new CityDaoImpl();
        return ct.getAllCities();
    }
    @ApiOperation("get sports")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/sports")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sports_details() throws SQLException {
       SportsDao spt = new SportsDaoImpl();
        return spt.getAllSports();
    }
}
