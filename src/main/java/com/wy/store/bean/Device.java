package com.wy.store.bean;

//设备信息，记录一下设备的基础信息
public class Device {

	private long ID;
	//设备id
	private String deviceID;
	//设备名称
	private String name;
	
	//类别---可以分表
	private Category category;
	
	//条形码 --- 应该和编码相同
	private String barCode;
	
	//如果需要多张图片，那么需要建一个表--
	private  String image;
	
	//仓库 ---- 可以建立一个表
	private Warehouse warehouse;

	
	//添加描述
	private String description;
	
	public Device() {
		
	}
	
	public Device(long iD, String deviceID, String name, Category category, String barCode, String image,
			Warehouse warehouse) {
		super();
		ID = iD;
		this.deviceID = deviceID;
		this.name = name;
		this.category = category;
		this.barCode = barCode;
		this.image = image;
		this.warehouse = warehouse;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

	@Override
	public String toString() {
		return "Device [ID=" + ID + ", deviceID=" + deviceID + ", name=" + name + ", category=" + category
				+ ", barCode=" + barCode + ", image=" + image + ", warehouse=" + warehouse + ", description="
				+ description + "]";
	}
	
	

	
	
	
}
