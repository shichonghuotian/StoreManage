package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.jdbc.StoreDB;
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
		
		try {
            QueryBuilder<Warehouse, Long> builder =    dao.queryBuilder();
            Where<Warehouse, Long> where = builder.where();
            where.eq("name", name);
            
            PreparedQuery<Warehouse> preparedQuery = builder.prepare();

			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
