package com.wy.store.modules.devices.warehouse;

import java.util.List;

import com.wy.store.app.BaseViewController;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.dao.impl.WarhouseDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.Warehouse;
import com.wy.store.modules.devices.category.DeviceCategoryAddController;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

		  mWarhouseDao.delete(warehouse);
		  
		  observableList.remove(warehouse);
		  
		// 获取选中的行数
		// User user = mTableView.getSelectionModel().getSelectedItem();
		// System.out.println("select user " + user);

		//
		// new WAlert.Builder().message("确定删除"+user.getName() +
		// "吗？").create().show();

		// 还是需要添加事件的
	}


}
