package com.wy.store.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import com.wy.store.modules.devices.add.DeviceAddView;
import com.wy.store.modules.devices.loan.DeviceLoanView;
import com.wy.store.modules.devices.returns.DeviceReturnView;

//主页面
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
	
	//默认是透明的
	//生成页面
	public void initLayout() {
		
//		JLabel label = new JLabel("test show jframe");
//		
	    Container container =	getContentPane();
//	    
//	    container.add(new DeviceAddView());
	    container.add(new DeviceReturnView());
//	    label.setBounds(10, 10, 100, 100);
//
//	   label.setOpaque(true);
//	    label.setForeground(Color.GREEN);
////	    label.setBackground(Color.RED);
//	    container.setBackground(Color.white);
//		
//	    
//	    container.add(label);
//	    
//	    JButton button = new JButton("click");
//	    
//	    button.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//				System.out.println("click");
//				new MainScreen();
//			}
//		});
//	    
//	    container.add(button);

		
	}
	
}
