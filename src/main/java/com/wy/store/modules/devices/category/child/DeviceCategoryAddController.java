package com.wy.store.modules.devices.category.child;

import java.util.List;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.eventbus.RxEvent;
import com.wy.store.common.eventbus.RxEventBus;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.ParentCategoryDao;
import com.wy.store.db.dao.impl.CategoryDaoImpl;
import com.wy.store.db.dao.impl.ParentCategoryDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.ParentCategory;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@ViewController(res="/layout_device_category_add")
public class DeviceCategoryAddController extends BaseViewController {

	@FXML
	TextField mNameTextField;
	@FXML
	TextField mCodeTextField;
	@FXML
	Label mTipLabel;
	
	@FXML
	ComboBox<ParentCategory> mParentCombox;
	
	CategoryDao mCategoryDao;
	
	ParentCategoryDao mParentCategoryDao;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		
		super.onCreate(intent);
		mCategoryDao = new CategoryDaoImpl();
		mParentCategoryDao = new ParentCategoryDaoImpl();
		
		List<ParentCategory> warhouseList = mParentCategoryDao.getAll();

		mParentCombox.getItems().addAll(warhouseList);
		
		mParentCombox.getSelectionModel().selectFirst();

	}
	
	public void saveAction(ActionEvent event) {

		String name = mNameTextField.getText().trim();
		
		String code = mCodeTextField.getText().trim();
		
		ParentCategory parentCategory = mParentCombox.getSelectionModel().getSelectedItem();

		
		if(!StringUtils.isEmpty(name)) {
			
			Category c = mCategoryDao.getCategory(parentCategory.getId(), name);
			if(c != null) {
				
				mTipLabel.setText("总类别下，存在相同的类别名称");
				
				return;
			}
			
			c = mCategoryDao.getCategoryByCode(parentCategory.getId(), code);
			
			if(c != null) {
				
				mTipLabel.setText("总类别下，存在相同的类别编号");
				
				return;
			}
			Category category = new Category(name);
			category.setCode(code);
			category.setParentCategory(parentCategory);
			
			mCategoryDao.add(category);

			mNameTextField.setText("");
			mCodeTextField.setText("");
			mTipLabel.setText("");
			
			RxEventBus.getDefault().post(new RxEvent<String>(""));
			
			WEventBus.getDefaultEventBus().post(new WCategoryAddEvent());
			WAlert.showMessageAlert("添加成功");

		}
		
	}
	

	public void cancelAction(ActionEvent event) {
		logger.info("key window = " + getKeyWindow());
		dismissController();
		
	}
}
