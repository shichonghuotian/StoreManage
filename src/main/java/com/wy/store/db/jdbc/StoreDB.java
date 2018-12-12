package com.wy.store.db.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.LocalLog;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wy.store.db.dao.CategoryDao;
import com.wy.store.db.dao.WarhouseDao;
import com.wy.store.db.dao.impl.CategoryDaoImpl;
import com.wy.store.db.dao.impl.WarhouseDaoImpl;
import com.wy.store.domain.Category;
import com.wy.store.domain.Device;
import com.wy.store.domain.DeviceLoanInfo;
import com.wy.store.domain.Manager;
import com.wy.store.domain.User;
import com.wy.store.domain.UserFinger;
import com.wy.store.domain.UserImage;
import com.wy.store.domain.Warehouse;

//返回connection
public class StoreDB {
	Logger logger = LogManager.getLogger(getClass());
//	jdbc:sqlite::resource:db/app.db#
	public static String databaseUrl = "jdbc:sqlite::resource:db/storeMgr.data";
	
	public static String DBCONF_PROPERTIES = "dbconf.properties";
	public static ConnectionSource getConnectionSource() {
		ConnectionSource connectionSource = null;
		try {

			connectionSource = new JdbcConnectionSource(databaseUrl);
//			((JdbcConnectionSource) connectionSource).setUsername("root");
//			((JdbcConnectionSource) connectionSource)
//					.setPassword("xxx");
		
			 

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connectionSource;
	}
	
	
	
	public static void createTable() throws SQLException {
		
		

		ConnectionSource connectionSource  = getConnectionSource();
		TableUtils.createTableIfNotExists(connectionSource, User.class);
		TableUtils.createTableIfNotExists(connectionSource, UserImage.class);

		
		TableUtils.createTableIfNotExists(connectionSource, Category.class);

		TableUtils.createTableIfNotExists(connectionSource, Warehouse.class);
		TableUtils.createTableIfNotExists(connectionSource, Device.class);
		TableUtils.createTableIfNotExists(connectionSource, DeviceLoanInfo.class);
		TableUtils.createTableIfNotExists(connectionSource, Manager.class);
		TableUtils.createTableIfNotExists(connectionSource, UserFinger.class);

		System.out.println(isDBFirstLoad());
		
//		if(!isDBFirstLoad()) {
//			
//			addCategorys();
//			addWarehouses();
//
//			setDBFirstLoaded();
//
//		}

	}
	
	public static void addCategorys() {
		ArrayList<Category> categories = new ArrayList<>();
		for(int i=1;i<10;i++) {
			
			
			categories.add(new Category("类别 " + i+1));
		}
		
		CategoryDao dao = new CategoryDaoImpl();
		
		dao.addList(categories);
	}
	
	public static void addWarehouses() {
		ArrayList<Warehouse> list = new ArrayList<>();
		for(int i=1;i<10;i++) {
			
			
			list.add(new Warehouse("仓库 " + (i+1)));
		}
		
		WarhouseDao dao = new WarhouseDaoImpl();
		
		dao.addList(list);
		
	}
	
	public static File getDBPropertiesFile() {
    	String path;
		try {
			path = StoreDB.class.getClassLoader().getResource("").toURI().getPath();
	    	
	    	File file = new File(path + DBCONF_PROPERTIES);

	    	return file;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	return null;
		
	}
	
	public static Properties getDBProperties() {
        Properties p = new Properties();

        try {

        	
			FileInputStream fis = new FileInputStream(getDBPropertiesFile());
			p.load(fis);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return p;
		
	}

	public static boolean isDBFirstLoad() {
		
//		
		Properties properties = getDBProperties();
		
		return Boolean.parseBoolean(properties.getProperty("firstLoad"));
		
	}
	
	public static void setDBFirstLoaded() {
		
		Properties properties = getDBProperties();

		properties.setProperty("firstLoad", "true");
		FileOutputStream out;
		try {
			out = new FileOutputStream(getDBPropertiesFile());
			properties.store(out, "");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
