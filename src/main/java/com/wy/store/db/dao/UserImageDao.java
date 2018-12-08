package com.wy.store.db.dao;

import java.util.List;

import com.wy.store.domain.User;
import com.wy.store.domain.UserImage;

public interface UserImageDao {

	public void addImage(UserImage image) ;

	public List<UserImage> getImageList(String userId);

	public List<UserImage> getImageList(User user);
	
}
