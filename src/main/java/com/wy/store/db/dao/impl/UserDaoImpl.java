package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.wy.store.db.dao.UserDao;
import com.wy.store.db.dao.UserFingerDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.Device;
import com.wy.store.domain.User;
import com.wy.store.domain.UserFinger;

@Component
public class UserDaoImpl implements UserDao{

	
	Dao<User, Long> dao;
	public UserDaoImpl() {
		// TODO Auto-generated constructor stub

		try {
			dao = DaoManager.createDao(StoreDB.getConnectionSource(), User.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean addUser(User user) {
		int b = 0;
		try {
			b = dao.create(user);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return b == 1;

	}

	public List<User> getAllUser() {
		List<User> list = null;
		try {
			list = dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public boolean isExist(String userId) {


		User user = getUser(userId);
		
		return user != null;
	}

	public User getUser(String userId) {
		try {
            QueryBuilder<User, Long> builder =    dao.queryBuilder();
            Where<User, Long> where = builder.where();
            where.eq("u_id", userId);
            
            PreparedQuery<User> preparedQuery = builder.prepare();

			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<User> getUserListLikeUserId(String likeUserId) {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		try {

//			mDao.queryBuilder().where().like("LastName", "A%").query()
			users = dao.queryBuilder().where().like("u_id", "%"+likeUserId+"%").query();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		
		return users;
		
	}

	@Override
	public List<User> getUserListLikeUserName(String likeUserName) {
		// TODO Auto-generated method stub
		List<User> users = new ArrayList<>();
		try {

//			mDao.queryBuilder().where().like("LastName", "A%").query()
			users = dao.queryBuilder().where().like("name", "%"+likeUserName+"%").query();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		
		return users;
		
	}

	@Override
	public User getUserOfFingerId(String fingerId) {
		// TODO Auto-generated method stub
		
		List<User> users = null;
		try {
			users = dao.queryForEq("finger_id", fingerId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (users != null && users.size() > 0) {

			return users.get(0);
		}
		
		return null;
	}

	@Override
	public boolean deleteUser(User user) {
		// TODO Auto-generated method stub
		try {
			UserFingerDao userFingerDao = new UserFingerDaoImpl();
			
			UserFinger userFinger = userFingerDao.getUserFinger(user.getFingerId());
			if(userFinger!=null) {
				userFingerDao.delete(userFinger);
			}
			
			dao.delete(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
