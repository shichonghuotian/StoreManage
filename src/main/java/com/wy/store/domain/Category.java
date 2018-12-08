package com.wy.store.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_device_category")
public class Category {

	@DatabaseField(generatedId =true,columnName="_id")
	private long id;
	
	
	//类别名称
	@DatabaseField
	private String name;


	public Category() {
		super();
	}

	

	public Category(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public Category(String name) {
		super();
		this.name = name;
	}


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



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
}
