package com.wy.store.app;

import com.wy.store.modules.devices.list.DeviceListView;
import com.wy.store.modules.main.MainScreen;

//0000如果做本地应用，不考虑网络交互，其实可以直接用mvc模式，缺点就是膨胀的controller
//如果用mvp，好处就是可以替换model中的一些业务逻辑，
public class StoreApp {

	// 使用mvp模式，
	// 边开发边封装，

	public static void main(String[] args) {
	
//		   try {
//               System.setProperty("apple.laf.useScreenMenuBar", "true");
//               System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
//               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//       }
//       catch(ClassNotFoundException e) {
//               System.out.println("ClassNotFoundException: " + e.getMessage());
//       }
//       catch(InstantiationException e) {
//               System.out.println("InstantiationException: " + e.getMessage());
//       }
//       catch(IllegalAccessException e) {
//               System.out.println("IllegalAccessException: " + e.getMessage());
//       }
//       catch(UnsupportedLookAndFeelException e) {
//               System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());
//       }
//		   
//  			new LoginViewImpl();

//		new MainScreen();
		new DeviceListView();
		   
		// javax.swing.SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// }
		//
		////
		// }
}

}
