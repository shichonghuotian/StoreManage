package com.wy.wfx.core.controller;

import javafx.scene.Node;

/**
 * 定义一个上下文，存放view的数据
 * @author Apple
 *
 */
public class WViewContext<T> {

	
	private final Node rootNode;
	
	private final T controller;
	
	

	public WViewContext(Node rootNode, T controller) {
		super();
		this.rootNode = rootNode;
		this.controller = controller;
	}

	public Node getRootNode() {
		return rootNode;
	}

	public T getController() {
		return controller;
	}
	

	
	
}
