package com.wy.store.db.dao;

import java.util.List;

import com.wy.store.domain.ParentCategory;

public interface ParentCategoryDao {

	
	public boolean add(ParentCategory category);
	
	public ParentCategory getCategory(String name);
	public ParentCategory getCategoryByCode(String code);

	public boolean isExist(String name);
	public boolean isExistOfCode(String code);

	
	public boolean delete(ParentCategory category);

	public List<ParentCategory> getAll();
	
	public boolean addList(List<ParentCategory> list);
}
