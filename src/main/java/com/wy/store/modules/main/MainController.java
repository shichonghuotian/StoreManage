package com.wy.store.modules.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.wy.store.app.BaseViewController;
import com.wy.store.app.StoreApp;
import com.wy.store.common.finger.WFingerService;
import com.wy.store.common.finger.WFingerServiceFactory;
import com.wy.store.common.finger.WFingerServiceLoadListener;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.UserDao;
import com.wy.store.db.dao.impl.UserDaoImpl;
import com.wy.store.modules.devices.category.child.DeviceCategoryListController;
import com.wy.store.modules.devices.category.parent.DeviceParentCategoryAddController;
import com.wy.store.modules.devices.category.parent.DeviceParentCategoryListController;
import com.wy.store.modules.devices.list.DeviceListController;
import com.wy.store.modules.devices.mgr.DeviceLoanMgrController;
import com.wy.store.modules.devices.warehouse.WarehouseListController;
import com.wy.store.modules.finger.FingerDeviceController;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

@ViewController(res = "/layout_main.fxml")
public class MainController extends BaseViewController implements WFingerServiceLoadListener {

	@FXMLWindow
	Window window;

	@FXML
	Pane mContainerPane;

	@FXML
	Label mMgrInfoLabel;
//	@FXML
//	Pane mMenuContainerPane;

	@FXML
	VBox mVbox;
	@FXML
	Label	mFingerMsgLabel;
	@FXML
	ListView<MainMenuItem> mListView;

	// ObservableList<MainMenuItem> menuList;

	List<MainMenuItem> mMenuItemList;

//	@Autowired
	UserDao userDao;

	WFingerService fingerService;
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		setTitle("工具管理系统");
		fingerService = WFingerServiceFactory.getFingerService();
		
		fingerService.registerConnectListener(this);

		try {
			presentController(new WFxIntent(LoginController.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userDao = new UserDaoImpl();
		
		
		if(fingerService.isOpen()) {
			
			mFingerMsgLabel.setText("已连接");
		}else {
			mFingerMsgLabel.setText("未连接");
		}

		mMenuItemList = new ArrayList<>();
//		mMenuItemList.add(new MainMenuItem("管理员", ManagerListController.class));

		mMenuItemList.add(new MainMenuItem("用户管理", UserListController.class));
		mMenuItemList.add(new MainMenuItem("设备管理", DeviceListController.class));
		mMenuItemList.add(new MainMenuItem(" 	总类别", DeviceParentCategoryListController.class));
		mMenuItemList.add(new MainMenuItem(" 	子类别", DeviceCategoryListController.class));

		mMenuItemList.add(new MainMenuItem(" 	仓库", WarehouseListController.class));

		mMenuItemList.add(new MainMenuItem("借还管理", DeviceLoanMgrController.class));

		
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
		MenuItem exitMenuItem =  new MenuItem("退出");
		
		menu.getItems().add(exitMenuItem);
//		menu.getItems().add(new MenuItem("Save"));
//		menu.getItems().add(new SeparatorMenuItem());
//		menu.getItems().add(new MenuItem("Exit"));
		exitMenuItem.setOnAction((ActionEvent t) -> {
           exitAction(null);
        });  

		mVbox.getChildren().add(0, menuBar);
//		mMenuContainerPane.getChildren().add(menuBar);

		if(StoreApp.currentManager != null) {
			mMgrInfoLabel.setText(StoreApp.currentManager.getName());
		}
		
		
		if(getKeyWindow() !=null) {
			
			Stage stage = (Stage) getKeyWindow();
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					// TODO Auto-generated method stub
					event.consume();
//					System.out.println("==========================close==================");

					exitAction(null);
					
				}
			});
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		WFingerServiceFactory.getFingerService().unregisterConnectListener(this);

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
	public void fingerAction(ActionEvent event) {
		try {
			presentController(new WFxIntent(FingerDeviceController.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	protected <T> void addView(Class<? extends WFxViewController> controllerClass) {

		for(WFxViewController controller : getChildControllerList()) {
			//手动调用一下回收的方法
			controller.onDestroy();
			
		}
		removeAllChildController();
		mContainerPane.getChildren().clear();

		WFxIntent intent = new WFxIntent(controllerClass);

		addChildController(intent, mContainerPane);
	}
	
	public void mangerAction(ActionEvent event) {
		addView(ManagerListController.class);

	}
	public void exitAction(ActionEvent event) {
		Optional<ButtonType>  result =	WAlert.showConfirmationMessageAlert("是否要退出系统？");
		if (result.get() == ButtonType.OK){

			exitApp();
			
		} 
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


	@Override
	public void onDeviceConnectFailed(String s) {
		// TODO Auto-generated method stub
		mFingerMsgLabel.setText(s);
	}

	@Override
	public void onDeviceConnectSuccess(String msg) {
		// TODO Auto-generated method stub
		mFingerMsgLabel.setText("已连接");

	}

}
