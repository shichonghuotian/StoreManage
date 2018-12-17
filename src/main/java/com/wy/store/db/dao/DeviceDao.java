package com.wy.store.db.dao;

import java.util.List;

import com.wy.store.domain.Category;
import com.wy.store.domain.Device;
import com.wy.store.domain.Warehouse;

public interface DeviceDao {

	
public boolean add(Device device);

	
	public List<Device> getAllDevice();
	
	public Device getDevice(String deviceId);

	public void delete(Device device);
	public void update(Device device);

	public boolean isExist(String deviceId);
	
	
	public List<Device> getDeviceListLikeDeviceId(String deviceId);
	
	public List<Device> getDeviceListLike(String deviceId,String name,Category category,Warehouse warehouse);


	public List<Device> getDeviceListLikeDeviceName(String deviceName);
	public List<Device> getDeviceListByCateogry(Category category);
	public List<Device> getDeviceListByWarehouse(Warehouse warehouse);


}
