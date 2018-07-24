package com.wy.store.bean;

import java.util.Date;

//借阅信息表
public class DeviceLoanInfo {

	//设备
	private Device device;
	
	//领取人
	private User takeUser;
	
	//归还人-- 可以不同
	private User returnUser;

	//管理员或经受人 -- out person
	private User master;
	
	//借出时间
	private Date loanDate;
	
	//归还时间
	private Date returnDate;
	
	//是否借出--标记状态，归还-- 借出 -- 入库
	private boolean isLoan;
	
	//用途
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

	public boolean isLoan() {
		return isLoan;
	}

	public void setLoan(boolean isLoan) {
		this.isLoan = isLoan;
	}

	@Override
	public String toString() {
		return "DeviceLoanInfo [device=" + device + ", takeUser=" + takeUser + ", returnUser=" + returnUser
				+ ", master=" + master + ", loanDate=" + loanDate + ", returnDate=" + returnDate + ", description="
				+ description + "]";
	}
	
	
}
