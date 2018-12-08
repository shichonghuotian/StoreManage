package com.wy.store.db.dao;

import java.util.List;

import com.wy.store.domain.Category;

public interface CategoryDao {

	
	public boolean add(Category category);
	public boolean isExist(String name);
	public boolean delete(Category category);

	public List<Category> getAll();
	
	public boolean addList(List<Category> list);

}
