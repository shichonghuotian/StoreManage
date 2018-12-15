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
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.Category;
import com.wy.store.domain.User;

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
	
	@Override
	public List<Category> getCategoryList(long parentId) {
		
		List<Category> list = new ArrayList<>();
		
		try {

//			mDao.queryBuilder().where().like("LastName", "A%").query()
			list = dao.queryBuilder().where().eq("parentCategory_id", parentId).query();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		
		return list;
		
	}


	public Category getCategory(String name) {
		
		try {
            QueryBuilder<Category, Long> builder =    dao.queryBuilder();
            Where<Category, Long> where = builder.where();
            where.eq("name", name);
            
            PreparedQuery<Category> preparedQuery = builder.prepare();

			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Category getCategory(long parentId,String name) {
		
		try {
            QueryBuilder<Category, Long> builder =    dao.queryBuilder();
            Where<Category, Long> where = builder.where();
            where.eq("parentCategory_id", parentId).and().eq("name", name);
            
            PreparedQuery<Category> preparedQuery = builder.prepare();

			return dao.queryForFirst(preparedQuery);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Category getCategoryByCode(long parentId,String code) {
		
		try {
            QueryBuilder<Category, Long> builder =    dao.queryBuilder();
            Where<Category, Long> where = builder.where();
            where.eq("parentCategory_id", parentId).and().eq("code", code);
            
            PreparedQuery<Category> preparedQuery = builder.prepare();

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
