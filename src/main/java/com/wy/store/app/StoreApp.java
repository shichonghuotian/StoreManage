package com.wy.store.app;

import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

import javax.jws.soap.SOAPBinding.Use;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.sun.javafx.applet.Splash;
import com.wy.store.common.finger.WFingerServiceFactory;
import com.wy.store.db.dao.UserFingerDao;
import com.wy.store.db.dao.impl.UserFingerDaoImpl;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.UserFinger;
import com.wy.store.modules.main.MainController;
import com.wy.wfx.core.ann.BindMainController;
import com.wy.wfx.core.app.WFxApplication;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//0000如果做本地应用，不考虑网络交互，其实可以直接用mvc模式，缺点就是膨胀的controller
//如果用mvp，好处就是可以替换model中的一些业务逻辑，
/**
 * @author Apple
 *
 */
/**
 * @author Apple
 *
 */
@SpringBootApplication
//@ComponentScan(basePackages = { "com.wy.store" })
//@AutoConfigureAfter
@BindMainController(MainController.class)
public class StoreApp extends WFxApplication {

	Logger logger = LogManager.getLogger(getClass());

	private StoreSplashScreen splashScreen;

	private final CompletableFuture<Runnable> splashFuture;

	private static String[] savedArgs = new String[0];
	protected ConfigurableApplicationContext context;

	public static void main(String[] args) {


		launch(StoreApp.class,args);
	}

	public StoreApp() {
		// TODO Auto-generated constructor stub
		this.splashFuture = new CompletableFuture<>();
		

	}

	public StoreSplashScreen getSplashScreen() {
		if (this.splashScreen == null) {

			this.splashScreen = new StoreSplashScreen();

		}

		return this.splashScreen;
	}

	protected static void launchApp(Class<? extends StoreApp> appClass, String[] args) {
		StoreApp.savedArgs = args;
		launch(appClass, args);
	}

	// 注意处理一下spring初始化的问题
	private ConfigurableApplicationContext loadSpring() {
		context = SpringApplication.run(getClass(), savedArgs);
		context.getAutowireCapableBeanFactory().autowireBean(this);

		return context;
	}

	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		super.init();
		System.out.println("splashFuture = " + splashFuture);

		CompletableFuture.supplyAsync(() -> {

			return loadSpring();
		}).whenComplete((ctx, throwable) -> {
			if (throwable != null) {
				// Platform.runLater(() -> showErrorAlert(throwable));
			} else {

				Platform.runLater(() -> {

					// 这里可以执行一些操作
				});
			}

		}).thenAcceptBothAsync(splashFuture, (ctx, closeSplash) -> {
			System.out.println("end");
			Platform.runLater(closeSplash);
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		final Stage splashStage = new Stage(StageStyle.TRANSPARENT);

		System.out.println("start");
		if (this.getSplashScreen().visible()) {
			final Scene splashScene = new Scene(splashScreen.getParent(), Color.TRANSPARENT);
			splashStage.setScene(splashScene);
			// splashStage.getIcons().addAll(defaultIcons);
			splashStage.initStyle(StageStyle.TRANSPARENT);
			splashStage.show();
		} else {
		}

		splashFuture.complete(() -> {
			System.out.println("start main");

			try {
				super.start(primaryStage);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			splashStage.hide();
			splashStage.setScene(null);

		});
		
		

	}

	@Override
	public void stop() throws Exception {
		super.stop();
		context.close();
		
		WFingerServiceFactory.getFingerService().closeDevice();
	}
	
	
	public void test() {

		UserFingerDao dao = new UserFingerDaoImpl();
		
		UserFinger finger = new UserFinger();
		
		finger.setFingerId(dao.getNextId());
		
		byte[] bs = {(byte)0xEE,(byte)0xEE};
		finger.setFingerBlob(bs);
//		dao.addFinger(finger);
		
		
		System.out.println("next finger id = " + dao.getNextId());
		
	}

}
