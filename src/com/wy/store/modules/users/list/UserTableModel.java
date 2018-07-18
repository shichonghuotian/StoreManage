package com.wy.store.modules.users.list;


import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.wy.store.bean.User;

public class UserTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<User> list;
	
	public UserTableModel(List<User> list) {
		this.list = list;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return list.get(rowIndex).getName();
	}


	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return "用户名";
	}
}
