package com.wy.store.modules.users.add;


import org.springframework.context.annotation.Scope;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.finger.WFingerService;
import com.wy.store.common.finger.WFingerServiceEnrollListener;
import com.wy.store.common.finger.WFingerServiceFactory;
import com.wy.store.common.finger.WFingerServiceListener;
import com.wy.store.common.finger.WFingerServiceLoadListener;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.UserDao;
import com.wy.store.db.dao.UserFingerDao;
import com.wy.store.db.dao.impl.UserDaoImpl;
import com.wy.store.db.dao.impl.UserFingerDaoImpl;
import com.wy.store.domain.User;
import com.wy.store.domain.UserFinger;
import com.wy.store.modules.users.list.WUserAddEvent;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@ViewController(res = "/layout_user_add")
@Scope("prototype") // 默认是单例
public class UserAddController extends BaseViewController implements WFingerServiceEnrollListener,WFingerServiceLoadListener {

	@FXML
	TextField mUserCodeTextField;
	@FXML
	TextField mNameTextField;

	@FXML
	ProgressBar fingerProgressBar;
	@FXML
	Label fingerMsgLabel;
	
	
	UserDao userDao;

	WFingerService fingerService;

	UserFingerDao fingerDao;
	
	UserFinger currentFinger;
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		setTitle("添加用户");

		fingerDao = new UserFingerDaoImpl();
		userDao = new UserDaoImpl();
		fingerService = WFingerServiceFactory.getFingerService();
		fingerService.registerConnectListener(this);
		fingerService.register(this);

		currentFinger = new UserFinger();
		
		System.out.println("maxid = " + fingerDao.getNextId());
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		fingerService.unregisterConnectListener(this);
		fingerService.unregister(this);
	}

	@Override
	public void onDeviceConnectFailed(String s) {
		// TODO Auto-generated method stub
		
		System.err.println("指纹仪连接状态: " + s);
		
	}
	

	@Override
	public void onDeviceConnectSuccess(String msg) {
		// TODO Auto-generated method stub
		 fingerMsgLabel.setText("指纹仪连接成功");
	}

	@Override
	public void onEnrollSuccess(int fingerId, byte[] regBlob) {
		// TODO Auto-generated method stub
		setMsgText("指纹录入成功  id= " +fingerId);
		
		currentFinger.setFingerId(fingerId);
		currentFinger.setFingerBlob(regBlob);
		
		Platform.runLater(() -> {
			
			fingerProgressBar.setProgress(1);
//插入到数据库
			
		});
	}

	@Override
	public void onEnrollReceived(int enrollCount) {
		// TODO Auto-generated method stub
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("enrollCount  =  " + enrollCount + " progress = " + 1.f/3.f*(float)enrollCount);

				fingerProgressBar.setProgress(1.f/3.f*(float)enrollCount);
				
//				fingerMsgLabel.setText("指纹录入成功");
				setMsgText("请继续录入指纹");

			}
		});
	}

	@Override
	public void onEnrollReceivedError(String error) {
		// TODO Auto-generated method stub
//		fingerMsgLabel.setText(error);
		
		setMsgText(error);
	}



	private void setMsgText(String text) {
		Platform.runLater(() -> fingerMsgLabel.setText(text));
	
	}
	



	public void beginAction(ActionEvent event) {
		System.out.println("beginAction + " );

		if(!fingerService.isOpen()) {
			fingerService.openDevice();
			
		}
		fingerMsgLabel.setText("指纹设备已经连接");
		fingerService.enrollFinger();
		currentFinger = new UserFinger();


	}

	public void resetAction(ActionEvent event) {

		fingerProgressBar.setProgress(0);
		

		fingerService.enrollFinger();
		currentFinger = new UserFinger();

	}

	public void cancelAction(ActionEvent event) {

	
		dismissController();
	}

	public void saveAction(ActionEvent event) {

		// 检查各种信息是否填写完成

		if (checkForm()) {

			User user = new User(mUserCodeTextField.getText().trim(),
					mNameTextField.getText().trim());
			// 检查是否重复
			

			boolean isExist = userDao.isExist(user.getUserId());
			System.out.println("isexist = " + isExist);
			if (!userDao.isExist(user.getUserId())) {

		
				fingerDao.addFinger(currentFinger);
				user.setFingerId(currentFinger.getFingerId());
				userDao.addUser(user);

				WEventBus.getDefaultEventBus().post(new WUserAddEvent());;

				
				WAlert.showMessageAlert("用户添加成功");

			} else {
				WAlert.showMessageAlert("当前用户编号已经存在");
			}

		} else {
			WAlert.showMessageAlert("检查表单是否填写完成");

		}
	}

	public boolean checkForm() {
		
		boolean b = currentFinger.getFingerBlob() != null;

		return !StringUtils.isEmpty(mUserCodeTextField.getText()) && !StringUtils.isEmpty(mNameTextField.getText())
				 && b;
	}


	

}
