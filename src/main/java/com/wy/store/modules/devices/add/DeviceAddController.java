package com.wy.store.modules.devices.add;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.DeviceDao;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.dao.impl.CategoryDaoImpl;
import com.wy.store.db.dao.impl.DeviceDaoImpl;
import com.wy.store.db.dao.impl.WarhouseDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.Device;
import com.wy.store.domain.Warehouse;
import com.wy.wfx.core.ann.ViewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

@ViewController(res="/layout_device_add")
public class DeviceAddController extends BaseViewController{

	@FXML
	TextField mCodeTextField;
	@FXML
	TextField mNameTextField;
	@FXML
	ComboBox<Category> mCategoryComboBox;
	@FXML
	ComboBox<Warehouse> mWarehouseComboBox;


//	@Autowired
	DeviceDao deviceDao;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		deviceDao = new DeviceDaoImpl();
		WarhouseDao warhouseDao = new WarhouseDaoImpl();
		CategoryDao categoryDao = new CategoryDaoImpl();
		
		List<Category>list = categoryDao.getAll();

		mCategoryComboBox.getItems().addAll(list);
		
		List<Warehouse> warhouseList = warhouseDao.getAll();

		mWarehouseComboBox.getItems().addAll(warhouseList);
		
		mCategoryComboBox.getSelectionModel().selectFirst();
		mWarehouseComboBox.getSelectionModel().selectFirst();

	}
	
	public void saveAction(ActionEvent event) {

		if (checkForm()) {
			Category category = mCategoryComboBox.getSelectionModel().getSelectedItem();
			Warehouse warehouse = mWarehouseComboBox.getSelectionModel().getSelectedItem();
			
			Device device = new Device(mCodeTextField.getText().trim(), mNameTextField.getText().trim(), category, warehouse);

			//检查是否重复
			
			
			
			if(!deviceDao.isExist(device.getDeviceId())) {
				
				deviceDao.add(device);
				
				System.out.println(device);
				WAlert.showMessageAlert("设备添加成功");

			}else {
				WAlert.showMessageAlert("当前设备编号已经存在");
			}
			

		} else {
			WAlert.showMessageAlert("检查表单是否填写完成");

		}
	}

	public void cancelAction(ActionEvent event) {
		
		
	}
	
	public boolean checkForm() {

		return !StringUtils.isEmpty(mCodeTextField.getText().trim()) && !StringUtils.isEmpty(mNameTextField.getText().trim())
				;
	}

}
