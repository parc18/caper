package com.khelacademy.daoImpl;

import com.khelacademy.dao.EventDao;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.Event;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.DBArrow;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDaoImpl implements EventDao{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    DBArrow SQLArrow = DBArrow.getArrow();
    
    ApiFormatter<List<Event>> eventResponse;
    @Override
    public Response getAllEvents() {
        Event event = new Event();
        Date now = new Date();
        event.setDate(now);
        event.setDescription("Humans are visual creatures. A captivating, interesting picture can help tell the story and promote your event. It might be a photograph of people participating in a previous similar event (and everyone loves a picture of cute kids doing science), it might be related to the topic of the event (such as an astronomy photograph or a microscope image of crystal structures) or it could be a photograph of a drawcard speaker.");
        event.setEventId(1);
        event.setEventVenue("marathahalli");
        event.setEventType(1);
        event.setCity("Bangalore");
        event.setEventName("Badminton");
        event.setOrganizers(new String[] {"LODHA Group", "DOFF"});
        event.setSponsers(new String[] {"RIL", "TATA Group"});
        event.setPrice(999);
        List<Event> lst = new ArrayList<Event>();
        lst.add(event);
        lst.add(event);
        eventResponse = ServiceUtil.convertToSuccessResponse(lst);
        return Response.ok(new GenericEntity<ApiFormatter<List<Event>>>(eventResponse) {
        }).build();
    }

    public Response getEventByCity(String city) {

    	PreparedStatement statement=null;
    	try {
            if(city.equals("all")){
            	statement = SQLArrow.getPreparedStatement("SELECT  * from event");
            }else{
            	statement = SQLArrow.getPreparedStatement("SELECT  * from event  WHERE event_city=?");
    			statement.setString(1, city);	
            }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	System.out.println(statement.toString());
    	List<Event> allEvents = new ArrayList<Event>();
    	//ApiFormatter<List<Event>> eventResponse = ServiceUtil.convertToSuccessResponse(allUser);
        try (ResultSet rs = SQLArrow.fire(statement)) {
        	Event event = new Event();
        	while (rs.next()) {
            event.setDate(rs.getDate("eventdate"));
        	System.out.println(rs.getString("description"));
            event.setDescription(rs.getString("description"));
            event.setEventId(rs.getInt("event_id"));
            event.setEventVenue(rs.getString("venue"));
            event.setEventType(rs.getInt("event_type"));
            event.setCity(rs.getString("event_city"));
            event.setEventName(rs.getString("event_name"));
            event.setOrganizers(new String[] {"LODHA Group", "DOFF"});
            event.setSponsers(new String[] {"RIL", "TATA Group"});
            event.setPrice(0);
            allEvents.add(event);
        	}
        }catch(Exception e){
        	e.printStackTrace();
        	LOGGER.error("ERROR IN GETTING EVENTS DETAILE FOR CITY: " + city);
        	MyErrors error = new MyErrors(e.getMessage());
        	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 500);
            return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
            }).build();
        }
    	ApiFormatter<List<Event>>  events= ServiceUtil.convertToSuccessResponse(allEvents);
        return Response.ok(new GenericEntity<ApiFormatter<List<Event>>>(events) {
        }).build();
    }
}
