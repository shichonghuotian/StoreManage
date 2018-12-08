package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.wy.store.db.dao.DeviceLoanInfoDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.Device;
import com.wy.store.domain.DeviceLoanInfo;

@Component
public class DeviceLoanInfoDaoImpl implements DeviceLoanInfoDao {

	Logger logger = LogManager.getLogger(getClass());


	Dao<DeviceLoanInfo, Long> dao;


	public DeviceLoanInfoDaoImpl() {
		// TODO Auto-generated constructor stub
		
		try {
			dao = DaoManager.createDao(StoreDB.getConnectionSource(), DeviceLoanInfo.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public boolean addLoanInfo(DeviceLoanInfo info) {
		// TODO Auto-generated method stub

		try {
			dao.create(info);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}


	@Override
	public boolean updateLoanInfo(DeviceLoanInfo info) {
		// TODO Auto-generated method stub

		try {
			dao.update(info);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}


	@Override
	public List<DeviceLoanInfo> getLoanInfoList(Device device) {
		
		return getLoanInfoList(device.getDeviceId());
	}


	@Override
	public List<DeviceLoanInfo> getLoanInfoList(String deviceId) {
		try {
			return dao.queryForEq("device_id", deviceId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}


	@Override
	public DeviceLoanInfo getNewestDeviceLoanInfo(Device device) {
		
		try {
			 QueryBuilder<DeviceLoanInfo,Long> builder = dao.queryBuilder();

			DeviceLoanInfo loanInfo = builder.orderBy("_id", false).where().eq("device_id", device.getId()).queryForFirst();
		
			logger.log(Level.ERROR, builder.prepareStatementString());
			logger.log(Level.ERROR, "info = " + loanInfo.isLoan());

		

			return loanInfo;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}


	@Override
	public List<DeviceLoanInfo> getAll() {
		// TODO Auto-generated method stub
		
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<DeviceLoanInfo> getLoanedList() {
		 try {
				
			 QueryBuilder<DeviceLoanInfo,Long> builder = dao.queryBuilder();

			List<DeviceLoanInfo> list = builder.where().eq("isLoan", true).query();
			
			logger.log(Level.DEBUG, builder.prepareStatementString());
//			logger.log(Level.DEBUG, "info = " + info);

		
			return list;
		  
		  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return null;
			
		
	}



	@Override
	public boolean isLoaned(Device device) {

	  try {
			
		 QueryBuilder<DeviceLoanInfo,Long> builder = dao.queryBuilder();

		DeviceLoanInfo info = builder.where().eq("device_id", device.getId()).and().eq("isLoan", true).queryForFirst();
		
		logger.log(Level.DEBUG, builder.prepareStatementString());
		logger.log(Level.DEBUG, "info = " + info);

	
		if(info != null) {
			
			return true;
		}
	  
	  } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		return false;
	}
}
