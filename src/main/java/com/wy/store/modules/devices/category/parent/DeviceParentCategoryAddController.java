package com.wy.store.modules.devices.category.parent;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.ParentCategoryDao;
import com.wy.store.db.dao.impl.ParentCategoryDaoImpl;
import com.wy.store.domain.ParentCategory;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@ViewController(res="/layout_device_parent_category_add")
public class DeviceParentCategoryAddController extends BaseViewController {

	@FXML
	TextField mNameTextField;
	@FXML
	TextField mCodeTextField;
	@FXML
	Label mTipLabel;
	
	ParentCategoryDao mCategoryDao;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		
		super.onCreate(intent);
		setTitle("添加总类别");
		mCategoryDao = new ParentCategoryDaoImpl();
	}
	
	public void saveAction(ActionEvent event) {

		String string = mNameTextField.getText().trim();
		String code = mCodeTextField.getText().trim();
		
		if(!StringUtils.isEmpty(string) && !StringUtils.isEmpty(code)) {
			
//			if(mCategoryDao.fi)
			if(mCategoryDao.isExist(string)) {
				
				mTipLabel.setText("类别名已经存在");

			}else if(mCategoryDao.isExistOfCode(code)) {
				mTipLabel.setText("类别编码不能重复");
			
			}else {

				ParentCategory category = new ParentCategory(string);
				category.setCode(code);
				
				mCategoryDao.add(category);
				mNameTextField.setText("");
				mCodeTextField.setText("");
				mTipLabel.setText("");				
				WEventBus.getDefaultEventBus().post(new WParentCategoryEvent());
				WAlert.showMessageAlert("添加成功");

				dismissController();

			}
			
		}else {
			mTipLabel.setText("编码或者类别名称不能为空");
		}
		
	}
	

	public void cancelAction(ActionEvent event) {
		dismissController();
		
	}
}
