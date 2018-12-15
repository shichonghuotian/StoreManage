package com.wy.store.db.dao;

import java.util.List;

import com.wy.store.domain.User;

public interface UserDao {
	public boolean addUser(User user);

	
	public List<User> getAllUser();
	
	public User getUser(String userId);

	public User getUserOfFingerId(String fingerId);

	public boolean isExist(String userId);


	/**
	 * like搜索
	 * @param userId
	 * @return
	 */
	public List<User> getUserListLikeUserId(String likeUserId);
	public List<User> getUserListLikeUserName(String likeUserName);

	public boolean deleteUser(User user);

}
