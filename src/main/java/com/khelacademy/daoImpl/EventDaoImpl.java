package com.khelacademy.daoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.khelacademy.dao.EventDao;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.Event;
import com.khelacademy.www.pojos.EventPrice;
import com.khelacademy.www.pojos.EventPriceResponse;
import com.khelacademy.www.pojos.MyErrors;
import com.khelacademy.www.services.ServiceUtil;
import com.khelacademy.www.utils.DBArrow;
import com.khelacademy.www.utils.RedisBullet;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

    public Response getEventByCityId(Integer cityId, Integer gameId) {

    	PreparedStatement statement=null;
    	try {
            if(cityId <=0 && (gameId == null || gameId<=0)){
                statement = SQLArrow.getPreparedStatement("SELECT  * from event  where eventdate > now()");
            }else if(cityId>0 &&  (gameId == null || gameId<=0)){
                statement = SQLArrow.getPreparedStatement("SELECT  * from event  WHERE event_city_id=? and eventdate > now()");
                statement.setInt(1, cityId);
            }else if(cityId<=0 &&  (gameId != null || gameId>0)){
                statement = SQLArrow.getPreparedStatement("SELECT  * from event  WHERE event_type = ? and eventdate > now()");
                statement.setInt(1, gameId);
            }else if(cityId>0 &&  (gameId != null || gameId>0)){
                statement = SQLArrow.getPreparedStatement("SELECT  * from event  WHERE event_city_id=? and event_type = ? and eventdate > now()");
                statement.setInt(1, cityId);
                statement.setInt(2, gameId);
            }
		} catch (Exception e) {
			LOGGER.error("ERROR IN PREPARING STATEMENT FOR CITY BASED EVENT FOR THE CITY ID: " + cityId, e);
		}
    	Map<Integer, Event> allEvents = new HashMap<Integer, Event>();
    	//ApiFormatter<List<Event>> eventResponse = ServiceUtil.convertToSuccessResponse(allUser);
        try (ResultSet rs = SQLArrow.fire(statement)) {
        	while (rs.next()) {
        	Event event = new Event();
            event.setDate(rs.getDate("eventdate"));
            JedisPool jedisPool = RedisBullet.getPool();
        	Jedis jedis = jedisPool.getResource();
        	//jedis.get(Integer.toString(rs.getInt("event_id")));
            event.setDescription(jedis.get(Integer.toString(rs.getInt("event_id"))));
            jedis.close();
            jedisPool.close();
            event.setEventId(rs.getInt("event_id"));
            event.setEventVenue(rs.getString("venue"));
            event.setEventType(rs.getInt("event_type"));
            event.setCity(rs.getString("event_city"));
            event.setEventName(rs.getString("event_name"));
            event.setEventImgUrl(rs.getString("img_url"));
            event.setOrganizers(new String[] {"KhelAcademy", "Bflit"});
            event.setSponsers(new String[] {"KhelAcademy", "Bflit"});
            event.setPrice(rs.getInt("start_price"));
            event.setTimings(rs.getString("timings"));
            event.setTimings(rs.getString("timings"));
            event.setStatus(rs.getInt("status"));
            allEvents.put(event.getEventId(),event);
        	}
        }catch(Exception e){
        	e.printStackTrace();
        	LOGGER.error("ERROR IN GETTING EVENTS DETAILE FOR CITY: " + cityId);
        	MyErrors error = new MyErrors(e.getMessage());
        	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 500);
            return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
            }).build();
        }
    	ApiFormatter<Map<Integer,Event>>  events= ServiceUtil.convertToSuccessResponse(allEvents);
        return Response.ok(new GenericEntity<ApiFormatter<Map<Integer, Event>>>(events) {
        }).build();
    }

	@Override
	public Response getEventPrice(Integer eventId) {
    	PreparedStatement statement=null;
    	EventPriceResponse eventPrices = new EventPriceResponse();
    	Map<Integer, List<EventPrice>> groupByCategotyMap = new HashMap<Integer, List<EventPrice>>();
    	eventPrices.setEventId(eventId);
    	List<EventPrice> priceDetails = new ArrayList<EventPrice>();
    	try {
            if(eventId != null){
            	statement = SQLArrow.getPreparedStatement("SELECT  * from price where event_id=?");
            	statement.setInt(1, eventId);
            }else{
            	
            }
            try (ResultSet rs = SQLArrow.fire(statement)) {
            	while (rs.next()) {
            		EventPrice prices = new EventPrice();
            		prices.setPriceId(rs.getInt("price_id"));
            		prices.setPriceAmount(rs.getInt("price_amount"));
            		prices.setCurrency(rs.getString("price_currency"));
            		prices.setDesc(rs.getString("description"));
            		prices.setCategory(rs.getInt("category_id"));   
            		prices.setName(rs.getString("price_name"));
            		priceDetails.add(prices);
            	}
                groupByCategotyMap = priceDetails.stream().collect(Collectors.groupingBy(EventPrice::getCategory));
            }catch(Exception e){
            	LOGGER.error("ERROR IN GETTING EVENT'S PRICE DETAILE FOR EVENTID: " + eventId);
            	MyErrors error = new MyErrors(e.getMessage());
            	ApiFormatter<MyErrors>  err= ServiceUtil.convertToFailureResponse(error, "true", 500);
                return Response.ok(new GenericEntity<ApiFormatter<MyErrors>>(err) {
                }).build();
            }
		} catch (SQLException e1) {
			LOGGER.error("ERROR IN PREPARING STATEMENT FOR EVENT BASED PRICE FOR THE EVENTID : " + eventId);
		}
    	eventPrices.setPriceDetails(groupByCategotyMap);
    	ApiFormatter<EventPriceResponse>  events= ServiceUtil.convertToSuccessResponse(eventPrices);
        return Response.ok(new GenericEntity<ApiFormatter<EventPriceResponse>>(events) {
        }).build();
	}

}
