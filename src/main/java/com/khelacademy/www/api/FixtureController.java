package com.khelacademy.www.api;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khelacademy.daoImpl.MatchDrawImpl;
import com.khelacademy.document.MatchFixture;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.services.ServiceUtil;

import net.bytebuddy.implementation.bytecode.Throw;
@RestController
@CrossOrigin
public class FixtureController {	
	@Autowired
	MatchDrawImpl matchDraw;
	
	@Autowired
	
    private static final Logger LOGGER = LoggerFactory.getLogger(FixtureController.class);
    @RequestMapping(value = "/eventusers", method = RequestMethod.GET)
    public ResponseEntity<?> geteventsUsers(@RequestParam("event_id") Integer eventId){
        
        String err = "";
        try {
        	ApiFormatter<MatchFixture>  events;
        	MatchFixture mf = matchDraw.checkOnceForFixture(eventId);
        	if(mf!=null)
        		events = ServiceUtil.convertToSuccessResponse(mf);
        	else
            	events= ServiceUtil.convertToSuccessResponse(matchDraw.makeFixture(eventId, matchDraw.groupPlayers(eventId)));
            //return Response.ok(new GenericEntity<ApiFormatter<Map<String, List<Fixtures>>>>(events) {
           // }).build();
			//MyErrors error = new MyErrors(e.getMessage());
			//ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (NoSuchMethodException e) {
        	LOGGER.error(e.getMessage());
            err = e.getMessage();
        } catch (InstantiationException e) {
        	LOGGER.error(e.getMessage());
        	err = e.getMessage();
        } catch (IllegalAccessException e) {
        	LOGGER.error(e.getMessage());
        	err = e.getMessage();
        } catch (InvocationTargetException e) {
        	LOGGER.error(e.getMessage());
        	err = e.getMessage();
        }
        MyErrors error = new MyErrors(err);
		ApiFormatter<MyErrors> err1 = ServiceUtil.convertToFailureResponse(error, "true", 406);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err1);
    }
    @RequestMapping(value = "/eventusers-update", method = RequestMethod.GET)
    public ResponseEntity<?> geteventsUsersUpdate(@RequestParam("event_id") Integer eventId){
        
        String err = "";
        try {
        	ApiFormatter<MatchFixture>  events;
        	MatchFixture mf = matchDraw.checkOnceForFixture(eventId);
        	if(mf!=null)	
        		events = ServiceUtil.convertToSuccessResponse(matchDraw.updateFixtureForNextRound(mf));
        	else
        		throw new Exception("No event found");
            //return Response.ok(new GenericEntity<ApiFormatter<Map<String, List<Fixtures>>>>(events) {
           // }).build();
			//MyErrors error = new MyErrors(e.getMessage());
			//ApiFormatter<MyErrors> err = ServiceUtil.convertToFailureResponse(error, "true", 406);
			return ResponseEntity.status(HttpStatus.OK).body(events);
        } catch (NoSuchMethodException e) {
        	LOGGER.error(e.getMessage());
            err = e.getMessage();
        } catch (InstantiationException e) {
        	LOGGER.error(e.getMessage());
        	err = e.getMessage();
        } catch (IllegalAccessException e) {
        	LOGGER.error(e.getMessage());
        	err = e.getMessage();
        } catch (InvocationTargetException e) {
        	LOGGER.error(e.getMessage());
        	err = e.getMessage();
        } catch (Exception e) {
        	e.printStackTrace();
        	LOGGER.error(e.getMessage());
    	err = e.getMessage();
    }
        MyErrors error = new MyErrors(err);
		ApiFormatter<MyErrors> err1 = ServiceUtil.convertToFailureResponse(error, "true", 406);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(err1);
    }
}
