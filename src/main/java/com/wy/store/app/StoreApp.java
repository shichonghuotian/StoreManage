package com.wy.store.app;

import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.wy.store.bean.User;
import com.wy.store.modules.devices.list.DeviceListView;
import com.wy.store.modules.main.MainScreen;

//0000如果做本地应用，不考虑网络交互，其实可以直接用mvc模式，缺点就是膨胀的controller
//如果用mvp，好处就是可以替换model中的一些业务逻辑，
public class StoreApp {

	private static Session session;
    private static Transaction transaction;
    private static SessionFactory sessionFactory;
    static {
        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }
	
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
//		new DeviceListView();
		   
		// javax.swing.SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// }
		//
		////
		// }
		System.out.println("begin");
//		Configuration config = new Configuration();
//		config.configure();
//		//获取数据库的连接池
//		SessionFactory factory = config.buildSessionFactory();
//		System.out.println(factory);

		
//		 try {
//	            for(int i=0;i<10 ; i++){
//	                User user = new User();
//	                user.setUserID("id_" + i);
//	                user.setName("zhangSan"+i);
//	                user.setPassword("123456");
//	                user.setFingerID("M");
//	                
//	                session.save(user);
//
//
//	            }
//	            transaction.commit();
//	        }catch (Exception e){
//	            e.printStackTrace();
//	            transaction.rollback();
//	        }finally {
//	            if(session != null){
//	                session.close();
//	            }
//	            if(sessionFactory != null){
//	                sessionFactory.close();
//	            }
//			      
//	        }
        List<User> list = session.createQuery("from User").setFirstResult(0).setMaxResults(5).list();
        System.out.println(list);

			System.out.println("end");

	}

}
