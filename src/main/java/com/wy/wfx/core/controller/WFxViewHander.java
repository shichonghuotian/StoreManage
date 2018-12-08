package com.wy.wfx.core.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.wy.wfx.core.ann.FXMLWindow;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class WFxViewHander {

	/**
	 * 显示controller
	 * 
	 * @param controllerClass
	 * @param primaryStage
	 * @return
	 * @throws Exception
	 */
	public static <T> WViewContext<T> showController(Class<T> controllerClass, Stage primaryStage) throws Exception {

		WViewContext<T> viewContext = getViewContext(controllerClass);
//		injectWindowInController(viewContext.getController(), primaryStage);

		WFxViewController controller =(WFxViewController) viewContext.getController();
		controller.setKeyWindow(primaryStage);
		controller.onCreate(new WFxIntent());
		Scene scene = new Scene((Parent) viewContext.getRootNode());
		primaryStage.setScene(scene);
		primaryStage.show();

		return viewContext;

	}

	/**
	 * 作为子窗口显示，阻塞主窗口
	 * 
	 * @param controllerClass
	 * @return
	 * @throws Exception
	 */
	public static <T> WViewContext<T> showControllerInWindow(Class<T> controllerClass, Window parentWindow)
			throws Exception {
		WViewContext<T> viewContext = getViewContext(controllerClass);
		Stage stage = new Stage();

		injectWindowInController(viewContext.getController(), stage);
		Scene scene = new Scene((Parent) viewContext.getRootNode());
		// viewContext.getController().setParentStage(primaryStage);
		stage.setScene(scene);
		stage.initModality(Modality.WINDOW_MODAL);
		if (parentWindow != null) {
			stage.initOwner(parentWindow);
		}
		// //会阻塞下面的代码
		stage.showAndWait();
		return viewContext;

	}

	/**
	 * 添加到子view中
	 * 
	 * @param controllerClass
	 * @param pane
	 * @return
	 * @throws Exception
	 */
	public static <T> WViewContext<T> showInPane(Class<T> controllerClass, Pane pane) throws Exception {
		WViewContext<T> viewContext = getViewContext(controllerClass);

		if (pane != null) {
			System.out.println(viewContext.getRootNode());
			pane.getChildren().clear();
			pane.getChildren().add(viewContext.getRootNode());
		}

		return viewContext;

	}

	private static <T> WViewContext<T> getViewContext(Class<T> controllerClass) throws Exception {
		WViewContext<T> viewContext = WViewControllerFactory.getInstance().createController(controllerClass);
		return viewContext;
	}

	// 使用注解，检查一下class有没有这个属性
	private static <T> void injectWindowInController(T controller, Stage stage) throws Exception {

		Field[] fields =controller.getClass().getDeclaredFields();
		// 获取一个成员变量上的注解
		if (fields.length > 0) {

			for (Field field : fields) {

				if (field.isAnnotationPresent(FXMLWindow.class)) {
					for (Annotation anno : field.getDeclaredAnnotations()) {// 获得所有的注解
						if (anno.annotationType().equals(FXMLWindow.class)) {// 自动注入

//							FXMLWindow windowAnno = (FXMLWindow) anno;

							if(field.getType() == Window.class) {
								field.setAccessible(true);
								
								field.set(controller, stage);
							}else {
								
					            throw new Exception("Window need javafx.stage.Window");
							}
							

						}

					}

				}
			}

		}
	}

}
