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
import com.khelacademy.dao.UserDao;
import com.khelacademy.daoImpl.EventDaoImpl;
import com.khelacademy.daoImpl.UserDaoImpl;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.services.ServiceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    @ApiOperation("Ping request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/ping")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ping() {
        System.out.print(System.getenv("C_PASSWORD"));
        return Response.ok("{\"message\":\"pong\"}").build();
    }

    @ApiOperation("Home request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/home")
    @Produces(MediaType.APPLICATION_JSON)
    public Response home() {

        return Response.ok("{\n" +
                "    \"banners\": [{\n" +
                "            \"eventId\": 1,\n" +
                "            \"images\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\",\n" +
                "            \"url\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"eventId\": 1,\n" +
                "            \"images\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\",\n" +
                "            \"url\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"eventId\": 1,\n" +
                "            \"images\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\",\n" +
                "            \"url\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"widgets\": [{\n" +
                "            \"eventId\": 1,\n" +
                "            \"images\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\",\n" +
                "            \"url\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\",\n" +
                "            \"name\": \"badminton\",\n" +
                "            \"organiser\": \"khelacademy\",\n" +
                "            \"sponsors\": [{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"victor\"\n" +
                "            }],\n" +
                "            \"eventDate\": \"20-Jan-2018\",\n" +
                "            \"regDate\": \"dd-mm-yyyy\",\n" +
                "            \"minFee\": 777,\n" +
                "            \"maxFee\": 999,\n" +
                "            \"venue\": \"delhi\",\n" +
                "            \"eventType\": \"BADMINTON\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"eventId\": 1,\n" +
                "            \"images\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\",\n" +
                "            \"url\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\",\n" +
                "            \"name\": \"badminton\",\n" +
                "            \"organiser\": \"khelacademy\",\n" +
                "            \"sponsors\": [{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"victor\"\n" +
                "            }],\n" +
                "            \"eventDate\": \"20-Jan-2018\",\n" +
                "            \"regDate\": \"dd-mm-yyyy\",\n" +
                "            \"minFee\": 777,\n" +
                "            \"maxFee\": 999,\n" +
                "            \"venue\": \"delhi\",\n" +
                "            \"eventType\": \"BADMINTON\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"eventId\": 1,\n" +
                "            \"images\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\",\n" +
                "            \"url\": \"https://s3-eu-west-1.amazonaws.com/rb-cms/rbv5/dev/uploads/cover_images/16279fd1b67a1842f5ca875d7d18326e8e6f/i1080x475.jpg\",\n" +
                "            \"name\": \"badminton\",\n" +
                "            \"organiser\": \"khelacademy\",\n" +
                "            \"sponsors\": [{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"victor\"\n" +
                "            }],\n" +
                "            \"eventDate\": \"20-Jan-2018\",\n" +
                "            \"regDate\": \"dd-mm-yyyy\",\n" +
                "            \"minFee\": 777,\n" +
                "            \"maxFee\": 999,\n" +
                "            \"venue\": \"delhi\",\n" +
                "            \"eventType\": \"BADMINTON\"\n" +
                "        }\n" +
                "    ]\n" +
                "}").build();
    }
    @ApiOperation("get user request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/user_details")
    @Produces(MediaType.APPLICATION_JSON)
    public Response user_details() throws SQLException {
        UserDao user = new UserDaoImpl();
        return user.getUserById(1);
    }

    @ApiOperation("get event request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @GET
    @Path("/events")
    @Produces(MediaType.APPLICATION_JSON)
    public Response events() throws SQLException {
        EventDao event = new EventDaoImpl();
        return event.getAllEvents();
    }
    @ApiOperation("post user request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @POST
    @Path("/user_register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response user_registration(@RequestBody User userDetails) throws SQLException {
        UserDao user = new UserDaoImpl();
        user.registerUser(userDetails);
        return Response.ok("{\"message\":\"success\"}").build();
    }

    @ApiOperation("instamojo payment user")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @POST
    @Path("/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response payment(@RequestBody PaymentOrder order) throws SQLException {
        Instamojo api = null;

        try {
            // gets the reference to the instamojo api
            api = InstamojoImpl.getApi("[CLIENT_ID]", "[CLIENT_SECRET]", "[API_ENDPOINT]", "[AUTH_ENDPOINT]");
        } catch (Exception e) {

        }
        boolean isOrderValid = order.validate();

        if (isOrderValid) {
            ApiFormatter<CreatePaymentOrderResponse> paymentOrderResponse;
            try {
                CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);
                // print the status of the payment order.
                System.out.println(createPaymentOrderResponse.getPaymentOrder().getStatus());
                paymentOrderResponse = ServiceUtil.convertToSuccessResponse(createPaymentOrderResponse);
                return Response.ok(paymentOrderResponse).build();
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
