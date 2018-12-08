package com.wy.store.db.dao;

import java.util.List;

import com.wy.store.domain.Device;
import com.wy.store.domain.DeviceLoanInfo;

/**
 * @author Apple
 *
 */
/**
 * @author Apple
 *
 */
public interface DeviceLoanInfoDao {

	/**
	 * 添加一条
	 * @param info
	 * @return
	 */
	public boolean addLoanInfo(DeviceLoanInfo info);
	
	/**
	 * 是否借出
	 * @param info
	 * @return
	 */
	public boolean isLoaned(Device device);

	
	/**
	 * 
	 * @param info
	 * @return
	 */
	public boolean updateLoanInfo(DeviceLoanInfo info);
	/**
	 * 获取对应的loan info
	 * @param device
	 * @return
	 */
	public List<DeviceLoanInfo> getLoanInfoList(Device device);
	
	/**
	 * 已经借出的数据
	 * @return
	 */
	public List<DeviceLoanInfo> getLoanedList();

	/**
	 * 获取loanInfo
	 * @param deviceId
	 * @return
	 */
	public List<DeviceLoanInfo> getLoanInfoList(String deviceId);

	/**
	 * 获取最近一条loaninfo
	 * @param deviceId
	 * @return
	 */
	public DeviceLoanInfo getNewestDeviceLoanInfo(Device device);
	
	/**
	 * 
	 * @return
	 */
	public List<DeviceLoanInfo> getAll();
	

}
