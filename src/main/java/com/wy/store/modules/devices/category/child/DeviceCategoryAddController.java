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
	
	
	boolean isEdit;

	Category editCategory;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		
		super.onCreate(intent);
		setTitle("添加类别");
		
		if(intent.getExtras() !=null) {
			editCategory = (Category) intent.getExtras().get("category");
			isEdit = (boolean) intent.getExtras().get("isEdit");
			if (editCategory == null) {

				isEdit = false;

			}
		}
		
		mCategoryDao = new CategoryDaoImpl();
		mParentCategoryDao = new ParentCategoryDaoImpl();
		
		List<ParentCategory> plist = mParentCategoryDao.getAll();

		mParentCombox.getItems().addAll(plist);
		

		if (isEdit) {
			mCodeTextField.setDisable(true);
			mCodeTextField.setText(editCategory.getCode());
			mNameTextField.setText(editCategory.getName());
			setTitle("编辑类别");
			mParentCombox.getSelectionModel().select(editCategory.getParentCategory());
		} else {
			setTitle("添加类别");
			mParentCombox.getSelectionModel().selectFirst();

		}
		
		
		

	}
	
	private void reset() {
		mNameTextField.setText("");
		mCodeTextField.setText("");
		mTipLabel.setText("");
	}
	
	private void add() {
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

			
			
			RxEventBus.getDefault().post(new RxEvent<String>(""));
			
			WEventBus.getDefaultEventBus().post(new WCategoryAddEvent());
			WAlert.showMessageAlert("添加成功");
			
//			dismissController();
			reset();


		}
	}
	
	private void edit() {
		String name = mNameTextField.getText().trim();
				
		ParentCategory parentCategory = mParentCombox.getSelectionModel().getSelectedItem();

		
		if(!StringUtils.isEmpty(name)) {
			
			Category c = mCategoryDao.getCategory(parentCategory.getId(), name);
			if(c != null) {
				
				mTipLabel.setText("总类别下，存在相同的类别名称");
				
				return;
			}
			


			editCategory.setParentCategory(parentCategory);
			editCategory.setName(name);
			
			mCategoryDao.update(editCategory);

			
			
			RxEventBus.getDefault().post(new RxEvent<String>(""));
			
			WEventBus.getDefaultEventBus().post(new WCategoryAddEvent());
			WAlert.showMessageAlert("修改成功");
			
			dismissController();
			
		}
	}
	public void saveAction(ActionEvent event) {

		if(isEdit) {
			
			edit();
		}else {
			add();
		}
		
	}
	

	public void cancelAction(ActionEvent event) {
		logger.info("key window = " + getKeyWindow());
		dismissController();
		
	}
}
