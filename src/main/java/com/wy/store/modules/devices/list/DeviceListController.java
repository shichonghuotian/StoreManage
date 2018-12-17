package com.wy.store.modules.devices.list;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.Subscribe;
import com.j256.ormlite.dao.Dao;
import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.DateUtils;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.DeviceDao;
import com.wy.store.db.dao.DeviceLoanInfoDao;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.dao.impl.CategoryDaoImpl;
import com.wy.store.db.dao.impl.DeviceDaoImpl;
import com.wy.store.db.dao.impl.DeviceLoanInfoDaoImpl;
import com.wy.store.db.dao.impl.WarhouseDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.Device;
import com.wy.store.domain.DeviceLoanInfo;
import com.wy.store.domain.Warehouse;
import com.wy.store.modules.devices.add.DeviceAddController;
import com.wy.wfx.core.ann.FXMLWindow;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
@ViewController(res="/layout_device_list")
public class DeviceListController extends BaseViewController {
	Logger logger = LogManager.getLogger(getClass());

	@FXMLWindow
	Window window;
	
	@FXML
	TableView<Device> mTableView;
	@FXML
	ComboBox<String> mSearchComboBox;
	@FXML
	TextField mSearchTextField;
	@FXML
	ComboBox<Warehouse> mWarehouseComboBox;
	@FXML
	ComboBox<Category> mCategoryComboBox;

	ObservableList<Device> observableList;

	DeviceDao deviceDao;

	DeviceLoanInfoDao deviceLoanInfoDao;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		WEventBus.getDefaultEventBus().register(this);
		deviceDao = new DeviceDaoImpl();
		deviceLoanInfoDao = new DeviceLoanInfoDaoImpl();
		WarhouseDao warhouseDao = new WarhouseDaoImpl();
		CategoryDao categoryDao = new CategoryDaoImpl();

		List<Category> categoryList = categoryDao.getAll();
		Category allCategory = new Category(-1, "全部");

		mCategoryComboBox.getItems().add(allCategory);

		mCategoryComboBox.getItems().addAll(categoryList);

		List<Warehouse> warhouseList = warhouseDao.getAll();

		Warehouse allWarehouse = new Warehouse(-1, "全部");
		mWarehouseComboBox.getItems().add(allWarehouse);

		mWarehouseComboBox.getItems().addAll(warhouseList);

		mCategoryComboBox.getSelectionModel().selectFirst();
		mWarehouseComboBox.getSelectionModel().selectFirst();

		List<Device> list = loadLoanInfo(deviceDao.getAllDevice());
		System.out.println(list);
		observableList = FXCollections.observableList(list);

		TableColumn<Device, Long> idColumn = new TableColumn<>("序号");

		TableColumn<Device, String> codeColumn = new TableColumn<>("设备编号");

		TableColumn<Device, String> nameColumn = new TableColumn<>("设备名称");
		
		TableColumn<Device, String> dateColumn = new TableColumn<>("创建时间");

		TableColumn<Device, String> categoryColumn = new TableColumn<>("设备类别");
		TableColumn<Device, String> warehouseColumn = new TableColumn<>("仓库");
		TableColumn<Device, String> shelveColumn = new TableColumn<>("货架");
		TableColumn<Device, String> latticeColumn = new TableColumn<>("货位");
		TableColumn<Device, String> loadInfoColumn = new TableColumn<>("状态");

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		codeColumn.setCellValueFactory(new PropertyValueFactory<>("deviceId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		dateColumn.setCellValueFactory((CellDataFeatures<Device, String> param) -> new SimpleStringProperty(DateUtils.getCustomFormatDateString(
				param.getValue().getCreateDate())));

		categoryColumn.setCellValueFactory((CellDataFeatures<Device, String> param) -> {
			if(param.getValue().getCategory()!=null) {
				return new SimpleStringProperty(param.getValue().getCategory().getName());

			}else {
				return new SimpleStringProperty("");

			}
		});

		warehouseColumn.setCellValueFactory((CellDataFeatures<Device, String> param) -> {
			
			if(param.getValue().getWarehouse()!=null) {
				return new SimpleStringProperty(param.getValue().getWarehouse().getName());

			}else {
				return new SimpleStringProperty("");

			}
		});

		shelveColumn.setCellValueFactory(new PropertyValueFactory<>("warehouseShelve"));

		latticeColumn.setCellValueFactory(new PropertyValueFactory<>("shelveLattice"));
		
		
		loadInfoColumn.setCellValueFactory((CellDataFeatures<Device, String> param) -> {
			
			if(param.getValue().getLoanInfo() !=null && param.getValue().getLoanInfo().isLoan()) {
				
				return new SimpleStringProperty("借出");
				
			}else {
				return new SimpleStringProperty("在库");

			}
		});

		ObservableList<TableColumn<Device,?>> columns = mTableView.getColumns();
		
		
		 columns.addAll(idColumn, codeColumn, nameColumn,dateColumn, categoryColumn, warehouseColumn,shelveColumn,latticeColumn,loadInfoColumn);

		
		
		mTableView.setItems(observableList);

		

		mSearchComboBox.getItems().addAll("设备编号", "设备名称");

		mSearchComboBox.getSelectionModel().select(0);
		

		mSearchTextField.textProperty()
		.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			beginSearch();
		});
		mCategoryComboBox.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends Category> observable, Category oldValue, Category newValue) -> {
			beginSearch();
		});
		mWarehouseComboBox.getSelectionModel().selectedItemProperty()
		.addListener((ObservableValue<? extends Warehouse> observable, Warehouse oldValue,
				Warehouse newValue) -> {
			beginSearch();
		});
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		WEventBus.getDefaultEventBus().unregister(this);

		
	}
	
	@Subscribe
	public void onRefresh(WDeviceAddEvent o) {
		beginSearch();
	}
	
	


	public void beginSearch() {

		boolean isid = mSearchComboBox.getSelectionModel().getSelectedIndex() == 0;

		String deviceId = "";

		String deviceName = "";

		if (isid) {
			deviceId = mSearchTextField.getText().trim();
		} else {
			deviceName = mSearchTextField.getText().trim();
		}

		Category category = mCategoryComboBox.getSelectionModel().getSelectedItem();
		Warehouse warehouse = mWarehouseComboBox.getSelectionModel().getSelectedItem();

		observableList.clear();

		observableList.addAll(loadLoanInfo(deviceDao.getDeviceListLike(deviceId, deviceName, category, warehouse)));

	}

	public void addAction(ActionEvent event) {

		try {
			this.presentController(new WFxIntent(DeviceAddController.class));
//			FxViewHander.showControllerInWindow(DeviceAddController.class, window);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAction(ActionEvent event) {

		// 获取选中的行数
		 Device device = mTableView.getSelectionModel().getSelectedItem();

		 if(device != null) {
			 Optional<ButtonType> result = WAlert.showConfirmationMessageAlert("是否删除本条数据，删除后，相应的工具借还记录也会被删除");
			 
				if (result.get() == ButtonType.OK){

					//删除数据
					
					deviceLoanInfoDao.delete(device);
					
					deviceDao.delete(device);
					
				} 
		 }else {
			 WAlert.showMessageAlert("请选择一条数据");
		 }
		
		 
		
	}

	public void editAction(ActionEvent event) {
//		System.out.println("edit");

		// 确定编辑这个用户吗
		 Device device = mTableView.getSelectionModel().getSelectedItem();

		 if(device != null) {

			 WFxIntent intent = new WFxIntent(DeviceAddController.class);
			 
			 intent.putExtra("isEdit", true);
			 intent.putExtra("data", device);
			 
			 presentController(intent);
		 }else {
			 WAlert.showMessageAlert("请选择一条数据");

		 }
	}

	public List<Device> loadLoanInfo(List<Device> devices) {
		//
	 List<DeviceLoanInfo> list =	deviceLoanInfoDao.getLoanedList();
		
	 if(list != null) {
		 
		 for(int i=0;i<list.size();i++) {
			 DeviceLoanInfo info = list.get(i);
			 
			int index = devices.indexOf(info.getDevice());
//			 System.out.println(">>>>"+index);

			 if(index >=0) {
				 Device device = devices.get(index);
				 device.setLoanInfo(info);
			 }
		 }
		 
	 }
		
		return devices;
		
	}
}
