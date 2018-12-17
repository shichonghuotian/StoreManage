package com.wy.store.modules.devices.category.parent;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.StringUtils;
import com.wy.store.common.eventbus.WEventBus;
import com.wy.store.common.view.WAlert;
import com.wy.store.db.dao.ParentCategoryDao;
import com.wy.store.db.dao.impl.ParentCategoryDaoImpl;
import com.wy.store.domain.ParentCategory;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

@ViewController(res = "/layout_device_parent_category_add")
public class DeviceParentCategoryAddController extends BaseViewController {

	@FXML
	TextField mNameTextField;
	@FXML
	TextField mCodeTextField;
	@FXML
	Label mTipLabel;

	ParentCategoryDao mCategoryDao;

	boolean isEdit;

	ParentCategory editCategory;

	@Override
	public void onCreate(WFxIntent intent) {
		// TODO Auto-generated method stub

		super.onCreate(intent);

		if(intent.getExtras() !=null) {
			editCategory = (ParentCategory) intent.getExtras().get("category");
			isEdit = (boolean) intent.getExtras().get("isEdit");
			if (editCategory == null) {

				isEdit = false;

			}
		}
		
		

		if (isEdit) {
			mCodeTextField.setDisable(true);
			mCodeTextField.setText(editCategory.getCode());
			mNameTextField.setText(editCategory.getName());
			setTitle("编辑总类别");

		} else {
			setTitle("添加总类别");

		}
		mCategoryDao = new ParentCategoryDaoImpl();

	}

	private void reset() {
		mNameTextField.setText("");
		mCodeTextField.setText("");
		mTipLabel.setText("");
	}

	public void saveAction(ActionEvent event) {

		if (isEdit) {
			edit();
		} else {
			add();
		}

	}

	protected void add() {
		String string = mNameTextField.getText().trim();
		String code = mCodeTextField.getText().trim();

		if (!StringUtils.isEmpty(string) && !StringUtils.isEmpty(code)) {

			// if(mCategoryDao.fi)
			if (mCategoryDao.isExist(string)) {

				mTipLabel.setText("类别名已经存在");

			} else if (mCategoryDao.isExistOfCode(code)) {
				mTipLabel.setText("类别编码不能重复");

			} else {

				ParentCategory category = new ParentCategory(string);
				category.setCode(code);

				mCategoryDao.add(category);
				WEventBus.getDefaultEventBus().post(new WParentCategoryEvent());

				WAlert.showMessageAlert("添加成功");

				reset();

			}

		} else {
			mTipLabel.setText("编码或者类别名称不能为空");
		}

	}

	protected void edit() {

		String string = mNameTextField.getText().trim();
//		String code = mCodeTextField.getText().trim();

		if (mCategoryDao.isExist(string)) {

			mTipLabel.setText("类别名已经存在");

		}else {
			editCategory.setName(string);
			mCategoryDao.update(editCategory);

			WEventBus.getDefaultEventBus().post(new WParentCategoryEvent());

			WAlert.showMessageAlert("修改成功");

			dismissController();
			
		}
		

	}

	public void cancelAction(ActionEvent event) {
		dismissController();

	}
}
