package com.wy.store.common.finger;

public interface WFingerServiceEnrollListener {

	//fingerid,二进制数据
	void onEnrollSuccess(int fingerId);
	
	void onEnrollReceived(int enrollCount);
	
	
	void onEnrollReceivedError(String error);
}
