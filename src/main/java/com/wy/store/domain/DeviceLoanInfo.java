package com.wy.store.domain;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_device_loan")
//借阅信息表
public class DeviceLoanInfo {

	//设备
	@DatabaseField(generatedId =true,columnName="_id")
	public long  id;
	
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private Device device;
	
	//领取人
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private User takeUser;
	
	//归还人-- 可以不同
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private User returnUser;

	//管理员或经受人 -- out person
	@DatabaseField(foreign=true,foreignAutoRefresh=true)
	private User master;
	
	//借出时间
	@DatabaseField
	private Date loanDate;
	
	//归还时间
	@DatabaseField
	private Date returnDate;
	
	//是否借出--标记状态，归还-- 借出 -- 入库
	@DatabaseField
	private boolean isLoan;
	
	//用途
	@DatabaseField
	private String description;

	public DeviceLoanInfo(Device device, User takeUser, User returnUser, User master, Date loanDate, Date returnDate,
			String description) {
		super();
		this.device = device;
		this.takeUser = takeUser;
		this.returnUser = returnUser;
		this.master = master;
		this.loanDate = loanDate;
		this.returnDate = returnDate;
		this.description = description;
	}
	
	public DeviceLoanInfo() {
		// TODO Auto-generated constructor stub
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public User getTakeUser() {
		return takeUser;
	}

	public void setTakeUser(User takeUser) {
		this.takeUser = takeUser;
	}

	public User getReturnUser() {
		return returnUser;
	}

	public void setReturnUser(User returnUser) {
		this.returnUser = returnUser;
	}

	public User getMaster() {
		return master;
	}

	public void setMaster(User master) {
		this.master = master;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isLoan() {
		return isLoan;
	}

	public void setLoan(boolean isLoan) {
		this.isLoan = isLoan;
	}

	@Override
	public String toString() {
		return "DeviceLoanInfo [id=" + id + ", device=" + device + ", takeUser=" + takeUser + ", returnUser="
				+ returnUser + ", master=" + master + ", loanDate=" + loanDate + ", returnDate=" + returnDate
				+ ", isLoan=" + isLoan + ", description=" + description + "]";
	}

	
	
	
}
