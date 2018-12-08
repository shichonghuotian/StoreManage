package com.wy.store.modules.devices.category;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.impl.CategoryDaoImpl;
import com.wy.store.domain.Category;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@ViewController(res="/layout_device_category_add")
public class DeviceCategoryAddController extends BaseViewController {

	@FXML
	TextField mNameTextField;
	
	CategoryDao mCategoryDao;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		
		super.onCreate(intent);
		mCategoryDao = new CategoryDaoImpl();
	}
	
	public void saveAction(ActionEvent event) {

		String string = mNameTextField.getText().trim();
		if(!StringUtils.isEmpty(string)) {
			
//			if(mCategoryDao.fi)
			if(mCategoryDao.isExist(string)) {
				
				WAlert.showMessageAlert("类别已经存在");
			}else {
				Category category = new Category(string);

				mCategoryDao.add(category);
				WAlert.showMessageAlert("添加成功");

				mNameTextField.setText("");
			}
			
		}
		
	}
	

	public void cancelAction(ActionEvent event) {
		logger.info("key window = " + getKeyWindow());
		dismissController();
		
	}
}
