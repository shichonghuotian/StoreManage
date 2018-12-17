package com.wy.store.db.dao;

import java.util.List;

import com.wy.store.domain.Category;
import com.wy.store.domain.Warehouse;

public interface WarhouseDao {

	
	public boolean add(Warehouse warehouse);
	public boolean update(Warehouse warehouse);
	
	public List<Warehouse> getAll();
	
	public boolean addList(List<Warehouse> list);
	
	public boolean isExist(String name);
	public boolean delete(Warehouse warehouse);


	
}
