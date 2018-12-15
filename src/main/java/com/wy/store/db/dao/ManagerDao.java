package com.wy.store.db.dao;

import java.util.List;

import com.wy.store.domain.Manager;

public interface ManagerDao {

	public boolean addMagager(Manager manager);

	
	public List<Manager> getAllMagagers();
	
	public Manager getManager(String name);

	public boolean delete(Manager manager);

	public boolean isExist(String name);

	
}
