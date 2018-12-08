package com.wy.store.common.finger;

//定义一个接口，封装关于指纹识别的所有方法
public interface WFingerService {
	
	//
	//打开设备
	public int openDevice() ;
	
	/**
	 * 使用base64,或者使用图片，应该都是没有问题的
	 */
	public void registerFingerId(int fid,String base64) ;
	
	
	public void enrollFinger();
	/**
	 * 注册监听接口
	 * @param listener
	 */
	public void register(WFingerServiceListener listener);
	
	/**
	 * 移除监听接口
	 * @param listener
	 */
	public void unregister(WFingerServiceListener listener);
	
	/**
	 * 验证指纹，返回一串id
	 * @return
	 */
	String identifyFinger();
	
	public void receivedFinger();
	
	/**
	 * 关闭device
	 */
	public void closeDevice();
	
	public int getDeviceCount();
	
	
	/**
	 * device is open
	 * @return
	 */
	public boolean isOpen();
	
	boolean isDeviceConnected() ;
	
	 boolean isDBConnected();
	
	
}
