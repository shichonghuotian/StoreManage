package com.wy.store.modules.finger;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.finger.WFingerService;
import com.wy.store.common.finger.WFingerServiceFactory;
import com.wy.store.common.finger.WFingerServiceLoadListener;
import com.wy.store.common.view.WAlert;
import com.wy.store.modules.users.list.UserListController;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
@ViewController(res="/layout_finger_device")
public class FingerDeviceController extends BaseViewController implements WFingerServiceLoadListener{

	@FXML
	Label msgLabel;
	WFingerService fingerService;

	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		super.onCreate(intent);
		setTitle("指纹仪");
		
	fingerService = WFingerServiceFactory.getFingerService();
		
		fingerService.registerConnectListener(this);
		
		if(fingerService.isOpen()) {
			
			msgLabel.setText("已连接");
		}else {
			msgLabel.setText("未连接");
		}
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		fingerService.unregisterConnectListener(this);
	}


	@Override
	public void onDeviceConnectFailed(String error) {
		// TODO Auto-generated method stub
		
		msgLabel.setText(error);
	}


	@Override
	public void onDeviceConnectSuccess(String msg) {
		// TODO Auto-generated method stub
		msgLabel.setText(msg);
	}
	
	
	public void testAction(ActionEvent e) {

		if(!fingerService.isOpen()) {
			
			fingerService.openDevice();
		}else {
			
			WAlert.showMessageAlert("设备已经连接");
		}

	}
	

}
