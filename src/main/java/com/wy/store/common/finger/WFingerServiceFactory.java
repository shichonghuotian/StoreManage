package com.wy.store.common.finger;

import com.wy.store.common.finger.zk.WZKFingerServiceImpl;

public class WFingerServiceFactory {

	public static WFingerService service = new WZKFingerServiceImpl();
	
	public static WFingerService getFingerService() {
		
		return service;
	}
}
