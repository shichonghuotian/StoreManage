package com.wy.store.modules.devices.list;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;

import com.wy.store.bean.Category;
import com.wy.store.bean.Device;
import com.wy.store.bean.User;
import com.wy.store.bean.Warehouse;
import com.wy.store.modules.users.list.UserTableModel;


//所有的设备列表
public class DeviceListView extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable mTable;
	JScrollPane mScrollPane;

	private List<Device> mList;
	private TableModel mDeviceTableModel;

	public DeviceListView() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		//生成页面
		setTitle("用户列表");
		setSize(300, 300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        list.setPreferredSize(new Dimension(200, 100));


		List<Device> list = new ArrayList<>();
		
		for(int i=0;i<100;i++) {
			
			
			Device device = new Device(i, "sz1000" + i, "工具 " + i, new Category("常用"), "", "", new Warehouse("仓库1"));
			list.add(device);
					
					

		}
		
		this.mList = list;
		
		mDeviceTableModel = new DeviceListTableModel(list);
		
	
		
		mTable = new JTable(mDeviceTableModel);
		mTable.setGridColor(Color.GRAY);
		mTable.setFillsViewportHeight(false);
		mTable.setRowHeight(30);
		
		mScrollPane = new JScrollPane(mTable);
		
		this.add(mScrollPane);
		
		
		setVisible(true);
		
	}
	
}
