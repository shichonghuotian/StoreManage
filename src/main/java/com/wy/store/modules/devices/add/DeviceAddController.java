package com.wy.store.modules.devices.add;

import java.util.Date;
import java.util.List;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.DeviceDao;
import com.wy.store.db.dao.ParentCategoryDao;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.dao.impl.CategoryDaoImpl;
import com.wy.store.db.dao.impl.DeviceDaoImpl;
import com.wy.store.db.dao.impl.ParentCategoryDaoImpl;
import com.wy.store.db.dao.impl.WarhouseDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.Device;
import com.wy.store.domain.ParentCategory;
import com.wy.store.domain.Warehouse;
import com.wy.store.modules.devices.list.WDeviceAddEvent;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.beans.value.ObservableValue;
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

	@FXML
	ComboBox<ParentCategory> mParentCategoryComboBox;
	@FXML
	TextField mShelveTextField;
	@FXML
	TextField mLatticeTextField;
//	@Autowired
	DeviceDao deviceDao;
	CategoryDao categoryDao;
	ParentCategoryDao parentCategoryDao;
	
	
	boolean isEdit;
	Device editDevice;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		
		if(intent.getExtras() !=null) {
			
			isEdit = (boolean) intent.getExtras().get("isEdit");
			
			editDevice = (Device) intent.getExtras().get("data");
			if(editDevice !=null) {
				
				isEdit = true;
			}else {
				
				isEdit = false;
			}

		}
		
		
		
		deviceDao = new DeviceDaoImpl();
		WarhouseDao warhouseDao = new WarhouseDaoImpl();
		categoryDao = new CategoryDaoImpl();
		parentCategoryDao = new ParentCategoryDaoImpl();
		
		List<Warehouse> warhouseList = warhouseDao.getAll();

		mWarehouseComboBox.getItems().addAll(warhouseList);
		mWarehouseComboBox.getSelectionModel().selectFirst();

		List<ParentCategory> parentCategories = parentCategoryDao.getAll();
		
		mParentCategoryComboBox.getItems().addAll(parentCategories);
		
		List<Category>list = categoryDao.getAll();
		mCategoryComboBox.getItems().addAll(list);
		
	
		
		mParentCategoryComboBox.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends ParentCategory> observable, ParentCategory oldValue, ParentCategory newValue) -> {
			
			ParentCategory category = newValue;
			
			loadChildCategory(category);

		});
		

		if(isEdit) {
			setTitle("编辑设备");
			
			mCodeTextField.setDisable(true);
			mCodeTextField.setText(editDevice.getDeviceId());
			mNameTextField.setText(editDevice.getName());
			if(editDevice.getCategory() != null) {
				mParentCategoryComboBox.getSelectionModel().select(editDevice.getCategory().getParentCategory());

			}else {
				mParentCategoryComboBox.getSelectionModel().selectFirst();

			}
			
			if(editDevice.getWarehouse() !=null) {
				mWarehouseComboBox.getSelectionModel().select(editDevice.getWarehouse());
			}
			
			mShelveTextField.setText(editDevice.getWarehouseShelve());
			mLatticeTextField.setText(editDevice.getShelveLattice());
		}else {
			mCodeTextField.setDisable(false);
			setTitle("添加设备");
			mParentCategoryComboBox.getSelectionModel().selectFirst();

		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	/**
	 * 重置状态
	 */
	public void reset() {
		mNameTextField.setText("");
		mCodeTextField.setText("");
		
		
	}
	
	private void loadChildCategory(ParentCategory parentCategory) {
		
		List<Category> list = categoryDao.getCategoryList(parentCategory.getId());
		
		mCategoryComboBox.getItems().clear();
		
		mCategoryComboBox.getItems().addAll(list);
		mCategoryComboBox.getSelectionModel().selectFirst();
		
	}
	
		
	public void saveAction(ActionEvent event) {

		if(isEdit) {
			edit();
		}else {
			add();
		}
	}
	
	private void add() {
		if (checkForm()) {
			Category category = mCategoryComboBox.getSelectionModel().getSelectedItem();
			Warehouse warehouse = mWarehouseComboBox.getSelectionModel().getSelectedItem();
			
			Device device = new Device(mCodeTextField.getText().trim(), mNameTextField.getText().trim(), category, warehouse);

			device.setWarehouseShelve(mShelveTextField.getText().trim());
			device.setShelveLattice(mLatticeTextField.getText().trim());
			
			device.setCreateDate(new Date());
			//检查是否重复
			
			if(!deviceDao.isExist(device.getDeviceId())) {
				
				deviceDao.add(device);
				
//				System.out.println(device);
				WEventBus.getDefaultEventBus().post(new WDeviceAddEvent());
				WAlert.showMessageAlert("设备添加成功");
//				dismissController();

				reset();

			}else {
				WAlert.showMessageAlert("当前设备编号已经存在");
			}
			

		} else {
			WAlert.showMessageAlert("检查表单是否填写完成");

		}
	}
	
	private void edit() {
		if (checkForm()) {
			Category category = mCategoryComboBox.getSelectionModel().getSelectedItem();
			Warehouse warehouse = mWarehouseComboBox.getSelectionModel().getSelectedItem();
			

			editDevice.setName(mNameTextField.getText().trim());
			editDevice.setCategory(category);
			editDevice.setWarehouse(warehouse);
			editDevice.setWarehouseShelve(mShelveTextField.getText().trim());
			editDevice.setShelveLattice(mLatticeTextField.getText().trim());
			
//			device.setCreateDate(new Date());
			//检查是否重复
			
			deviceDao.update(editDevice);;
			
//			System.out.println(device);
			WEventBus.getDefaultEventBus().post(new WDeviceAddEvent());
			WAlert.showMessageAlert("设备修改成功");
			dismissController();
			

		} else {
			WAlert.showMessageAlert("检查表单是否填写完成");

		}
	}

	public void cancelAction(ActionEvent event) {
		
		dismissController();
	}
	
	public boolean checkForm() {

		return !StringUtils.isEmpty(mCodeTextField.getText().trim()) && !StringUtils.isEmpty(mNameTextField.getText().trim())
				
				&&!StringUtils.isEmpty(mShelveTextField.getText().trim()) && !StringUtils.isEmpty(mLatticeTextField.getText().trim())
				
				;
	}

}
