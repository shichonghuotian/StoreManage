package com.wy.store.common.view;

import javafx.scene.control.Alert;

public class WAlert extends Alert{

	private WAlert(AlertType alertType) {
		super(alertType);
		// TODO Auto-generated constructor stub
	}

	
	
	public static final class Builder {
		AlertType alertType;
		String title;
		String message;
		
		public Builder() {
			this.alertType = AlertType.INFORMATION;
		}
		public Builder(AlertType alertType) {
			
			this.alertType = alertType;
		}
		
		public Builder alertType(AlertType alertType) {
			
			this.alertType = alertType;
			
			return this;
		}
		
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		public Builder message(String message) {
			
			this.message = message;
			
			return this;
		}
		
		public WAlert create() {
			
			WAlert alert = new WAlert(alertType);
			alert.setHeaderText(null);

			alert.setTitle(title);
			alert.setContentText(message);
			return alert;
			
			
		}
	}
	
	
	public static void showMessageAlert(String message) {
		
		new WAlert.Builder().message(message).create().show();

	}
	
	
}
