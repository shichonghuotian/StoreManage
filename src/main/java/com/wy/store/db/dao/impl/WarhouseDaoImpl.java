package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.Category;
import com.wy.store.domain.Warehouse;

@Component
public class WarhouseDaoImpl implements WarhouseDao {

	Dao<Warehouse, Long> dao;

	public WarhouseDaoImpl() {
		// TODO Auto-generated constructor stub

		try {
			dao = DaoManager.createDao(StoreDB.getConnectionSource(), Warehouse.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean add(Warehouse warehouse) {
		int b = 0;
		try {
			b = dao.create(warehouse);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return b == 1;

	}

	@Override
	public boolean addList(List<Warehouse> list) {

		try {
			return dao.create(list) == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	@Override
	public List<Warehouse> getAll() {
		// TODO Auto-generated method stub

		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	public Warehouse getWarehouse(String name) {

		List<Warehouse> warehouses = null;
		try {
			warehouses = dao.queryForEq("name", name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (warehouses != null && warehouses.size() > 0) {

			return warehouses.get(0);
		}

		return null;
	}

	@Override
	public boolean isExist(String name) {
		// TODO Auto-generated method stub
		return getWarehouse(name) != null;
	}

	@Override
	public boolean delete(Warehouse warehouse) {
		// TODO Auto-generated method stub
		try {
			dao.delete(warehouse);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
