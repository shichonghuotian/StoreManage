package com.wy.store.modules.main;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wy.store.app.BaseViewController;
import com.wy.store.db.dao.UserDao;
import com.wy.store.db.dao.impl.UserDaoImpl;
import com.wy.store.modules.devices.category.DeviceCategoryListController;
import com.wy.store.modules.devices.list.DeviceListController;
import com.wy.store.modules.devices.mgr.DeviceLoanMgrController;
import com.wy.store.modules.devices.warehouse.WarehouseListController;
import com.wy.store.modules.login.LoginController;
import com.wy.store.modules.manager.list.ManagerListController;
import com.wy.store.modules.users.list.UserListController;
import com.wy.wfx.core.ann.FXMLWindow;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;
import com.wy.wfx.core.controller.WFxViewController;
import com.wy.wfx.core.controller.WFxViewHander;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import javafx.util.Callback;

@ViewController(res = "/layout_main.fxml")
public class MainController extends BaseViewController {

	@FXMLWindow
	Window window;

	@FXML
	Pane mContainerPane;

//	@FXML
//	Pane mMenuContainerPane;

	@FXML
	VBox mVbox;
	
	@FXML
	ListView<MainMenuItem> mListView;

	// ObservableList<MainMenuItem> menuList;

	List<MainMenuItem> mMenuItemList;

//	@Autowired
	UserDao userDao;

	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);

		userDao = new UserDaoImpl();
		
		System.out.println("userdao " + userDao);
		try {
			// FxViewHander.showControllerInWindow(LoginController.class,
			// window);

			WFxViewHander.showControllerInWindow(LoginController.class, window);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mMenuItemList = new ArrayList<>();
		mMenuItemList.add(new MainMenuItem("Category", ManagerListController.class));

		mMenuItemList.add(new MainMenuItem("用户管理", UserListController.class));
		mMenuItemList.add(new MainMenuItem("设备管理", DeviceListController.class));
		mMenuItemList.add(new MainMenuItem("借还管理", DeviceLoanMgrController.class));

		mMenuItemList.add(new MainMenuItem("Category", DeviceCategoryListController.class));

		mMenuItemList.add(new MainMenuItem("仓库", WarehouseListController.class));

		ObservableList<MainMenuItem> observableList = FXCollections.observableList(mMenuItemList);

		mListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MainMenuItem>() {

			@Override
			public void changed(ObservableValue<? extends MainMenuItem> observable, MainMenuItem oldValue,
					MainMenuItem newValue) {
				// TODO Auto-generated method stub

				// System.out.println("select");
				selectMenu(newValue);

			}

		});
		mListView.setCellFactory(new StringListCellFactory());
		mListView.setItems(observableList);

		MenuBar menuBar = new MenuBar();
		
//		for(MainMenuItem mainMenuItem : mMenuItemList) {
//			Menu menu = new Menu(mainMenuItem.getTitle());
//
//			menuBar.getMenus().add(menu);
//
//		}

		Menu menu = new Menu("系统");
		menuBar.getMenus().add(menu);
		menu.getItems().add(new MenuItem("退出"));
//		menu.getItems().add(new MenuItem("Save"));
//		menu.getItems().add(new SeparatorMenuItem());
//		menu.getItems().add(new MenuItem("Exit"));


		mVbox.getChildren().add(0, menuBar);
//		mMenuContainerPane.getChildren().add(menuBar);

	}

	private void selectMenu(MainMenuItem item) {
		addView(item.getControlerClass());
	}

	public void userClickAction(ActionEvent e) {

		addView(UserListController.class);
	}

	public void deviceClickAction(ActionEvent e) {
		addView(DeviceListController.class);

	}

	public void loanClickAction(ActionEvent event) {
		addView(DeviceLoanMgrController.class);

	}

	protected <T> void addView(Class<? extends WFxViewController> controllerClass) {

		removeAllChildController();
		mContainerPane.getChildren().clear();

		WFxIntent intent = new WFxIntent(controllerClass);

		addChildController(intent, mContainerPane);
	}

	class StringListCellFactory implements Callback<ListView<MainMenuItem>, ListCell<MainMenuItem>> {
		@Override
		public ListCell<MainMenuItem> call(ListView<MainMenuItem> playerListView) {
			return new StringListCell();
		}

		class StringListCell extends ListCell<MainMenuItem> {
			@Override
			protected void updateItem(MainMenuItem item, boolean b) {
				super.updateItem(item, b);

				if (item != null) {
					setText(item.getTitle());
				}
			}
		}
	}

}
