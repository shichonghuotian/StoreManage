package com.wy.store.app;

import java.net.URL;
import java.util.ResourceBundle;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wy.wfx.core.controller.WFxIntent;
import com.wy.wfx.core.controller.WFxViewController;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class BaseViewController extends WFxViewController {
	protected Logger logger = LogManager.getLogger(getClass());

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub
		
		
	}
	
	@Override
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		super.setTitle(title);
		if(getKeyWindow()!= null) {
			Stage stage = (Stage) getKeyWindow();

			stage.setTitle(title);
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("BaseViewController destory");
	}
    
    public void closeWindow() {
    	
//    	getStage().close();
    }
    
    public void exitApp() {
    	
//    	Platform.runLater(() -> {
//			
//			Platform.exit();
//    	});
//       
        
		System.exit(0);


    }
    
//    @PreDestroy
//	public void onDestory() {
//		
//		System.err.println(getClass().getName() + " destory");
//	}
}
