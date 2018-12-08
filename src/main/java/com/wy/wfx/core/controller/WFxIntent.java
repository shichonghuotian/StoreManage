package com.wy.wfx.core.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * controller 跳转管理
 * @author Apple
 *
 */
public class WFxIntent {

	
	private Class<? extends WFxViewController> controllerClass;
	
	private Map<String, Object> extras;
	
	
	public WFxIntent() {
		super();
		// TODO Auto-generated constructor stub
	}


	public WFxIntent(Class<? extends WFxViewController> controllerClass) {
		
		this.controllerClass = controllerClass;
	}
	

	public Class<? extends WFxViewController> getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(Class<WFxViewController> controllerClass) {
		this.controllerClass = controllerClass;
	}
	
	public void putExtra(String key,Object value) {
		
		if(extras == null) {
			extras = new HashMap<>();
		}
		
		extras.put(key, value);
	}


	public Map<String, Object> getExtras() {
		return extras;
	}


	public void setExtras(Map<String, Object> extras) {
		this.extras = extras;
	}
	
	
}
