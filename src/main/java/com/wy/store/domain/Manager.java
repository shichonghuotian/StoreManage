package com.wy.store.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//增加一个表
@DatabaseTable(tableName = "t_manager")

public class Manager {
	@DatabaseField(generatedId = true, columnName = "_id")
	private long id;

	// 用户名
	@DatabaseField
	private String name;
	// 用户密码
	@DatabaseField
	private String password;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	
	
}
