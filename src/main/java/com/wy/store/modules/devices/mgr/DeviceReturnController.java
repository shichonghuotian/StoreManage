package com.wy.store.modules.devices.mgr;

import java.util.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.DateUtils;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.eventbus.WEventBus;
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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
	@FXML
	Label mDeviceMsgLabel;
	DeviceDao deviceDao;

	DeviceLoanInfoDao deviceLoanInfoDao;
	

	UserDao userDao;
	
	User currentUser;
	
	Device currentDevice;


	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);

		deviceDao = new DeviceDaoImpl();
		userDao = new UserDaoImpl();
		deviceLoanInfoDao = new DeviceLoanInfoDaoImpl();
		mDateTextField.setText(DateUtils.getCurrentDateString());
		
		mDeviceIdTextField.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue) {

					loadDevice();
				}
				
			}
		});
		
		mDeviceIdTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				loadDevice();
				
			}
		});
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
	
	private void loadDevice() {
		String deviceId = mDeviceIdTextField.getText().trim();

		if(StringUtils.isEmpty(deviceId)) return;
		//
		currentDevice = deviceDao.getDevice(deviceId);
		System.out.println("current device = " + currentDevice);
		
		if(currentDevice != null) {
			StringBuilder builder = new StringBuilder();
			
			builder.append("设备编号：" + currentDevice.getDeviceId());
			builder.append("    ");
			builder.append("设备名称：" +currentDevice.getName() );
			builder.append("    ");
			builder.append("设备类别：" + currentDevice.getCategory().getParentCategory().getName() + "-" + currentDevice.getCategory().getName());
			builder.append("    ");
			builder.append("仓库：" + currentDevice.getWarehouse().getName());
			mDeviceMsgLabel.setText(builder.toString());

		}else {
			mDeviceMsgLabel.setText("");
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
					
					WEventBus.getDefaultEventBus().post(new WLoanReturnEvent());

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
