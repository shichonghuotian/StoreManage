package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.Category;
import com.wy.store.domain.User;
import com.wy.store.domain.UserImage;

@Component
public class CategoryDaoImpl implements CategoryDao{

	
	Dao<Category, Long> dao;


	public CategoryDaoImpl() {
		// TODO Auto-generated constructor stub
		
		try {
			dao = DaoManager.createDao(StoreDB.getConnectionSource(), Category.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public boolean add(Category category) {
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
	public List<Category> getAll() {
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
	public boolean addList(List<Category> list) {
		try {
			  return	dao.create(list) == 1;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return false;	
			
	}

	public Category getCategory(String name) {
		
		List<Category> categories = null;
		try {
			categories = dao.queryForEq("name", name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (categories != null && categories.size() > 0) {

			return categories.get(0);
		}
		
		return null;
	}

	@Override
	public boolean isExist(String name) {
		// TODO Auto-generated method stub
		Category category = getCategory(name);
		return category != null;
		
	}


	@Override
	public boolean delete(Category category) {
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
