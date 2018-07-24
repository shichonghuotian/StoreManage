package com.wy.store.modules.devices.list;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.wy.store.bean.Device;

public class DeviceListTableModel extends AbstractTableModel {

	private List<Device> list;
	
	private String[] titles = {"序号","名称","编号","类别","存储地点","借用日期","已借用日期","已借用时间","借用人","操作员"};
	

//	private String[] titles = {"序号","名称","编号","类别","存储地点","借用日期","已借用日期","已借用时间","借用人","操作员"};

	private Object[][] datas;
	
	public DeviceListTableModel() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public DeviceListTableModel(List<Device> list) {
		super();
		this.list = list;
		initDatas();
		
	}

    public void initDatas(){
        datas =new Object[list.size()][titles.length];

        for(int i=0;i<list.size();i++){
            for(int j=0;j<titles.length;j++){
            	
            	Device device = list.get(i);
            	datas[i][0]=device.getID();
            	datas[i][1]=device.getName();
            	datas[i][2]=device.getDeviceID();
            	datas[i][3]=device.getCategory().getName();

            	datas[i][4]=device.getWarehouse().getName();
            }
        }
    }


	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return datas[rowIndex][columnIndex];
	}
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return titles[column];
	}

	
	
}
