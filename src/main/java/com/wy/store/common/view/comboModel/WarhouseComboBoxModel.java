package com.wy.store.common.view.comboModel;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.wy.store.domain.Category;
import com.wy.store.domain.Warehouse;

public class WarhouseComboBoxModel extends DefaultComboBoxModel<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Warehouse> list;
	
	public WarhouseComboBoxModel(List<Warehouse> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		for(int i=0;i<list.size();i++) {
			addElement(list.get(i).getName());
		}
	}

	public Warehouse getSelectWareHouse() {
		
		return list.get(getIndexOf(getSelectedItem()));
	}

}
