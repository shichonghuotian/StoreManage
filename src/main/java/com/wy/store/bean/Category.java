package com.wy.store.bean;

public class Category {

	
	private long ID;
	
	
	//类别名称
	private String name;


	public Category(String name) {
		super();
		this.name = name;
	}


	public long getID() {
		return ID;
	}


	public void setID(long iD) {
		ID = iD;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return "Category [ID=" + ID + ", name=" + name + "]";
	}
	
	
}
