package com.wy.store.modules.main;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.wy.store.modules.devices.add.DeviceAddView;
import com.wy.store.modules.devices.loan.DeviceLoanView;
import com.wy.store.modules.devices.returns.DeviceReturnView;

public class MainScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MainScreen() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	
	public void init() {
		setTitle("库存管理");

		setSize(400, 400);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		initLayout();
		setVisible(true);

	}
	public void initLayout() {
	    Container container =	getContentPane();
//	    
	    container.add(new DeviceAddView());
//	    container.add(new DeviceLoanView());
	}
}
