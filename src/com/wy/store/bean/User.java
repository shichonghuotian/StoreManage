package com.wy.store.bean;

public class User {

	//用户id
	private String userID;
	
	//指纹识别号
	private String fingerID;
	
	//用户名
	private String name;
	//用户密码
	private String password;
	
	
	
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getFingerID() {
		return fingerID;
	}
	public void setFingerID(String fingerID) {
		this.fingerID = fingerID;
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
	@Override
	public String toString() {
		return "User [userID=" + userID + ", fingerID=" + fingerID + ", name=" + name + ", password=" + password + "]";
	}
	
	
}
