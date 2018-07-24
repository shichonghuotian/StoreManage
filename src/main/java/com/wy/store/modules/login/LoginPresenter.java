package com.wy.store.modules.login;

public class LoginPresenter {

	private LoginModel model;
	private	LoginView loginView;
	
	public void attach(LoginView view) {
		
		this.loginView = view;
		
	}
	
	public void login(String name,String pwd) {
		
		
		loginView.login(false);
		
	}
}
