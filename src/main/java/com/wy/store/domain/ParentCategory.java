package com.wy.store.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 父类别
 * @author Apple
 *
 */
@DatabaseTable(tableName = "t_device_parent_category")
public class ParentCategory {
	@DatabaseField(generatedId =true,columnName="_id")
	private long id;
	
	@DatabaseField
	private String code;
	
	//类别名称
	@DatabaseField
	private String name;
	
	
	
	
	public ParentCategory() {
		super();
	}


	public ParentCategory(String name) {
		super();
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
}
