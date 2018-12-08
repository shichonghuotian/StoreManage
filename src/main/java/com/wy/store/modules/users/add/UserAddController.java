package com.wy.store.modules.users.add;


import org.springframework.context.annotation.Scope;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.finger.WFingerService;
import com.wy.store.common.finger.WFingerServiceFactory;
import com.wy.store.common.finger.WFingerServiceListener;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.UserDao;
import com.wy.store.db.dao.impl.UserDaoImpl;
import com.wy.store.domain.User;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@ViewController(res = "/layout_user_add")
@Scope("prototype") // 默认是单例
public class UserAddController extends BaseViewController implements WFingerServiceListener {

	@FXML
	TextField mUserCodeTextField;
	@FXML
	TextField mNameTextField;
	@FXML
	TextField mFingerTextField;

	@FXML
	ProgressBar fingerProgressBar;
	@FXML
	Label fingerMsgLabel;
	
	
	UserDao userDao;

	WFingerService fingerService;

	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		userDao = new UserDaoImpl();
		fingerService = WFingerServiceFactory.getFingerService();
		fingerService.register(this);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		fingerService.unregister(this);
	}

	@Override
	public void onFingerReceived(String fingerId) {

	}

	@Override
	public void enrollFingerReceived(int fingerId, int enrollCount) {
		// TODO Auto-generated method stub
		// 收到通知，记录一下

		// 这里就是处理一下进度条就可以了

		System.out.println("enrollCount + " + enrollCount);
	}



	public void beginAction(ActionEvent event) {

		if(!fingerService.isOpen()) {
			fingerService.openDevice();
			
			fingerService.enrollFinger();
		}
	

	}

	public void resetAction(ActionEvent event) {

		
	}

	public void cancelAction(ActionEvent event) {

		// new WAlert.Builder().message("确定取消保存用户吗？").create().show();

		Stage stage = (Stage) mFingerTextField.getScene().getWindow();

		stage.close();
		// exitApp();

		// stage.close();
	}

	public void saveAction(ActionEvent event) {

		// 检查各种信息是否填写完成

		if (checkForm()) {

			User user = new User(mUserCodeTextField.getText().trim(), mFingerTextField.getText().trim(),
					mNameTextField.getText().trim());
			// 检查是否重复

			boolean isExist = userDao.isExist(user.getUserId());
			System.out.println("isexist = " + isExist);
			if (!userDao.isExist(user.getUserId())) {

				userDao.addUser(user);

				System.out.println(user);
				WAlert.showMessageAlert("用户添加成功");

			} else {
				WAlert.showMessageAlert("当前用户编号已经存在");
			}

		} else {
			WAlert.showMessageAlert("检查表单是否填写完成");

		}
	}

	public boolean checkForm() {

		return !StringUtils.isEmpty(mUserCodeTextField.getText()) && !StringUtils.isEmpty(mNameTextField.getText())
				&& !StringUtils.isEmpty(mFingerTextField.getText());
	}

}
