package com.wy.store.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="t_image")
public class UserImage {

	@DatabaseField(generatedId = true,columnName = "t_id")
	private long id;
	
    @DatabaseField(foreign = true)
	private User user;
	
    @DatabaseField
	private String path;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "UserImage [id=" + id + ", user=" + user + ", path=" + path + "]";
	}

	

	
	
}
