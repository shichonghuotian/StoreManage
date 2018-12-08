package com.wy.store.modules.manager.add;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.ManagerDao;
import com.wy.store.db.dao.impl.ManagerDaoImpl;
import com.wy.store.domain.Manager;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

@ViewController(res="/layout_manager_add")
public class ManagerAddController extends BaseViewController{
	@FXML
	TextField mNameTextField;
	@FXML
	TextField mPwdTextField;

	@FXML
	TextField mPwdConfirmTextField;
	
	ManagerDao managerDao;
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		managerDao = new ManagerDaoImpl();
	}
	
	public void cancelAction(ActionEvent event) {

		dismissController();

	}

	public void saveAction(ActionEvent event) {

		// 检查各种信息是否填写完成

		if (checkForm()) {

//			User user = new User(mUserCodeTextField.getText().trim(), mFingerTextField.getText().trim(),
//					mNameTextField.getText().trim());
			//检查是否重复
			Manager manager = new Manager();
			manager.setName(mNameTextField.getText().trim());
			manager.setPassword(mPwdConfirmTextField.getText().trim());
			
			
			boolean isExist = managerDao.isExist(manager.getName());
			System.out.println("isexist = " + isExist);
			if(!managerDao.isExist(manager.getName())) {
				
				managerDao.addMagager(manager);
				
				WAlert.showMessageAlert("添加成功");

			}else {
				WAlert.showMessageAlert("当前管理员已经存在");
			}
			

		} else {
			WAlert.showMessageAlert("检查表单是否填写完成");

		}
	}

	public boolean checkForm() {

		return !StringUtils.isEmpty(mNameTextField.getText()) && !StringUtils.isEmpty(mPwdConfirmTextField.getText())
				&& !StringUtils.isEmpty(mPwdTextField.getText());
	}
}
