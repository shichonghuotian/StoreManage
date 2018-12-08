package com.wy.store.modules.devices.warehouse;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.dao.impl.WarhouseDaoImpl;
import com.wy.store.domain.Category;
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
	
	WarhouseDao mWarehouseDao;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		mWarehouseDao = new WarhouseDaoImpl();
	}
	public void saveAction(ActionEvent event) {

		String string = mNameTextField.getText().trim();
		if(!StringUtils.isEmpty(string)) {
			
//			if(mCategoryDao.fi)
			if(mWarehouseDao.isExist(string)) {
				
				WAlert.showMessageAlert("类别已经存在");
			}else {
				Warehouse warehouse = new Warehouse(string);

				mWarehouseDao.add(warehouse);
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
