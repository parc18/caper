package com.khelacademy.daoImpl;

import com.khelacademy.dao.EventDao;
import com.khelacademy.www.pojos.ApiFormatter;
import com.khelacademy.www.pojos.Event;
import com.khelacademy.www.services.ServiceUtil;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventDaoImpl implements EventDao{
    ApiFormatter<List<Event>> eventResponse;
    @Override
    public Response getAllEvents() {
        Event event = new Event();
        Date now = new Date();

        event.setDate(now);
        event.setDescription("Humans are visual creatures. A captivating, interesting picture can help tell the story and promote your event. It might be a photograph of people participating in a previous similar event (and everyone loves a picture of cute kids doing science), it might be related to the topic of the event (such as an astronomy photograph or a microscope image of crystal structures) or it could be a photograph of a drawcard speaker.");
        event.setEventId(1);
        event.setEventIdValue(1);
        event.setEventVenue("marathahalli");
        event.setEventType(1);
        event.setCity("Bangalore");
        event.setEventName("Badminton");
        event.setImgUrl("https://www.scienceweek.net.au/wp-content/uploads/2015/08/Siouxsie-Wiles-249x300.jpg");
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

    @Override
    public Response getEventByCity() {
        return null;
    }
}
