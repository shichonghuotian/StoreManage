package com.wy.store.modules.devices.mgr;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wy.store.app.BaseViewController;
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
import com.wy.store.modules.devices.warehouse.WWarehouseAddEvent;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

@ViewController(res="/layout_device_loan")
public class DeviceLoanController extends BaseViewController implements WFingerServiceListener{

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
	UserDao userDao;
	DeviceLoanInfoDao loanInfoDao;
	
	User currentUser;
	
	WFingerService fingerService;
	
	Device currentDevice;

  
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		fingerService = WFingerServiceFactory.getFingerService();
		deviceDao = new DeviceDaoImpl();
		userDao = new UserDaoImpl();
		loanInfoDao = new DeviceLoanInfoDaoImpl();
		mDateTextField.setText(DateUtils.getCurrentDateString());

		fingerService.register(this);
		
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
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		fingerService.unregister(this);
		System.out.println("destory loan");
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
	
	
	@Override
	public void onFingerReceived(String fingerId) {
		// TODO Auto-generated method stub
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
				DeviceLoanInfo loanInfo = new DeviceLoanInfo();
				
				loanInfo.setDevice(device);
				loanInfo.setTakeUser(user);
				loanInfo.setLoan(true);
				
				loanInfo.setLoanDate(date);
				loanInfo.setDescription(mDescriptionTextArea.getText().trim());
				
				//需要查询是否存在借阅
				
				if(!loanInfoDao.isLoaned(device)) {
					loanInfoDao.addLoanInfo(loanInfo);

					WEventBus.getDefaultEventBus().post(new WLoanReturnEvent());

					WAlert.showMessageAlert("借出成功");

				}else {
					WAlert.showMessageAlert("工具已经借出去了");

				}
				
				
			}else {
				WAlert.showMessageAlert("没有发现用户或者工具，请先录入");

				
			}
			
			
		}else {
			///
			WAlert.showMessageAlert("表单没有填写完成 "  + mDeviceIdTextField +" text = " + mDeviceIdTextField.getText() + mUserIdTextField.getText());
		}
	}
	
	public void cancelAction(ActionEvent event) {
	

		dismissController();
	}

	
	public boolean checkForm() {

		return !StringUtils.isEmpty(mDeviceIdTextField.getText()) && !StringUtils.isEmpty(mUserIdTextField.getText()) ;
	}

	



}
