package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.wy.store.db.dao.ManagerDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.Manager;
import com.wy.store.domain.User;

public class ManagerDaoImpl implements ManagerDao{

	Dao<Manager, Long> dao;
	
	public ManagerDaoImpl() {
		try {
			dao = DaoManager.createDao(StoreDB.getConnectionSource(), Manager.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}
	
	@Override
	public boolean addMagager(Manager manager) {
		// TODO Auto-generated method stub
		int b = 0;
		try {
			b = dao.create(manager);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return b == 1;
	}

	@Override
	public List<Manager> getAllMagagers() {
		List<Manager> list = null;
		try {
			list = dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Manager getManager(String name) {
		// TODO Auto-generated method stub
		
		List<Manager> users = null;
		try {
			users = dao.queryForEq("name", name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (users != null && users.size() > 0) {

			return users.get(0);
		}
		
		return null;	}

	@Override
	public boolean isExist(String name) {
		// TODO Auto-generated method stub
		return getManager(name) != null;
	}

}
