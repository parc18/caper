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
import com.khelacademy.dao.UserDao;
import com.khelacademy.daoImpl.UserDaoImpl;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.DBArrow;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JAX-RS API endpoint.
 */
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

/*    @ApiOperation("post user request")
    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = String.class)})
    @POST
    @Path("/user_register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response user_registration(@QueryParam("firstname") String firstname,
                                      @QueryParam("lastname") String lastname,
                                      @QueryParam("email") String email,
                                      @QueryParam("passcode") String passcode,
                                      @QueryParam("phone") String phone,
                                      @QueryParam("city") String city) throws SQLException {
        try (ResultSet rs = SQLArrow.fire("INSERT INTO user values(, )")) {
            userResponse = ServiceUtil.convertToSuccessResponse(user);
            SQLArrow.relax(rs);
        }
        return Response.ok(new GenericEntity<ApiFormatter<User>>(userResponse) {
        }).build();
    }*/
}
