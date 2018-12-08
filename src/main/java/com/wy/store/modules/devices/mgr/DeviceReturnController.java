package com.wy.store.modules.devices.mgr;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.DateUtils;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.DeviceDao;
import com.wy.store.db.dao.DeviceLoanInfoDao;
import com.wy.store.db.dao.UserDao;
import com.wy.store.db.dao.impl.DeviceDaoImpl;
import com.wy.store.db.dao.impl.DeviceLoanInfoDaoImpl;
import com.wy.store.db.dao.impl.UserDaoImpl;
import com.wy.store.domain.Device;
import com.wy.store.domain.DeviceLoanInfo;
import com.wy.store.domain.User;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@ViewController(res="/layout_device_return")
public class DeviceReturnController extends BaseViewController {
	
	Logger logger = LogManager.getLogger(getClass());

	
	@FXML
	TextField mDeviceIdTextField;
	@FXML
	TextField mUserIdTextField;
	@FXML
	TextField mDateTextField;
	@FXML
	TextArea mDescriptionTextArea;
	
	DeviceDao deviceDao;

	DeviceLoanInfoDao deviceLoanInfoDao;
	

	UserDao userDao;
	
	User currentUser;

	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);

		deviceDao = new DeviceDaoImpl();
		userDao = new UserDaoImpl();
		deviceLoanInfoDao = new DeviceLoanInfoDaoImpl();
		mDateTextField.setText(DateUtils.getCurrentDateString());
		
	}
	
	/**
	 * 接收到fingerid的操作
	 * @param fingerId
	 */
	public void fingerReceived(String fingerId) {
		
		currentUser = userDao.getUserOfFingerId(fingerId);
		logger.info("device --- user " + currentUser);

		if(currentUser != null) {//
			
			//设置一下
			mUserIdTextField.setText(currentUser.getName());
			
		}
		
	}
	
	public void saveAction(ActionEvent event) {
		//检查表单
		if(checkForm()) {
			
			//
			String deviceId = mDeviceIdTextField.getText().trim();
			
			Device device = deviceDao.getDevice(deviceId);
			
			String userId = mUserIdTextField.getText().trim();
			User user = userDao.getUser(userId);
			
			Date date = new Date();
			
			if(device!=null && user != null) {
				
				
				DeviceLoanInfo loanInfo = deviceLoanInfoDao.getNewestDeviceLoanInfo(device);
				loanInfo.setReturnDate(date);
				loanInfo.setReturnUser(user);
				logger.log(Level.INFO, loanInfo.isLoan());

				if(loanInfo.isLoan()) {
					loanInfo.setLoan(false);

					deviceLoanInfoDao.updateLoanInfo(loanInfo);
					
					
					WAlert.showMessageAlert("工具归还成功");
				}else {
					WAlert.showMessageAlert("工具没有借阅信息");
				}
				//需要查询是否存在借阅
			
				
				
			}else {
				WAlert.showMessageAlert("没有发现用户或者工具，请先录入");

				
			}
			
			
		}else {
			///
			WAlert.showMessageAlert("表单没有填写完成");
		}
	}
	
	public void cancelAction(ActionEvent event) {
		
		dismissController();
	}

	public boolean checkForm() {

		return !StringUtils.isEmpty(mDeviceIdTextField.getText()) && !StringUtils.isEmpty(mUserIdTextField.getText());
	}

}
