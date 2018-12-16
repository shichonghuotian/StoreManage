package com.wy.store.modules.users.list;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.google.common.eventbus.Subscribe;
import com.wy.store.app.BaseViewController;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.UserDao;
import com.wy.store.db.dao.impl.UserDaoImpl;
import com.wy.store.domain.User;
import com.wy.store.modules.users.add.UserAddController;
import com.wy.wfx.core.ann.FXMLWindow;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

@ViewController(res = "/layout_user_info.fxml")
public class UserListController extends BaseViewController {

	@FXMLWindow
	Window window;

	@FXML
	TableView<User> mTableView;

	@FXML
	ComboBox<String> mSearchComboBox;

	@FXML
	TextField mSearchTextField;
	@FXML
	Button mAddButton;
	@FXML
	Button mEditButton;
	@FXML
	Button mDeleteButton;

	ObservableList<User> observableList;

	UserDao mUserDao;

	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		// TODO Auto-generated method stub
		mUserDao = new UserDaoImpl();
		// final ObservableList<Person> data =
		// FXCollections.observableArrayList(

		List<User> list = mUserDao.getAllUser();
		observableList = FXCollections.observableList(list);

		TableColumn<User, Long> idColumn = new TableColumn<>("序号");

		TableColumn<User, String> userCodeColumn = new TableColumn<>("用户编号");

		TableColumn<User, String> nameColumn = new TableColumn<>("姓名");
		TableColumn<User, String> fingerColumn = new TableColumn<>("指纹数据");

		idColumn.setCellValueFactory(new PropertyValueFactory<User, Long>("id"));
		userCodeColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		fingerColumn.setCellValueFactory(new PropertyValueFactory<>("fingerId"));

		mTableView.getColumns().addAll(idColumn, userCodeColumn, nameColumn, fingerColumn);

		mTableView.setItems(observableList);

		mSearchTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				System.out.println(mSearchTextField.getText());
				String searchText = mSearchTextField.getText();

				List<User> users = null;

				if (mSearchComboBox.getSelectionModel().getSelectedIndex() == 0) {
					users = mUserDao.getUserListLikeUserId(searchText);
				} else {
					users = mUserDao.getUserListLikeUserName(searchText);
				}
				observableList.clear();

				observableList.addAll(users);
			}
		});

		mSearchComboBox.getItems().addAll("用户编号", "用户名");

		mSearchComboBox.getSelectionModel().select(0);

		WEventBus.getDefaultEventBus().register(this);
	}
	
	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		WEventBus.getDefaultEventBus().unregister(this);

	}

	@Subscribe
	public void onRefresh(WUserAddEvent o) {
		List<User> list = mUserDao.getAllUser();

		observableList.clear();
		observableList.addAll(list);
	}

	public void addAction(ActionEvent event) {

		try {
			this.presentController(new WFxIntent(UserAddController.class));

			// FxViewHander.showControllerInWindow(UserAddController.class,
			// window);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteAction(ActionEvent event) {

		// 获取选中的行数
		User user = mTableView.getSelectionModel().getSelectedItem();
		System.out.println("select user " + user);

		//需要查看是否有借出记录

		boolean t = false;
		if (t) {
			WAlert.showMessageAlert("当前管理员已经登陆，请退出重新登陆后删除");

		} else {
			Optional<ButtonType> result = WAlert.showConfirmationMessageAlert("确定删除" + user.getName() + "吗？");

			if (result.get() == ButtonType.OK) {
				
				
				mUserDao.deleteUser(user);

				observableList.remove(user);
			}
		}

		// 还是需要添加事件的
	}

	public void editAction(ActionEvent event) {
		System.out.println("edit");

		// 确定编辑这个用户吗
	}

}
