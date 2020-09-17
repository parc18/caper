package com.khelacademy.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.khelacademy.dao.GamesDao;
import com.khelacademy.model.BasicUserDetails;
import com.khelacademy.model.Games;

@Component
@Transactional
public class GamesDaoImpl implements GamesDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Games getGameById(Integer gameId) {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "FROM Games E WHERE E.gameId =:gameId";
		@SuppressWarnings("unchecked")
		Query<Games> query = session.createQuery(hql);
		query.setInteger("gameId", gameId);
		List<Games> results = query.list();
		if (results != null && results.size() == 1) {
			return results.get(0);
		}
		return null;
	}

}
