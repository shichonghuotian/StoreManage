package com.wy.store.modules.login;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.wy.store.app.BaseViewController;
import com.wy.store.app.StoreApp;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.ManagerDao;
import com.wy.store.db.dao.UserDao;
import com.wy.store.db.dao.impl.ManagerDaoImpl;
import com.wy.store.domain.Manager;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

@ViewController(res="/layout_login.fxml")
@Lazy
@Scope("prototype")//默认是单例
public class LoginController extends BaseViewController{

	@FXML
	TextField mNameTextField;
	@FXML
	PasswordField mPwdTextField;
	@FXML
	Label tipLabel;
	
	ManagerDao managerDao;
	
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		setTitle("登录");
		managerDao = new ManagerDaoImpl();
//		mNameTextField.setText("admin");
//		mPwdTextField.setText("admin");
		Stage stage = (Stage) getKeyWindow();
	
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			
			@Override
			public void handle(WindowEvent event) {
				// TODO Auto-generated method stub
				event.consume();
				System.out.println("==========================close==================");

				resetAction();
				
			}
		});
	}

	public void resetAction() {
		mNameTextField.setText("");
		mPwdTextField.setText("");
		
		exitApp();
	
	}
	
	public void loginAction() {
		
		//检查是否可以登陆
		
		Manager manager = new Manager();
		
		manager.setName(mNameTextField.getText().trim());
		manager.setPassword(mPwdTextField.getText());
		
	  Manager mgr =	managerDao.getManager(manager.getName());
		
		if(manager.equals(mgr)) {
			StoreApp.currentManager = mgr;
//			dismissController();
			Stage stage = (Stage)getKeyWindow();
			stage.close();

			
		}else {
			showLoginFaileAlert();
		}
	}
	
	
	public void showLoginFaileAlert() {
		
		tipLabel.setText("用户名或者密码不正确");

	}
	
	
	
}
