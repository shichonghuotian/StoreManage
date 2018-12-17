package com.wy.store.domain;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

//设备信息，记录一下设备的基础信息
@DatabaseTable(tableName = "t_device")
public class Device {

	@DatabaseField(generatedId =true,columnName="_id")
	private long id;
	//设备id
	@DatabaseField(unique = true)
	private String deviceId;
	//设备名称
	@DatabaseField
	private String name;
	
	//类别---可以分表
	@DatabaseField(foreign = true,foreignAutoRefresh=true)
	private Category category;
	
	//条形码 --- 应该和编码相同
	@DatabaseField
	private String barCode;
	
	//如果需要多张图片，那么需要建一个表--
	@DatabaseField
	private  String image;
	
	//仓库 ---- 可以建立一个表
	@DatabaseField(foreign = true,foreignAutoRefresh=true)
	private Warehouse warehouse;

	@DatabaseField
	private String warehouseShelve;
	@DatabaseField
	private String shelveLattice;

	//归还时间
		@DatabaseField
		private Date createDate;
	//添加描述
	@DatabaseField
	private String description;
	
	//
	private DeviceLoanInfo loanInfo;
	
	
	public Device() {
		
	}
	
	public Device(long id, String deviceID, String name, Category category, String barCode, String image,
			Warehouse warehouse) {
		super();
		this.id = id;
		this.deviceId = deviceID;
		this.name = name;
		this.category = category;
		this.barCode = barCode;
		this.image = image;
		this.warehouse = warehouse;
	}


	
	
	public Device(String deviceId, String name, Category category, Warehouse warehouse) {
		super();
		this.deviceId = deviceId;
		this.name = name;
		this.category = category;
		this.warehouse = warehouse;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	

	public DeviceLoanInfo getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(DeviceLoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}
	
	

	public String getWarehouseShelve() {
		return warehouseShelve;
	}

	public void setWarehouseShelve(String warehouseShelve) {
		this.warehouseShelve = warehouseShelve;
	}

	public String getShelveLattice() {
		return shelveLattice;
	}

	public void setShelveLattice(String shelveLattice) {
		this.shelveLattice = shelveLattice;
	}

	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (deviceId == null) {
			if (other.deviceId != null)
				return false;
		} else if (!deviceId.equals(other.deviceId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", deviceId=" + deviceId + ", name=" + name + ", category=" + category
				+ ", barCode=" + barCode + ", image=" + image + ", warehouse=" + warehouse + ", description="
				+ description + "]";
	}
	
	
	
}
