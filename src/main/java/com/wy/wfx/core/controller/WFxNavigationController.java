package com.wy.wfx.core.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * 定义一个导航器 可以按照需求封装一个导航器
 * 
 * 定义一个缓存策略，避免层级过深引起的内存问题
 * 
 * 研究一下动画功能 使用动画来切换controller，
 * 
 * @author Apple
 *
 */
public class WFxNavigationController extends WFxViewController {

	private List<WFxViewController> controllerList;

	WSlideTransition slideTransition;

	public WFxNavigationController(Class<? extends WFxViewController> rootClass,double width,double height) {
		
		slideTransition = new WSlideTransition();
		this.controllerList = new ArrayList<>();

		Pane pane = new Pane();
		pane.setPrefSize(width, height);
		setView(pane);
		
//		pane.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
		
		Rectangle rectangle = new Rectangle(pane.getPrefWidth(), pane.getPrefHeight());
		pane.setClip(rectangle);

		WFxIntent intent = new WFxIntent(rootClass);

		try {
			pushController(intent);
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
	public WFxNavigationController(Class<? extends WFxViewController> rootClass, Pane parentView) {

		this(rootClass, parentView.getPrefWidth(), parentView.getPrefHeight());
		
		parentView.getChildren().add(getView());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @param intent
	 * @throws Exception
	 */
	public void pushController(WFxIntent intent) throws Exception {
		WViewContext<? extends WFxViewController> viewContext = getViewContext(intent.getControllerClass());

		WFxViewController controller = viewContext.getController();

		controller.setNavigationController(this);
		controller.setView(viewContext.getRootNode());
		controller.onCreate(intent);

		WFxViewController currentController = getTopController();

		Node currentNode = currentController != null ? currentController.getView() : null;

		addControllerToList(controller);

		// 获取view

		addView(controller.getView());

		if (currentNode != null) {
			slideTransition.inTransition(currentNode, controller.getView(), Duration.millis(500));

		}
	}

	/**
	 * 
	 */
	public void popController() {
		
		//
		WFxViewController controller = getTopController();
		
		if(controller != null && this.controllerList.size() >1) {
			
			this.controllerList.remove(controller);
			
			Node from = controller.getView();
			
			//获取下一个
			
			WFxViewController nextController = getTopController();
			
			
			this.slideTransition.outTransition(from, nextController.getView(), Duration.millis(500),
					e ->  getControllerPane().getChildren().remove(from));
			
			
			
		}
		

		
	}

	public void addView(Node node) {

		// getControllerPane().getChildren().clear();
		Pane pane = (Pane) node;

		pane.setPrefSize(getControllerPane().prefWidthProperty().doubleValue(), getControllerPane().prefHeightProperty().doubleValue());
		getControllerPane().getChildren().add(node);
	}

	private void addControllerToList(WFxViewController controller) {

		this.controllerList.add(controller);

	}

	/**
	 * 获取最顶层的controller
	 * 
	 * @return
	 */
	private WFxViewController getTopController() {
		if (controllerList != null && controllerList.size() > 0)
			return controllerList.get(controllerList.size()-1);

		return null;
	}

	private Pane getControllerPane() {

		return (Pane) getView();
	}
}
