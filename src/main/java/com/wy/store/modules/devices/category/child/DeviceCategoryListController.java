package com.wy.store.modules.devices.category.child;

import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.wy.store.app.BaseViewController;
import com.wy.store.common.eventbus.RxEventBus;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.impl.CategoryDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.Device;
import com.wy.store.domain.ParentCategory;
import com.wy.store.domain.User;
import com.wy.store.domain.Warehouse;
import com.wy.store.modules.devices.add.DeviceAddController;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;

@ViewController(res="/layout_device_category_list")
public class DeviceCategoryListController extends BaseViewController{

	@FXML
	TableView<Category> mTableView;
	
	CategoryDao mCategoryDao;
	
	ObservableList<Category> observableList;

	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		WEventBus.getDefaultEventBus().register(this);

		mCategoryDao = new CategoryDaoImpl();
		
		List<Category> list = mCategoryDao.getAll();
		
		observableList = FXCollections.observableList(list);
		TableColumn<Category, Long> idColumn = new TableColumn<>("序号");
		TableColumn<Category, String> codeColumn = new TableColumn<>("编号");

		TableColumn<Category, String> nameColumn = new TableColumn<>("类别");
		TableColumn<Category, String> parentNameColumn = new TableColumn<>("总类别");

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
		
		parentNameColumn.setCellValueFactory((CellDataFeatures<Category, String> param) -> {
			
			if(param.getValue().getParentCategory()!=null) {
				return new SimpleStringProperty(param.getValue().getParentCategory().getName());

			}else {
				return new SimpleStringProperty("");

			}
		});
		ObservableList<TableColumn<Category,?>> columns = mTableView.getColumns();
		 columns.addAll(idColumn,parentNameColumn,codeColumn, nameColumn);
		
		mTableView.setItems(observableList);


		
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		WEventBus.getDefaultEventBus().unregister(this);

		
	}

	@Subscribe
	public void onRefresh(WCategoryAddEvent o) {
		List<Category> list = mCategoryDao.getAll();

		observableList.clear();
		observableList.addAll(list);
	}
	
	public void addAction(ActionEvent event) {

		try {
			this.presentController(new WFxIntent(DeviceCategoryAddController.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAction(ActionEvent event) {
		  Category category =	mTableView.getSelectionModel().getSelectedItem();

		  mCategoryDao.delete(category);
		  
		  observableList.remove(category);
		  
		// 获取选中的行数
		// User user = mTableView.getSelectionModel().getSelectedItem();
		// System.out.println("select user " + user);

		//
		// new WAlert.Builder().message("确定删除"+user.getName() +
		// "吗？").create().show();

		// 还是需要添加事件的
	}

}
