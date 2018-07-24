package com.wy.store.modules.devices.add;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.wy.store.bean.Category;
import com.wy.store.bean.Device;
import com.wy.store.bean.Warehouse;
import com.wy.store.common.view.comboModel.CategoryComboBoxModel;
import com.wy.store.common.view.comboModel.WarhouseComboBoxModel;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

//使用janel
public class DeviceAddView extends JPanel{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//名称
	private JTextField mNameTextField;


	//编号
	private JTextField mCodeTextField;
	
	//类别
	private JComboBox<String> mCategoryComboBox;
	private CategoryComboBoxModel mCategoryComboBoxModel;
	
	//仓库
	private JComboBox<String> mWarhoseComboBox;
	
	private WarhouseComboBoxModel mWarhoseComboBoxModel;

	
	
	
	public DeviceAddView() {
		initLayout();
	}
	
	
	public void saveAction() {
		
		Device device = new Device();
		
		device.setDeviceID(mCodeTextField.getText());
		device.setName(mNameTextField.getText());
		device.setCategory(mCategoryComboBoxModel.getSelectCategory());
		
		device.setWarehouse(mWarhoseComboBoxModel.getSelectWareHouse());
		
		System.out.println(device);
	}
	public void cancelAction() {
		
		JFrame f = (JFrame)getRootPane().getParent();
		
		f.dispose();
		
	}
	
	public void initLayout() {
		
		setLayout(null);
		
		JLabel label = new JLabel("设备编码：");
		label.setBounds(33, 59, 74, 16);
		add(label);
		
		mCodeTextField = new JTextField();
		mCodeTextField.setBounds(106, 54, 245, 26);
		add(mCodeTextField);
		mCodeTextField.setColumns(10);
		
		JLabel label_1 = new JLabel("设备名称：");
		label_1.setBounds(33, 14, 74, 16);
		add(label_1);
		
		mNameTextField = new JTextField();
		mNameTextField.setColumns(10);
		mNameTextField.setBounds(105, 9, 245, 26);
		add(mNameTextField);
		
		JLabel label_2 = new JLabel("设备类别：");
		label_2.setBounds(33, 113, 74, 16);
		add(label_2);
		
		mCategoryComboBox = new JComboBox<>();
		mCategoryComboBox.setBounds(106, 109, 245, 27);
		
		ArrayList<Category> list = new ArrayList<>();
		
		for(int i=0;i<10;i++) {
			list.add(new Category("类别" + i));
		}
		mCategoryComboBoxModel = new CategoryComboBoxModel(list);
		mCategoryComboBox.setModel(mCategoryComboBoxModel);
		add(mCategoryComboBox);
		
		
		JLabel label_3 = new JLabel("设备仓库：");
		label_3.setBounds(33, 171, 74, 16);
		add(label_3);
		
		mWarhoseComboBox = new JComboBox<>();
		mWarhoseComboBox.setBounds(106, 167, 245, 27);
		ArrayList<Warehouse> warhouseList = new ArrayList<>();
		
		for(int i=0;i<10;i++) {
			warhouseList.add(new Warehouse("仓库" + i));
		}
		mWarhoseComboBoxModel = new WarhouseComboBoxModel(warhouseList);
		mWarhoseComboBox.setModel(mWarhoseComboBoxModel);

		
		
		add(mWarhoseComboBox);
		
		JButton btnNewButton = new JButton("保存");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				saveAction();
			}
		});
		btnNewButton.setBounds(310, 232, 117, 29);
		add(btnNewButton);
		
		JButton button = new JButton("取消");
		button.setBounds(180, 232, 117, 29);
		add(button);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cancelAction();
			}
		});
	}
}
