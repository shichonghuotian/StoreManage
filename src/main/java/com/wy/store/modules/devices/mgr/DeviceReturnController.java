package com.wy.store.modules.devices.mgr;

import java.util.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wy.store.app.BaseViewController;
import com.wy.store.app.StoreApp;
import com.wy.store.common.Utils.DateUtils;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.finger.WFingerService;
import com.wy.store.common.finger.WFingerServiceFactory;
import com.wy.store.common.finger.WFingerServiceListener;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@ViewController(res="/layout_device_return")
public class DeviceReturnController extends BaseViewController implements WFingerServiceListener{
	
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
	CheckBox mCheckbox;

	@FXML
	Label mDeviceMsgLabel;
	@FXML
	Label mUserInfoLabel;
	DeviceDao deviceDao;

	DeviceLoanInfoDao deviceLoanInfoDao;
	

	UserDao userDao;
	
	User currentUser;
	
	Device currentDevice;

	WFingerService fingerService;

	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		setTitle("设备归还");
		fingerService = WFingerServiceFactory.getFingerService();

		fingerService.register(this);

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
		
		mUserIdTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(mCheckbox.isSelected()) {
					loadUser();

				}
				
			}
		});
		mUserIdTextField.setDisable(true);

		mCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				
				//支持手动输入用户编号
				if(mCheckbox.isSelected()) {
					mUserIdTextField.setDisable(false);
				}else {
					mUserIdTextField.setDisable(true);

					
				}
			}
		});
	
		connectFingerDevice();
		fingerService.receivedFinger();
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		fingerService.unregister(this);
	} 
	
	@Override
	public void onFingerReceived(int fingerId, int score) {
		// TODO Auto-generated method stub
		currentUser = userDao.getUserOfFingerId(fingerId +"");
		logger.info("device --- user " + currentUser);

		if(currentUser != null) {//
			
			//设置一下
			mUserIdTextField.setText(currentUser.getUserId());
			
			setUserInfo();
		}else {
			currentUser = null;
			setUserInfo();

		}
	}
	
	private void connectFingerDevice() {
	if(!fingerService.isOpen()) {
			
			fingerService.openDevice();
			
			fingerService.addFingerDataToDevice();
			
			
		}
	}
	
	private void loadUser() {

		String userId = mUserIdTextField.getText().trim();
		User user = userDao.getUser(userId);
		System.out.println("user = " + user);
		if(user !=null) {
			
			currentUser = user;
			
			
		}else {
			currentUser = null;
			
		
		}
		setUserInfo();
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
			if(currentDevice.getCategory()!=null) {
				
				builder.append("设备类别：" + currentDevice.getCategory().getParentCategory().getName() + "-" + currentDevice.getCategory().getName());
				builder.append("    ");
			}
			if(currentDevice.getWarehouse() != null) {
				builder.append("仓库：" + currentDevice.getWarehouse().getName());
			}
			mDeviceMsgLabel.setText(builder.toString());


		}else {
			mDeviceMsgLabel.setText("");
		}
		
	}
	
	
	private void setUserInfo() {
		
		if(currentUser!=null) {
			StringBuilder builder = new StringBuilder();
			
			builder.append("用户编号：" + currentUser.getUserId());
			builder.append("    ");
			
			builder.append("用户：" +currentUser.getName() );
			
			mUserInfoLabel.setText(builder.toString());
		}else {
			mUserInfoLabel.setText("");

		}
	}
	
	
	
	
	public void saveAction(ActionEvent event) {
		//检查表单
		if(checkForm()) {
			
			//
			String deviceId = mDeviceIdTextField.getText().trim();
			
			Device device = deviceDao.getDevice(deviceId);
			

			
			if(currentUser ==null) {
				WAlert.showMessageAlert("请添加用户");
				
			}
			Date date = new Date();
			
			if(device!=null && currentUser != null) {
				
				
				DeviceLoanInfo loanInfo = deviceLoanInfoDao.getNewestDeviceLoanInfo(device);
				loanInfo.setReturnDate(date);
				loanInfo.setReturnUser(currentUser);
				loanInfo.setMaster(StoreApp.currentManager);

				logger.log(Level.INFO, loanInfo.isLoan());

				if(loanInfo.isLoan()) {
					loanInfo.setLoan(false);

					deviceLoanInfoDao.updateLoanInfo(loanInfo);
					
					WEventBus.getDefaultEventBus().post(new WLoanReturnEvent());

					WAlert.showMessageAlert("工具归还成功");
					
					dismissController();
				}else {
					WAlert.showMessageAlert("工具没有借阅信息");
				}
			
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
