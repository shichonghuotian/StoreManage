package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.wy.store.db.dao.UserImageDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.User;
import com.wy.store.domain.UserImage;

public class UserImageDaoImpl implements UserImageDao{

	Dao<UserImage, Long> dao;


	public UserImageDaoImpl() throws SQLException {
		// TODO Auto-generated constructor stub
		
		dao = DaoManager.createDao(StoreDB.getConnectionSource(), UserImage.class);
	}
	
	public void addImage(UserImage image) {
		
		
		try {
			dao.create(image);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<UserImage> getImageList(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserImage> getImageList(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
