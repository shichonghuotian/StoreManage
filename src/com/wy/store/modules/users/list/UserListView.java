package com.wy.store.modules.users.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;

import com.wy.store.bean.User;

//用户列表，这里显示出所有的用户数据
public class UserListView extends JFrame{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTable mTable;
	
	JScrollPane mScrollPane;
	
	TableModel mUserTableModel;

	public UserListView() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void init() {
		
		setTitle("用户列表");
		setSize(300, 300);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		

		List<User> list = new ArrayList<>();
		
		for(int i=0;i<100;i++) {
			
			User user = new User();
			
			user.setName("name " + i);
			
			list.add(user);
		}
		
		mUserTableModel = new UserTableModel(list);
		
		mTable = new JTable(mUserTableModel);
		mTable.setFillsViewportHeight(false);
		
		mScrollPane = new JScrollPane(mTable);
		
		this.add(mScrollPane);
		
		
		setVisible(true);

		
	}
}
