package com.wy.store.modules.devices.category.parent;

import java.util.List;

import com.google.common.eventbus.Subscribe;
import com.wy.store.app.BaseViewController;
import com.wy.store.common.eventbus.RxEventBus;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.ParentCategoryDao;
import com.wy.store.db.dao.impl.CategoryDaoImpl;
import com.wy.store.db.dao.impl.ParentCategoryDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.Device;
import com.wy.store.domain.ParentCategory;
import com.wy.store.domain.User;
import com.wy.store.modules.devices.add.DeviceAddController;
import com.wy.store.modules.devices.category.child.WCategoryAddEvent;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

@ViewController(res = "/layout_device_parent_category_list")
public class DeviceParentCategoryListController extends BaseViewController {

	@FXML
	TableView<ParentCategory> mTableView;

	ParentCategoryDao mCategoryDao;

	ObservableList<ParentCategory> observableList;

	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);

		mCategoryDao = new ParentCategoryDaoImpl();

		List<ParentCategory> list = mCategoryDao.getAll();

		observableList = FXCollections.observableList(list);
		TableColumn<ParentCategory, Long> idColumn = new TableColumn<>("序号");

		TableColumn<ParentCategory, String> codeColumn = new TableColumn<>("编号");

		TableColumn<ParentCategory, String> nameColumn = new TableColumn<>("类别");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));

		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		ObservableList<TableColumn<ParentCategory, ?>> columns = mTableView.getColumns();
		columns.addAll(idColumn, codeColumn, nameColumn);

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
	public void onRefresh(WParentCategoryEvent o) {
		List<ParentCategory> list = mCategoryDao.getAll();

		observableList.clear();
		observableList.addAll(list);
	}

	public void addAction(ActionEvent event) {

		try {
			this.presentController(new WFxIntent(DeviceParentCategoryAddController.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAction(ActionEvent event) {
		ParentCategory category = mTableView.getSelectionModel().getSelectedItem();

		mCategoryDao.delete(category);

		observableList.remove(category);

	}

	public void editAction(ActionEvent event) {
		ParentCategory category = mTableView.getSelectionModel().getSelectedItem();

		if (category != null) {

			WFxIntent intent = new WFxIntent(DeviceParentCategoryAddController.class);

			intent.putExtra("isEdit", true);
			intent.putExtra("category", category);

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
