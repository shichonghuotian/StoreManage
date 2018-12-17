package com.wy.store.modules.devices.warehouse;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.dao.impl.WarhouseDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.ParentCategory;
import com.wy.store.domain.Warehouse;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@ViewController(res="/layout_device_warehouse_add")
public class WarehouseAddController extends BaseViewController{
	@FXML
	TextField mNameTextField;
	
	Warehouse editWarehouse;
	
	boolean isEdit;
	
	WarhouseDao mWarehouseDao;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		if(intent.getExtras() !=null) {
			editWarehouse = (Warehouse) intent.getExtras().get("data");
			isEdit = (boolean) intent.getExtras().get("isEdit");
			if (editWarehouse == null) {

				isEdit = false;

			}
		}
		
		

		if (isEdit) {
			mNameTextField.setText(editWarehouse.getName());
			setTitle("编辑仓库");

		} else {
			setTitle("添加仓库");

		}
		mWarehouseDao = new WarhouseDaoImpl();
	}
	public void saveAction(ActionEvent event) {

		if(isEdit) {
			edit();
		}else {
			add();
		}
		
	}
	
	private void add() {
		String string = mNameTextField.getText().trim();
		if(!StringUtils.isEmpty(string)) {
			
//			if(mCategoryDao.fi)
			if(mWarehouseDao.isExist(string)) {
				
				WAlert.showMessageAlert("数据库中已经存在");
			}else {
				Warehouse warehouse = new Warehouse(string);

				mWarehouseDao.add(warehouse);
				WEventBus.getDefaultEventBus().post(new WWarehouseAddEvent());

				WAlert.showMessageAlert("添加成功");

				mNameTextField.setText("");
				
			}
			
		}
	}
	
	private void edit() {
		String string = mNameTextField.getText().trim();
		if(!StringUtils.isEmpty(string)) {
			
//			if(mCategoryDao.fi)
			if(mWarehouseDao.isExist(string)) {
				
				WAlert.showMessageAlert("数据库中已经存在");
			}else {

				editWarehouse.setName(string);
				
				mWarehouseDao.update(editWarehouse);
				WEventBus.getDefaultEventBus().post(new WWarehouseAddEvent());

				WAlert.showMessageAlert("修改成功");

				dismissController();
				
			}
			
		}
	}
	

	public void cancelAction(ActionEvent event) {
		logger.info("key window = " + getKeyWindow());
		dismissController();
		
	}
}
