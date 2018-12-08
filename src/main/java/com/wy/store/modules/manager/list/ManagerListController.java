package com.wy.store.modules.manager.list;

import java.util.List;

import com.wy.store.app.BaseViewController;
import com.wy.store.db.dao.ManagerDao;
import com.wy.store.db.dao.impl.ManagerDaoImpl;
import com.wy.store.domain.Manager;
import com.wy.store.domain.User;
import com.wy.store.modules.manager.add.ManagerAddController;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
@ViewController(res="/layout_manager_list")
public class ManagerListController extends BaseViewController{

	@FXML
	TableView<Manager> mTableView;
	@FXML
	Button mAddButton;
	@FXML
	Button mEditButton;
	@FXML
	Button mDeleteButton;
	
	ObservableList<Manager> observableList;

	
	ManagerDao managerDao;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		managerDao = new ManagerDaoImpl();
		
		List<Manager> list = managerDao.getAllMagagers();
		observableList = FXCollections.observableList(list);


		TableColumn<Manager, String> nameColumn = new TableColumn<>("管理员");


		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		TableColumn<Manager, Long> idColumn = new TableColumn<>("序号");
		idColumn.setCellValueFactory(new PropertyValueFactory<Manager, Long>("id"));


		mTableView.getColumns().addAll( idColumn,nameColumn);

		mTableView.setItems(observableList);
	
	}
	
	public void addAction(ActionEvent event) {

		System.out.println("add");
		
//		
		
		try {
			this.presentController(new WFxIntent(ManagerAddController.class));

//			FxViewHander.showControllerInWindow(UserAddController.class, window);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAction(ActionEvent event) {
		
		//获取选中的行数
	  Manager manager =	mTableView.getSelectionModel().getSelectedItem();

		//
//		new WAlert.Builder().message("确定删除"+user.getName() + "吗？").create().show();
	  
		//还是需要添加事件的
	}

	public void editAction(ActionEvent event) {
		System.out.println("edit");

		//确定编辑这个用户吗
	}
}
