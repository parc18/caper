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

import com.holonplatform.jaxrs.swagger.annotations.ApiDefinition;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import com.khelacademy.dao.EventDao;
import com.khelacademy.dao.HomeDao;
import com.khelacademy.dao.UserDao;
import com.khelacademy.daoImpl.EventDaoImpl;
import com.khelacademy.daoImpl.HomeDaoImpl;
import com.khelacademy.daoImpl.UserDaoImpl;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.pojos.OTPContent;
import com.khelacademy.www.pojos.Order;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.SMSService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * JAX-RS API endpoint.
 */
@SuppressWarnings("restriction")
@ApiDefinition(docsPath = "/api/docs", title = "Example API", version = "v1", prettyPrint = true)
@Api("Test API")
@Component
@Path("/api")
public class ApiEndpoint {	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiEndpoint.class);
	
    @ApiOperation("Ping request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ping() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException {
    	LOGGER.debug("Server is running Fine Check DB credentials");
        return Response.ok("{\"message\":\"pong\"}").build();
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

    @ApiOperation("get event request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/event")
    @Produces(MediaType.APPLICATION_JSON)
    public Response events(@QueryParam("city_id") Integer cityId) throws SQLException {
        EventDao event = new EventDaoImpl();
        return event.getEventByCityId(cityId);
    }
    
    @ApiOperation("get event Prices request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/event_prices")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eventsPrices(@QueryParam("event_id") Integer eventId) throws SQLException {
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
        System.out.print(orderObj.toString());
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
            api = InstamojoImpl.getApi("test_Ubu7aJMhA9t6fvhnDuhe4ak9oHQP3RCPxtJ", "test_aCqcPrXNeZ0RJBDjx9i8zhU0csN61WNkAmWbP1aOPJMd2UcLm4Z87HkxMjsSGp9CfDDoyVb8fujQpA1ebeAnduTrCDxIXVYQTm2Zr95VJULbbrYzwHcVRa4RBLR", "https://test.instamojo.com/v2/", "https://test.instamojo.com/oauth2/token/");
        } catch (Exception e) {

        }
        boolean isOrderValid = order.validate();

        if (isOrderValid) {
            ApiFormatter<CreatePaymentOrderResponse> paymentOrderResponse;
            try {
                CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);
                // print the status of the payment order.
                System.out.println(createPaymentOrderResponse.getPaymentOrder().toString());
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
}
