package com.wy.store.common.eventbus;

import com.google.common.eventbus.EventBus;

public class WEventBus {
	
	private static EventBus eventBus = new EventBus();

	public static EventBus getDefaultEventBus() {

		return eventBus;
	}
}
