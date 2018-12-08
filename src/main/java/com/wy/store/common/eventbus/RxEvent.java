package com.wy.store.common.eventbus;

//必须实现这个类
public class RxEvent<T> {

	private T data;

	public RxEvent(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	
	
	

}
