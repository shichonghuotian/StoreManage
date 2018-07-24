package com.wy.store.modules.devices.loan;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;

//借出页面--- //
public class DeviceLoanView extends JPanel{
	//编码，也可以自动填写
	private JTextField mCodeTextField;

	//名称。这一个可以自动填写
	private JLabel mNameLabel;
	//存储地点
	private JLabel mWarehouseLabel;
	//显示当前时间即可，
	private JLabel mDateLabel;
	
	//借用人---这里需要 监听指纹录入
	private JTextField mTakerTextField;

	
	//管理员///当前的master用户
	private JLabel mManagerLabel;

	private JTextArea mUsageTextArea;
	
	
	private Device mCurrentDevice;
	
	private User mCurrentMaster;
	
	//借用人
	private User mTakeUser;

	public DeviceLoanView() {

		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("设备编码：");
		lblNewLabel.setBounds(22, 41, 106, 23);
		add(lblNewLabel);
		
		JLabel label = new JLabel("设备名称：");
		label.setBounds(22, 76, 106, 23);
		add(label);
		
		JLabel label_1 = new JLabel("仓库：");
		label_1.setBounds(22, 120, 106, 23);
		add(label_1);
		
		JLabel label_2 = new JLabel("借出日期：");
		label_2.setBounds(22, 160, 106, 23);
		add(label_2);
		
		JLabel label_3 = new JLabel("借用人：");
		label_3.setBounds(22, 205, 106, 23);
		add(label_3);
		
		JLabel label_4 = new JLabel("管理员：");
		label_4.setBounds(22, 250, 106, 23);
		add(label_4);
		
		mCodeTextField = new JTextField();
		mCodeTextField.setBounds(90, 39, 198, 26);
		
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
		add(mCodeTextField);
		mCodeTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		mNameLabel = lblNewLabel_1;
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(90, 79, 198, 16);
		add(lblNewLabel_1);
		
		JLabel label_5 = new JLabel("");
		label_5.setOpaque(true);
		mWarehouseLabel = label_5;
		label_5.setBackground(Color.WHITE);
		label_5.setBounds(90, 123, 198, 16);
		add(label_5);
		
		JLabel label_6 = new JLabel("");
		label_6.setOpaque(true);
		mDateLabel = label_6;
		label_6.setBackground(Color.WHITE);
		label_6.setBounds(90, 167, 198, 16);
		add(label_6);
		
		mTakerTextField = new JTextField();
		mTakerTextField.setColumns(10);
		mTakerTextField.setBounds(90, 203, 198, 26);
		add(mTakerTextField);
		
		JLabel label_7 = new JLabel("");
		label_7.setOpaque(true);
		mManagerLabel = label_7;
		label_7.setBackground(Color.WHITE);
		
		label_7.setBounds(90, 253, 198, 16);
		add(label_7);
		
		JLabel label_8 = new JLabel("用途：");
		label_8.setBounds(22, 300, 106, 23);
		add(label_8);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(90, 303, 198, 72);
		add(textArea);
		mUsageTextArea = textArea;
		
		JButton button = new JButton("完成");
		button.setBounds(254, 392, 117, 29);
		add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				doneAction();
			}
		});
		mCurrentMaster = new User();
		mCurrentMaster.setName("admin");

		mManagerLabel.setText(mCurrentMaster.getName());

	}
	
	//注意提示当前的设备是否借出，手动输入的
	public void doneAction() {
		DeviceLoanInfo loanInfo = new DeviceLoanInfo();
		loanInfo.setLoan(true);

		loanInfo.setDevice(mCurrentDevice);
		
		loanInfo.setMaster(mCurrentMaster);
		
		mTakeUser = new User();
		
		mTakeUser.setName(mTakerTextField.getText());
		
		loanInfo.setTakeUser(mTakeUser);
		
		loanInfo.setDescription(mUsageTextArea.getText());
		
		loanInfo.setLoanDate(new Date(System.currentTimeMillis()));
		
		System.out.println(loanInfo);
	}
	
	public void onCodeVauleChange(String text) {
		
		System.out.println("onCodeVauleChange " + text);
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
		
		mDateLabel.setText(DateUtils.getCurrentDateString());
		
	}
}
