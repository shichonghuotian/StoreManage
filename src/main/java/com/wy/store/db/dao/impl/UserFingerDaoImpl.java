package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.wy.store.db.dao.UserFingerDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.UserFinger;

public class UserFingerDaoImpl implements UserFingerDao{

	Dao<UserFinger, Long> dao;
	public UserFingerDaoImpl() {
		// TODO Auto-generated constructor stub

		try {
			dao = DaoManager.createDao(StoreDB.getConnectionSource(), UserFinger.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean addFinger(UserFinger finger) {
		// TODO Auto-generated method stub
		int b = 0;
		try {
			b = dao.create(finger);
			
			System.out.println("add finger");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return b == 1;
		
	}

	@Override
	public List<UserFinger> getAllUserFingers() {
		List<UserFinger> list = null;
		try {
			list = dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public UserFinger getUserFinger(long id) {
		try {
            QueryBuilder<UserFinger, Long> builder =    dao.queryBuilder();
            Where<UserFinger, Long> where = builder.where();
            where.eq("finger_id", id);
            
            PreparedQuery<UserFinger> preparedQuery = builder.prepare();

			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		return null;
	}

	@Override
	public long getNextId() {
		// TODO Auto-generated method stub
//
//	     try {
////			GenericRawResults<String[]> rawResults =
////				       dao.queryRaw(
////				         "SELECT seq + 1 FROM main.sqlite_sequence WHERE name = 't_user_finger'");
////			
////			System.out.println("raw = " +rawResults.getResults().get(0)[0]);
////			
////			return Long.parseLong(rawResults.getResults().get(0)[0]) ;
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		QueryBuilder<UserFinger, Long> qBuilder = dao.queryBuilder();
		
		qBuilder.orderBy("finger_id",false); // false for descending order
		qBuilder.limit((long) 1);
		try {
			List<UserFinger> listOfOne = qBuilder.query();
			
			System.out.println(listOfOne);
			if(listOfOne.isEmpty()) {
				
				return 1;
			}else {
				UserFinger finger = listOfOne.get(0);
				return finger.getFingerId() +1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		

		
//		

		return 1;
	}

	
}
