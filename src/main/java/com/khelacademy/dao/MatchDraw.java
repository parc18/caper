package com.khelacademy.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.khelacademy.document.MatchFixture;
import com.khelacademy.www.pojos.DrawHelper;
import com.khelacademy.www.pojos.User;

public interface MatchDraw {
	Map<String, List<User>> groupPlayers(Integer eventId);
	MatchFixture makeFixture(Integer eventId, Map<String, List<User>> groups) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
	List<DrawHelper> processFixture(String catUnderX, Map<String,List<User>> personByUnderX) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
