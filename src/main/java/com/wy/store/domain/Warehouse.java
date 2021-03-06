package com.wy.store.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//仓库
@DatabaseTable(tableName="t_warehourse")
public class Warehouse {
	@DatabaseField(generatedId =true,columnName="_id")
	private long id;
	
	//类别名称
	@DatabaseField
	private String name;

//	shelves
	
	
	public Warehouse() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Warehouse(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public Warehouse(String name) {
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
