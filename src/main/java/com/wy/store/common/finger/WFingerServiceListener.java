package com.wy.store.common.finger;

public interface WFingerServiceListener {

	public void onFingerReceived(String fingerId);
	
	public void enrollFingerReceived(int fingerId, int enrollCount);
}
