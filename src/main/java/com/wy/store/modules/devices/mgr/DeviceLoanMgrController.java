package com.wy.store.modules.devices.mgr;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.hamcrest.SelfDescribing;

import com.wy.store.app.BaseViewController;
import com.wy.store.common.Utils.DateUtils;
import com.wy.store.db.dao.DeviceLoanInfoDao;
import com.wy.store.db.dao.impl.DeviceLoanInfoDaoImpl;
import com.wy.store.domain.DeviceLoanInfo;
import com.wy.wfx.core.ann.FXMLWindow;
import com.wy.wfx.core.ann.ViewController;
import com.wy.wfx.core.controller.WFxIntent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;

@ViewController(res = "/layout_device_loanmgr")
public class DeviceLoanMgrController extends BaseViewController {

	@FXMLWindow
	Window window;
	@FXML
	TableView<DeviceLoanInfo> mTableView;
	@FXML
	TextField mSearchTextField;
	@FXML
	ComboBox<String> mSearchComboBox;

	DeviceLoanInfoDao deviceLoanInfoDao;
	ObservableList<DeviceLoanInfo> observableList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		deviceLoanInfoDao = new DeviceLoanInfoDaoImpl();
		
		List<DeviceLoanInfo> list = deviceLoanInfoDao.getAll();
		
		observableList = FXCollections.observableList(list);


		TableColumn<DeviceLoanInfo, Long> idColumn = new TableColumn<>("序号");

		TableColumn<DeviceLoanInfo, String> codeColumn = new TableColumn<>("设备编号");

		TableColumn<DeviceLoanInfo, String> nameColumn = new TableColumn<>("设备名称");

		TableColumn<DeviceLoanInfo, String> loanUserColumn = new TableColumn<>("借用人");
		TableColumn<DeviceLoanInfo, String> loanDateColumn = new TableColumn<>("借用时间");
		TableColumn<DeviceLoanInfo, String> returnColumn = new TableColumn<>("归还人");
		TableColumn<DeviceLoanInfo, String> returnDateColumn = new TableColumn<>("归还时间");

		TableColumn<DeviceLoanInfo, String> statusColumn = new TableColumn<>("状态");

		TableColumn<DeviceLoanInfo, String> descColumn = new TableColumn<>("用途");

		TableColumn<DeviceLoanInfo, String> managerColumn = new TableColumn<>("管理员");

		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		codeColumn.setCellValueFactory((CellDataFeatures<DeviceLoanInfo, String> param) -> new SimpleStringProperty(
				param.getValue().getDevice().getDeviceId()));

		nameColumn.setCellValueFactory((CellDataFeatures<DeviceLoanInfo, String> param) -> new SimpleStringProperty(
				param.getValue().getDevice().getName()));

		loanUserColumn.setCellValueFactory((CellDataFeatures<DeviceLoanInfo, String> param) -> new SimpleStringProperty(
				param.getValue().getTakeUser().getName()));

		loanDateColumn.setCellValueFactory((CellDataFeatures<DeviceLoanInfo, String> param) -> new SimpleStringProperty(DateUtils.getCustomFormatDateString(
				param.getValue().getLoanDate())));

		returnColumn.setCellValueFactory((CellDataFeatures<DeviceLoanInfo, String> param) -> new SimpleStringProperty(
				param.getValue().getReturnUser()!=null?param.getValue().getReturnUser().getName():""));

		returnDateColumn.setCellValueFactory((CellDataFeatures<DeviceLoanInfo, String> param) -> new SimpleStringProperty(DateUtils.getCustomFormatDateString(
				param.getValue().getReturnDate())));
		statusColumn.setCellValueFactory((CellDataFeatures<DeviceLoanInfo, String> param) -> new SimpleStringProperty(param.getValue().isLoan()?"借出":"归还"));

		descColumn.setCellValueFactory((CellDataFeatures<DeviceLoanInfo, String> param) -> new SimpleStringProperty(
				param.getValue().getDescription()));
		
		managerColumn.setCellValueFactory((CellDataFeatures<DeviceLoanInfo, String> param) -> new SimpleStringProperty("admin"));
		
		ObservableList<TableColumn<DeviceLoanInfo, ?>> columns = mTableView.getColumns();
		columns.addAll(idColumn, codeColumn, nameColumn, loanUserColumn, loanDateColumn, returnColumn, returnDateColumn,
				statusColumn, descColumn, managerColumn);
		;

		mTableView.setItems(observableList);

		mSearchComboBox.getItems().addAll("设备编号", "设备名称");

		mSearchComboBox.getSelectionModel().select(0);

		mSearchTextField.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					beginSearch();
				});
	}

	public void beginSearch() {

	}

	public void loanAction(ActionEvent event) {
		try {
//			FxViewHander.showControllerInWindow(DeviceLoanController.class, window);
			
			presentController(new WFxIntent(DeviceLoanController.class));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void returnAction(ActionEvent event) {

		try {
			presentController(new WFxIntent(DeviceReturnController.class));

//			FxViewHander.showControllerInWindow(DeviceReturnController.class, window);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
