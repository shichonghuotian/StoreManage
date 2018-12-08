package com.wy.store.modules.login;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.UserDao;
import com.wy.wfx.core.ann.ViewController;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@ViewController(res="/layout_login.fxml")
@Lazy
@Scope("prototype")//默认是单例
public class LoginController extends BaseViewController{

	@FXML
	TextField mNameTextField;
	@FXML
	PasswordField mPwdTextField;
	
	@Autowired
	UserDao userDao;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	
		mNameTextField.setText("admin");
		mPwdTextField.setText("admin");
		
		System.out.println("userdao " + userDao);
//		System.err.println("isShowing " + getStage().isShowing());
		
	
	}

	public void resetAction() {
		mNameTextField.setText("");
		mPwdTextField.setText("");
		
		System.err.println(mPwdTextField.getScene());
//		System.err.println("isShowing " + getStage().isShowing());

//		closeWindow();
		exitApp();
	
	}
	
	public void loginAction() {
		
		System.out.println("login");
		
		//登陆成功----
	   Stage stage = (Stage) mPwdTextField.getScene().getWindow();
	 
	   
	 
	   stage.close();
	}
	
	
	public void showLoginFaileAlert() {
		
		new WAlert.Builder().message("用户名或者密码不正确").create().show();

	}
	
	
	
}
