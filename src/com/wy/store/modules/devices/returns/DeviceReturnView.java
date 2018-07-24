package com.wy.store.modules.devices.returns;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.wy.store.bean.Category;
import com.wy.store.bean.Device;
import com.wy.store.bean.DeviceLoanInfo;
import com.wy.store.bean.User;
import com.wy.store.bean.Warehouse;
import com.wy.store.common.Utils.DateUtils;
import com.wy.store.common.Utils.StringUtils;

import javax.swing.JButton;

//设备归还页面---做一个一键归还页面
public class DeviceReturnView extends JPanel{

	
	//编码，也可以自动填写
	private JTextField mCodeTextField;
	//名称。这一个可以自动填写
	private JLabel mNameLabel;
	//存储地点
	private JLabel mWarehouseLabel;
	//借阅时间
	private JLabel mLoanDateLabel;
//	时长/
	
	private JLabel mLoanDateLengthLabel;

	
	//借用人---
	private JLabel mLoanUserLabel ;
	//管理员///当前的master用户
	private JLabel mManagerLabel;

	//归还人 这里需要 监听指纹录入
	private JTextField mReturnUserTextField;
	//显示当前时间即可，归还时间
		
	private JLabel mDateLabel;

	private Device mCurrentDevice;
	
	private User mCurrentMaster;
	
	//借用人
	private User mReturnUser;

	public DeviceReturnView() {
		
		setLayout(null);
		JLabel lblNewLabel = new JLabel("设备编码：");
		lblNewLabel.setBounds(22, 41, 106, 23);
		add(lblNewLabel);
		
		mCodeTextField = new JTextField();
		mCodeTextField.setBounds(109, 39, 205, 26);
		add(mCodeTextField);
		mCodeTextField.setColumns(10);
		
		JLabel label = new JLabel("设备名称：");
		label.setBounds(22, 82, 106, 23);
		add(label);
		
		JLabel label_1 = new JLabel("存储地点：");
		label_1.setBounds(22, 125, 106, 23);
		add(label_1);
		
		JLabel label_2 = new JLabel("借用人：");
		label_2.setBounds(22, 171, 106, 23);
		add(label_2);
		
		JLabel label_3 = new JLabel("借出时间：");
		label_3.setBounds(22, 222, 106, 23);
		add(label_3);
		
		JLabel label_4 = new JLabel("借用时长：");
		label_4.setBounds(22, 270, 106, 23);
		add(label_4);
		
		JLabel label_5 = new JLabel("归还人：");
		label_5.setBounds(22, 316, 106, 23);
		add(label_5);
		
		JLabel label_6 = new JLabel("归还时间：");
		label_6.setBounds(22, 361, 106, 23);
		add(label_6);
		
		JLabel label_7 = new JLabel("管理员：");
		label_7.setBounds(22, 410, 106, 23);
		add(label_7);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(Color.WHITE);

		lblNewLabel_1.setBounds(109, 82, 205, 23);

		add(lblNewLabel_1);
		mNameLabel = lblNewLabel_1;
		
		JLabel label_8 = new JLabel("");
		label_8.setOpaque(true);
		label_8.setBackground(Color.WHITE);
		label_8.setBounds(109, 128, 205, 23);
		add(label_8);
		mWarehouseLabel = label_8;

		JLabel label_9 = new JLabel("");
		label_9.setOpaque(true);
		label_9.setBackground(Color.WHITE);
		label_9.setBounds(109, 174, 205, 23);
		add(label_9);
		mLoanUserLabel = label_9;
		
		JLabel label_10 = new JLabel("");
		label_10.setOpaque(true);
		label_10.setBackground(Color.WHITE);
		label_10.setBounds(109, 225, 205, 23);
		add(label_10);
		mLoanDateLabel = label_10;
		JLabel label_11 = new JLabel("");
		label_11.setOpaque(true);
		label_11.setBackground(Color.WHITE);
		label_11.setBounds(109, 273, 205, 23);
		add(label_11);
		mLoanDateLengthLabel = label_11;
		mReturnUserTextField = new JTextField();
		mReturnUserTextField.setColumns(10);
		mReturnUserTextField.setBounds(109, 314, 205, 26);
		add(mReturnUserTextField);
		
		JLabel label_12 = new JLabel("");
		label_12.setOpaque(true);
		label_12.setBackground(Color.WHITE);
		label_12.setBounds(109, 361, 205, 23);
		add(label_12);
		mDateLabel = label_12;
		
		JLabel label_13 = new JLabel("");
		label_13.setOpaque(true);
		label_13.setBackground(Color.WHITE);
		label_13.setBounds(109, 413, 205, 23);
		add(label_13);
		
		mManagerLabel = label_13;
		
		JButton button = new JButton("完成");
		button.setBounds(230, 459, 117, 29);
		add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doneAction();
			}
		});
		mCodeTextField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				onCodeVauleChange(mCodeTextField.getText().trim());
			
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				onCodeVauleChange(mCodeTextField.getText().trim());


			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				onCodeVauleChange(mCodeTextField.getText().trim());

			}
		});
		
		mCurrentMaster = new User();
		mCurrentMaster.setName("admin");

		mManagerLabel.setText(mCurrentMaster.getName());

	}

	protected void doneAction() {
		DeviceLoanInfo loanInfo = new DeviceLoanInfo();

		loanInfo.setLoan(false);
		
	}
	public void onCodeVauleChange(String text) {
		if(!StringUtils.isEmpty(text)) {
			
			Device device = new Device();
			
			//查询一个device
			device.setDeviceID(text);
			
			device.setName("测试1");
			
			device.setCategory(new Category("类别1"));
			device.setWarehouse(new Warehouse("仓库1"));
			
			setDeviceValue(device);
			
			mCurrentDevice = device;
		}
		
		
	}
	
	public void setDeviceValue(Device device) {
		System.out.println(device);
		mNameLabel.setText(device.getName());
		mWarehouseLabel.setText(device.getWarehouse().getName());
		mLoanDateLabel.setText(DateUtils.getCurrentDateString());
		mLoanDateLengthLabel.setText("24H29M");
		mDateLabel.setText(DateUtils.getCurrentDateString());
		
		mLoanUserLabel.setText("张三");
	}
}
