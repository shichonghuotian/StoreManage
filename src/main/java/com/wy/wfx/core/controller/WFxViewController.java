package com.wy.wfx.core.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * 
 * @author Apple
 *
 */
public abstract class WFxViewController implements Initializable{

	//导航controller
	private WFxNavigationController navigationController;
	
	private WFxTabBarController tabBarController;
	
	private List<WFxViewController> childControllerList;


	
	private Window keyWindow;
	
	//
	private Node view;
	
	public List<WFxViewController> getChildControllerList() {
		if(childControllerList == null) {
			childControllerList = new ArrayList<>();
		}
		return childControllerList;
	}

	public abstract void onCreate(WFxIntent intent);
	
	public abstract void onDestroy();
	
	
	
	public Window getKeyWindow() {
		return keyWindow;
	}




	public void setKeyWindow(Window keyWindow) {
		this.keyWindow = keyWindow;
	}




	public WFxNavigationController getNavigationController() {
		return navigationController;
	}




	public void setNavigationController(WFxNavigationController navigationController) {
		this.navigationController = navigationController;
	}




	public Node getView() {
		return view;
	}




	public void setView(Node view) {
		this.view = view;
	}




	/**
	 * 弹出controller
	 * @param intent
	 * @throws Exception 
	 */
	public void presentController(WFxIntent intent) throws Exception {
		
		WViewContext<? extends WFxViewController> viewContext = getViewContext(intent.getControllerClass());
		WFxViewController controller = viewContext.getController();
		
		Stage stage = new Stage();
		Scene scene = new Scene((Parent) viewContext.getRootNode());
		// viewContext.getController().setParentStage(primaryStage);
		stage.setScene(scene);
		controller.setKeyWindow(stage);
		stage.initModality(Modality.WINDOW_MODAL);
		if (this.keyWindow != null) {
			stage.initOwner(this.keyWindow);
		}

		controller.setView(viewContext.getRootNode());
		controller.onCreate(intent);

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				controller.onDestroy();

			}
		});
		
		System.out.println(" stage " + stage);

		// //会阻塞下面的代码
		stage.showAndWait();
		

	}
	
	//关闭controller
	public void dismissController() {
		if(getKeyWindow() != null) {
			Stage stage = (Stage)getKeyWindow();
			System.out.println(" dismissController " + stage);

			stage.close();

		}
		
	}
	
	public void addChildController(WFxIntent intent,Pane conatinerView) {
		

		

		WViewContext<? extends WFxViewController> viewContext;
		try {
			viewContext = getViewContext(intent.getControllerClass());
			WFxViewController controller = viewContext.getController();

			controller.setNavigationController(this.navigationController);
			controller.setView(viewContext.getRootNode());
			controller.onCreate(intent);

			addChildControllerToList(controller);

//			conatinerView.getChildren().clear();
			conatinerView.getChildren().add(viewContext.getRootNode());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void removeAllChildController() {
		
		getChildControllerList().clear();
	}
	

	private WFxViewController getController(Class<?> controllerClass) {
		
		WFxViewController c = null;
		//查询，然后移除
		for(WFxViewController controller : getChildControllerList()) {
			
			if(controller.getClass() == controllerClass) {
				
				c = controller;
				
				break;
			}
		}
		
		
		return c;
		
	}
	
	private void addChildControllerToList(WFxViewController controller) {

		getChildControllerList().add(controller);

	}
	
	public void addChildControllerFromList(WFxViewController controller) {
		
		getChildControllerList().remove(controller);
	}
	
	 <T> WViewContext<T> getViewContext(Class<T> controllerClass) throws Exception {
		WViewContext<T> viewContext = WViewControllerFactory.getInstance().createController(controllerClass);
		return viewContext;
	}
	 
	public void setBackgoundColor(Color color) {
		
		
		if(getView() != null && (getView() instanceof Pane)) {
			Pane pane = (Pane) getView();
			
			pane.setBackground(new Background(new BackgroundFill(color, null, null)));
			
		}
	}




	public WFxTabBarController getTabBarController() {
		return tabBarController;
	}




	public void setTabBarController(WFxTabBarController tabBarController) {
		this.tabBarController = tabBarController;
	}
	
	

}
