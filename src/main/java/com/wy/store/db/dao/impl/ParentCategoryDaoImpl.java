package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.wy.store.db.dao.ParentCategoryDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.Category;
import com.wy.store.domain.ParentCategory;
import com.wy.store.domain.UserFinger;

public class ParentCategoryDaoImpl implements ParentCategoryDao{

	Dao<ParentCategory, Long> dao;


	public ParentCategoryDaoImpl() {
		// TODO Auto-generated constructor stub
		
		try {
			dao = DaoManager.createDao(StoreDB.getConnectionSource(), ParentCategory.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public boolean add(ParentCategory category) {
		// TODO Auto-generated method stub
		int b = 0;
		try {
			b = dao.create(category);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return b == 1;	}


	@Override
	public List<ParentCategory> getAll() {
		// TODO Auto-generated method stub

		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ArrayList<>();	
		
	}


	@Override
	public boolean addList(List<ParentCategory> list) {
		try {
			  return	dao.create(list) == 1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;	
			
	}

	public ParentCategory getCategory(String name) {
		try {
            QueryBuilder<ParentCategory, Long> builder =    dao.queryBuilder();
            Where<ParentCategory, Long> where = builder.where();
            where.eq("name", name);
            
            PreparedQuery<ParentCategory> preparedQuery = builder.prepare();

			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public ParentCategory getCategoryByCode(String code) {
		try {
            QueryBuilder<ParentCategory, Long> builder =    dao.queryBuilder();
            Where<ParentCategory, Long> where = builder.where();
            where.eq("code", code);
            
            PreparedQuery<ParentCategory> preparedQuery = builder.prepare();

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
		ParentCategory category = getCategory(name);
		return category != null;
		
	}
	@Override
	public boolean isExistOfCode(String code) {
		// TODO Auto-generated method stub
		ParentCategory category = getCategoryByCode(code);
		return category != null;
		
	}

	@Override
	public boolean delete(ParentCategory category) {
		// TODO Auto-generated method stub
		try {
			dao.delete(category);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

}
