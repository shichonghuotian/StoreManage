package com.wy.wfx.core.app;

import com.wy.wfx.core.ann.BindMainController;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;
import com.wy.wfx.core.controller.WFxViewController;
import com.wy.wfx.core.controller.WViewContext;
import com.wy.wfx.core.controller.WViewControllerFactory;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class WFxApplication extends Application {



	public WFxApplication() {

		// 添加一个

	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		super.init();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// this.setKeyWindow(primaryStage);
	
		BindMainController mainControllerAnno = this.getClass().getAnnotation(BindMainController.class);

		if (mainControllerAnno != null && !(mainControllerAnno.value() == null)) {

			Class<? extends WFxViewController> viewControllerClass = mainControllerAnno.value();

			WViewContext<? extends WFxViewController> viewContext = WViewControllerFactory.getInstance()
					.createController(viewControllerClass);
			
			WFxViewController controller = viewContext.getController();
			controller.setKeyWindow(primaryStage);
			controller.setView(viewContext.getRootNode());

			controller.onCreate(new WFxIntent());

			Scene scene = new Scene((Parent) viewContext.getRootNode());
			primaryStage.setScene(scene);
			primaryStage.show();


		} else {
			throw new Exception("need add @mainController");
		}
	}

	public <T> WViewContext<T> createController(final Class<T> controllerClass) throws Exception {
		// 1.create controller
		T controller = controllerClass.newInstance();

		this.getClass().getAnnotations();

		ViewController controllerAnnotation = controllerClass.getAnnotation(ViewController.class);

		// title
		if (controllerAnnotation != null && !controllerAnnotation.title().isEmpty()) {

		}

		WViewContext<T> context = new WViewContext<T>(null, controller);
		//
		// 处理一下生命周期 -- init destory

		return context;
	}

}
