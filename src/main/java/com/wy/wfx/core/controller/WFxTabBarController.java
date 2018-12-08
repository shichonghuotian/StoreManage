package com.wy.wfx.core.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class WFxTabBarController extends WFxViewController{
	private List<WFxViewController> controllerList;

	private TabPane mTabPane;
	
	private int currentIndex;
	
	public WFxTabBarController(Class<? extends WFxViewController> rootClass,double width,double height) {
		
		this.controllerList = new ArrayList<>();

		mTabPane= new TabPane();
		mTabPane.setPrefSize(width, height);
		setView(mTabPane);
//				
//		Rectangle rectangle = new Rectangle(pane.getPrefWidth(), pane.getPrefHeight());
//		pane.setClip(rectangle);

		WFxIntent intent = new WFxIntent(rootClass);

		try {
			addTabController(intent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @param rootClass
	 * @param view
	 */
	public WFxTabBarController(Class<? extends WFxViewController> rootClass, Pane parentView) {

		this(rootClass, parentView.getPrefWidth(), parentView.getPrefHeight());
		
		parentView.getChildren().add(getView());

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@Override
	public void onCreate(WFxIntent intent) {
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}
	
	public void addTabController(WFxIntent intent) throws Exception {
		WViewContext<? extends WFxViewController> viewContext = getViewContext(intent.getControllerClass());

	   final WFxViewController controller = viewContext.getController();

		controller.setTabBarController(this);
		controller.setView(viewContext.getRootNode());
		controller.onCreate(intent);

		this.controllerList.add(controller);
		
		Tab tab = new Tab("Tab" + (mTabPane.getTabs().size() +1));
		
		tab.setContent(controller.getView());
		
		mTabPane.getTabs().add(tab);
		
		mTabPane.getSelectionModel().select(tab);
		tab.setOnClosed(e -> {
			
			this.controllerList.remove(controller);
		});
		
		
	}

}
