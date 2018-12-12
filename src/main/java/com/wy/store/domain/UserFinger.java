package com.wy.store.domain;

import java.util.Arrays;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_user_finger")
public class UserFinger {

	@DatabaseField(generatedId =true,columnName="_id")
	private long id;


	@DatabaseField(columnName="finger_id")
	private long fingerId;


	@DatabaseField( columnDefinition= "LONGBLOB not null",
			dataType = DataType.BYTE_ARRAY)
	private byte[] fingerBlob;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getFingerBlob() {
		return fingerBlob;
	}

	public void setFingerBlob(byte[] fingerBlob) {
		this.fingerBlob = fingerBlob;
	}

	public long getFingerId() {
		return fingerId;
	}

	public void setFingerId(long fingerId) {
		this.fingerId = fingerId;
	}

	@Override
	public String toString() {
		return "UserFinger [id=" + id + ", fingerId=" + fingerId + ", fingerBlob=" + Arrays.toString(fingerBlob) + "]";
	}

	
	
	
	
}
