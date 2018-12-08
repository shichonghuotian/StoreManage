package com.wy.store.modules.main;

import com.wy.wfx.core.controller.WFxViewController;

public class MainMenuItem {

	private String title;

	private Class<? extends WFxViewController> controlerClass;
	
	


	public MainMenuItem(String title, Class<? extends WFxViewController> controlerClass) {
		super();
		this.title = title;
		this.controlerClass = controlerClass;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Class<? extends WFxViewController> getControlerClass() {
		return controlerClass;
	}

	public void setControlerClass(Class<? extends WFxViewController> controlerClass) {
		this.controlerClass = controlerClass;
	}
	
	
}
