package com.wy.store.domain;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_user")
public class User {
	@DatabaseField(generatedId =true,columnName="_id")
	private long id;
	
	
	@DatabaseField(unique = true,columnName = "u_id")
	///主键
	private String userId;
	//指纹识别号
	@DatabaseField(unique = true,columnName = "finger_id")
	private long fingerId;
	
	//用户名
	@DatabaseField
	private String name;
	//用户密码
	@DatabaseField
	private String password;
	
	@ForeignCollectionField(eager = true)
	private ForeignCollection<UserImage> imageList;

	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
	public User(String userId, String name) {
		super();
		this.userId = userId;
		this.name = name;
	}




	public User( String userId, long fingerId, String name) {
		super();
		this.userId = userId;
		this.fingerId = fingerId;
		this.name = name;
	}

	public User( String userId, long fingerId, String name, String password) {
		super();
		this.userId = userId;
		this.fingerId = fingerId;
		this.name = name;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getFingerId() {
		return fingerId;
	}

	public void setFingerId(long fingerId) {
		this.fingerId = fingerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ForeignCollection<UserImage> getImageList() {
		return imageList;
	}

	public void setImageList(ForeignCollection<UserImage> imageList) {
		this.imageList = imageList;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", fingerId=" + fingerId + ", name=" + name + ", password="
				+ password + ", imageList=" + imageList + "]";
	}
	
	
	
	
	
}
