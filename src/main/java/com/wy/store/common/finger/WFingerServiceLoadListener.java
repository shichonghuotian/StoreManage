package com.wy.store.common.finger;

public interface WFingerServiceLoadListener {

	public void onDeviceConnectFailed(String error);
	
	public void onDeviceConnectSuccess(String msg);

}
