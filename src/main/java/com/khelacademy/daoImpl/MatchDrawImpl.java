package com.khelacademy.daoImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.khelacademy.dao.MatchDraw;
import com.khelacademy.document.MatchFixture;
import com.khelacademy.document.PoolFixture;
import com.khelacademy.document.UserVersus;
import com.khelacademy.repository.MatchRepo;
import com.khelacademy.www.pojos.DrawHelper;
import com.khelacademy.www.pojos.Fixtures;
import com.khelacademy.www.pojos.User;
import com.khelacademy.www.utils.ByesContants;
import com.khelacademy.www.utils.DBArrow;
import com.khelacademy.www.utils.EnglishNumberToWords;

@Component
public class MatchDrawImpl implements MatchDraw {
	@Autowired
	MongoTemplate mongoTemplate;	
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    static int counterForPools = 1;
	static int startIndex = 0;
	static int endindex = 0;
    DBArrow SQLArrow = DBArrow.getArrow();
	@Override
	public Map<String, List<User>> groupPlayers(Integer eventId) {
		Integer i = null;
        Map<String, List<User>> groupByCategotyMap = new HashMap<String, List<User>>();
        PreparedStatement statement=null;
        try {
            if(eventId != null){
                statement = SQLArrow.getPreparedStatement("select * from temp_users as t inner join booking as b on t.booking_id = b.booking_id inner join ticket as tk on b.booking_id = tk.booking_id inner join price as p on t.price_id = p.price_id and b.status = ? AND tk.event_id =?");
                statement.setInt(2, eventId);
                statement.setString(1, "completed");
            }else{
                LOGGER.error("ERROR IN PREPARING STATEMENT FOR EVENT BASED USER FOR THE EVENTID : " + eventId);
            }
            try (ResultSet rs = SQLArrow.fire(statement)) {
                while (rs.next()) {
                    User u = new User();
                    u.setFirstName(rs.getString("NAME"));
                    u.setUserId(rs.getInt("USER_ID"));
                    u.setGameId(rs.getInt("game_user_id"));
                    u.setUnderX(rs.getString("price_name"));
                    if(groupByCategotyMap.get(String.valueOf(rs.getInt("category_id"))) == null){
                        groupByCategotyMap.put(String.valueOf(rs.getInt("category_id")), new ArrayList<User>());
                    }
                    groupByCategotyMap.get(String.valueOf(rs.getInt("category_id"))).add(u);
                }
            }catch(Exception e){
                LOGGER.error("ERROR IN GETTING EVENT'S User DETAILE FOR EVENTID: " + eventId);
                return null;

            }
        } catch (SQLException e1) {
            LOGGER.error("ERROR IN PREPARING STATEMENT FOR EVENT BASED PRICE FOR THE EVENTID : " + eventId);
        }
		// TODO Auto-generated method stub
		return groupByCategotyMap;
	}

	@Override
	public MatchFixture makeFixture(Integer eventId, Map<String, List<User>> groups) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Map<String, List<Fixtures>> finalFixture = new HashMap<String, List<Fixtures>>();
        counterForPools=0;
        for (Map.Entry<String, List<User>> entry : groups.entrySet()) {

            Map<String,List<User>> personByUnderX = new HashMap<>();

            //Map<String, Fixtures> fixture = new HashMap<String, Fixtures>();

            personByUnderX = entry.getValue().stream()
                    .collect(Collectors.groupingBy(User::getUnderX));

            List<DrawHelper> dU = processFixture(entry.getKey(), personByUnderX);
            startIndex = 0;
            endindex = 0;
            counterForPools = 1;

            for(DrawHelper d : dU) {
                finalFixture.put(d.getCategotyPlusUnderX(), getFinalFixure(d.getUser(), 1));
            }
        }
        MatchFixture fixture = new MatchFixture();
        fixture.setEventId(eventId);
        Map<String, Map<String, List<UserVersus>>> categoryWise = new HashMap<>();
        for (Map.Entry<String, List<Fixtures>> entry : finalFixture.entrySet()) {
        	Map<String, List<UserVersus>> poolNameWithUserVersus = new HashMap<>();
        	
        	List<UserVersus> userVerses = new ArrayList<>();
        	
        	if(true) {
        		
        		for(Fixtures f: entry.getValue()) {
        			UserVersus userV = new UserVersus();
        			userV.setRound(1);
        			userV.setTotalRound(entry.getValue().size());
        			userV.setUser1(f.getUser1());
        			userV.setUser2(f.getUser2());
        			userVerses.add(userV);
        		}
        		// putting pool
        		if(entry.getKey().charAt(0) == 'G')
        			poolNameWithUserVersus.put(entry.getKey().substring(2,3), userVerses);
        		else
        			poolNameWithUserVersus.put("NO_POOL", userVerses);
        		// putting categories
        		if(categoryWise.get(entry.getKey().substring(4)) != null) {
        			if(entry.getKey().charAt(0) == 'G')
        				categoryWise.get(entry.getKey().substring(4)).put(entry.getKey().substring(2,3), userVerses);
        			else
        				categoryWise.get(entry.getKey().substring(0)).put("NO_POOL", userVerses);
        		}else {
        			if(entry.getKey().charAt(0) == 'G')
        				categoryWise.put(entry.getKey().substring(4), poolNameWithUserVersus);
        			else
        				categoryWise.put(entry.getKey().substring(0), poolNameWithUserVersus);

        		}
        	}
        }
        fixture.setCategoryWise(categoryWise);
        
        mongoTemplate.save(fixture);
		// TODO Auto-generated method stub
		return fixture;
	}

    private List<Fixtures> getFinalFixure(List<User> users, int level) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Fixtures> fx = new ArrayList<Fixtures>();
        int totalPlayerInCurrentCategory = users.size();
        String x= EnglishNumberToWords.convert(totalPlayerInCurrentCategory).toUpperCase();
        //// do the hard work later
        ByesContants bc = new ByesContants();
        String numberInString = "get"+x;
        final int[] byeIndexArray = getMethodValue(bc, numberInString);//ByesContants.FIFTYEIGHT;
        int ByesIndexIdentifier=0;

        if(byeIndexArray[0]==-1){
            for(int i=0; i< totalPlayerInCurrentCategory; i=i+2){
                Fixtures f = new Fixtures();
                try {
                    f.setUser1(users.get(i));
                }catch (Exception e){
                    f.setUser1(null);
                }
                try {
                    f.setUser2(users.get(i+1));
                }catch (Exception e){
                    f.setUser2(null);
                }
                fx.add(f);

            }


            return fx;
        }
        int perfectPowerOfTwo = EnglishNumberToWords.nextPowerOf2(totalPlayerInCurrentCategory);

        int userSelectorArrayIndex= 0; ByesIndexIdentifier = 0;

        int k=0;

        while(k<perfectPowerOfTwo){
            Fixtures f = new Fixtures();
            if(ByesIndexIdentifier < byeIndexArray.length && (k==(byeIndexArray[ByesIndexIdentifier])-1)){
                f.setUser1(null);
                k++; ByesIndexIdentifier++;
            }else {
                f.setUser1(users.get(userSelectorArrayIndex++));
                k++;
            }

            if(ByesIndexIdentifier < byeIndexArray.length && (k==(byeIndexArray[ByesIndexIdentifier])-1)){
                f.setUser2(null);
                k++; ByesIndexIdentifier++;
            }else {
                f.setUser2(users.get(userSelectorArrayIndex++));
                k++;
            }
            f.setLevel(level);
            fx.add(f);
        }


        return fx;
    }

    @Override
    public List<DrawHelper> processFixture(String catSex, Map<String, List<User>> personByUnderX) {
        List<DrawHelper> dU = new ArrayList<DrawHelper>();
        for (Map.Entry<String, List<User>> underX : personByUnderX.entrySet()) {
            int totalPlayerInSexWise = underX.getValue().size();
            if( totalPlayerInSexWise > ByesContants.poolSize) {
            	int totalGroup = (int) Math.ceil((double)totalPlayerInSexWise/(double)ByesContants.poolSize);
            	//int groupSize = (int)Math.floor((double)totalPlayerInSexWise/(double)ByesContants.poolSize);
            	startIndex = 0;
            	endindex = ByesContants.poolSize;
                for(int i=0; i<totalGroup; i++) {
                	if(i == totalGroup - 1) {
                		dU.addAll(processFixture("G-"+ByesContants.POOL[counterForPools++]+"-"+catSex, new HashMap<String, List<User>>(){{
                            put(underX.getKey() , underX.getValue().subList(startIndex,totalPlayerInSexWise));
                        }}));
                	}else {
                        dU.addAll(processFixture("G-"+ByesContants.POOL[counterForPools++]+"-"+catSex, new HashMap<String, List<User>>(){{
                            put(underX.getKey() , underX.getValue().subList(startIndex,endindex));
                        }}));
                	}
 
                    startIndex += ByesContants.poolSize;
                    endindex += ByesContants.poolSize;
                }
            	
//                int splitOnIndex = underX.getValue().size() / 2;
//                dU.addAll(processFixture(catSex, new HashMap<String, List<User>>(){{
//                    put(ByesContants.POOL[counterForPools++] +"-"+ underX.getKey() , underX.getValue().subList(0,splitOnIndex));
//                }}));
//                dU.addAll(processFixture(catSex, new HashMap<String, List<User>>(){{
//                    put(ByesContants.POOL[counterForPools++] +"-"+ underX.getKey() , underX.getValue().subList(splitOnIndex,totalPlayerInSexWise));
//                }}));
            }else {
                DrawHelper d = new DrawHelper();
                d.setCategotyPlusUnderX(catSex+underX.getKey());
                d.setUser(underX.getValue());
                dU.add(d);
            }
        }
        return dU;
    }
    
    public int [] getMethodValue(Object o, String methodName) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class<?> clazz = o.getClass();
        String abc = "getFOUR";
        Method fooMethod = clazz.getMethod(methodName);
        Object fooObj = clazz.newInstance();
        int[] bar = (int[]) fooMethod.invoke(fooObj);
        for(Field field : clazz.getDeclaredFields()) {
            //you can also use .toGenericString() instead of .getName(). This will
            //give you the type information as well.

            System.out.println(field.getName());
        }
        return bar;
    }

}
