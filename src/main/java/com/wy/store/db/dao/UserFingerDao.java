package com.wy.store.db.dao;

import java.util.List;

import com.wy.store.domain.UserFinger;

public interface UserFingerDao {

	public boolean addFinger(UserFinger finger);
	
	public List<UserFinger> getAllUserFingers();
	
	public UserFinger  getUserFinger(long id);
	
	public boolean delete(UserFinger finger);
	public long getNextId();
	

}
