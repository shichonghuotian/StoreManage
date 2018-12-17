package com.wy.store.modules.devices.warehouse;

import java.util.List;
import java.util.Optional;

import com.google.common.eventbus.Subscribe;
import com.wy.store.app.BaseViewController;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.dao.impl.WarhouseDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.ParentCategory;
import com.wy.store.domain.User;
import com.wy.store.domain.Warehouse;
import com.wy.store.modules.devices.category.child.DeviceCategoryAddController;
import com.wy.store.modules.devices.category.parent.DeviceParentCategoryAddController;
import com.wy.store.modules.users.list.WUserAddEvent;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
@ViewController(res="/layout_device_warehouse_list")
public class WarehouseListController extends BaseViewController{
	
	@FXML
	TableView<Warehouse> mTableView;
	
	WarhouseDao mWarhouseDao;
	
	ObservableList<Warehouse> observableList;
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		mWarhouseDao = new WarhouseDaoImpl();
		List<Warehouse> list = mWarhouseDao.getAll();
		
		observableList = FXCollections.observableList(list);
		TableColumn<Warehouse, Long> idColumn = new TableColumn<>("序号");
		TableColumn<Warehouse, String> nameColumn = new TableColumn<>("类别");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		ObservableList<TableColumn<Warehouse,?>> columns = mTableView.getColumns();
		 columns.addAll(idColumn, nameColumn);
		
		mTableView.setItems(observableList);


		WEventBus.getDefaultEventBus().register(this);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		WEventBus.getDefaultEventBus().unregister(this);

	}

	@Subscribe
	public void onRefresh(WWarehouseAddEvent o) {
		List<Warehouse> list = mWarhouseDao.getAll();

		observableList.clear();
		observableList.addAll(list);
	}
	

	public void addAction(ActionEvent event) {

		try {
			this.presentController(new WFxIntent(WarehouseAddController.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAction(ActionEvent event) {
		  Warehouse warehouse =	mTableView.getSelectionModel().getSelectedItem();

		  if(warehouse != null) {
				 Optional<ButtonType> result = WAlert.showConfirmationMessageAlert("是否删除本条数据，删除后，相应的工具记录也会被清除！");
				 
					if (result.get() == ButtonType.OK){

						//删除数据
						
						 mWarhouseDao.delete(warehouse);
						  
						  observableList.remove(warehouse);
						
					} 
			 
		  }else {
			  
			  WAlert.showMessageAlert( "请选择一条数据");
		  }
		
		  
	
	}
	public void editAction(ActionEvent event) {
		Warehouse warehouse = mTableView.getSelectionModel().getSelectedItem();

		if (warehouse != null) {

			WFxIntent intent = new WFxIntent(WarehouseAddController.class);

			intent.putExtra("isEdit", true);
			intent.putExtra("data", warehouse);

			try {
				presentController(intent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			WAlert.showMessageAlert("请选择一条数据");
		}
		
	}


}
