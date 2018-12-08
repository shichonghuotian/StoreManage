package com.wy.store.db.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.wy.store.db.dao.DeviceDao;
import com.wy.store.db.jdbc.StoreDB;
import com.wy.store.domain.Category;
import com.wy.store.domain.Device;
import com.wy.store.domain.Warehouse;

@Service("deviceDao")
public class DeviceDaoImpl implements DeviceDao {
	Logger logger = LogManager.getLogger(getClass());

	Dao<Device, Long> dao;

	public DeviceDaoImpl() {
		// TODO Auto-generated constructor stub

		try {
			dao = DaoManager.createDao(StoreDB.getConnectionSource(), Device.class);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean add(Device device) {

		try {
			dao.createOrUpdate(device);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Device> getAllDevice() {
		// TODO Auto-generated method stub
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	@Override
	public Device getDevice(String deviceId) {
		// TODO Auto-generated method stub
		List<Device> devices = null;
		try {
			devices = dao.queryForEq("deviceId", deviceId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (devices != null && devices.size() > 0) {

			return devices.get(0);
		}

		return null;

	}

	@Override
	public boolean isExist(String deviceId) {
		// TODO Auto-generated method stub
		return getDevice(deviceId) != null;
	}

	@Override
	public List<Device> getDeviceListLikeDeviceId(String deviceId) {
		List<Device> devices = new ArrayList<>();
		try {

			// mDao.queryBuilder().where().like("LastName", "A%").query()
			devices = dao.queryBuilder().where().like("deviceId", "%" + deviceId + "%").query();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return devices;
	}

	@Override
	public List<Device> getDeviceListLikeDeviceName(String deviceName) {
		// TODO Auto-generated method stub
		List<Device> devices = new ArrayList<>();
		try {

			// mDao.queryBuilder().where().like("LastName", "A%").query()
			devices = dao.queryBuilder().where().like("name", "%" + deviceName + "%").query();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return devices;
		
	}

	@Override
	public List<Device> getDeviceListByCateogry(Category category) {
		List<Device> devices = new ArrayList<>();
		try {
			devices = dao.queryForEq("category_id", category.getId());
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return devices;
	}

	@Override
	public List<Device> getDeviceListByWarehouse(Warehouse warehouse) {
		
		List<Device> devices = new ArrayList<>();

		
		try {
			devices = dao.queryForEq("warehouse_id", warehouse.getId());
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return devices;
	}

	@Override
	public List<Device> getDeviceListLike(String deviceId, String name, Category category, Warehouse warehouse) {
		List<Device> devices = new ArrayList<>();
		try {

			// mDao.queryBuilder().where().like("LastName", "A%").query()
//			StatementBuilder<dev>
			
			QueryBuilder<Device,Long> builder = dao.queryBuilder();
			
			Where<Device, Long> where = builder.where().like("name", "%" + name + "%").and().like("deviceId",  "%" + deviceId + "%");

			if(category.getId() > -1) {
				
				where = where.and().eq("category_id", category.getId());
			}
			
			if(warehouse.getId() > -1) {
				where = where.and().eq("warehouse_id", warehouse.getId());

			}
			
			
//			System.out.println(builder.prepareStatementString());
			devices = where.query();
					
			
					
//			devices = dao.queryBuilder().where().like("name", "%" + name + "%").query();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return devices;
	}

}
