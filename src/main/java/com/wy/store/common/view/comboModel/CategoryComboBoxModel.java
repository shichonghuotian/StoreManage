package com.wy.store.common.view.comboModel;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import com.wy.store.domain.Category;

public class CategoryComboBoxModel extends DefaultComboBoxModel<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<Category> list;
	
	
	
	public CategoryComboBoxModel(List<Category> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		for(int i=0;i<list.size();i++) {
			addElement(list.get(i).getName());
		}
	}
	
	public Category getSelectCategory() {
		
		return list.get(getIndexOf(getSelectedItem()));
	}

}
