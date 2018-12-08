package com.wy.store.app;

import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class StoreSplashScreen {

//	private static String DEFAULT_IMAGE = "/splash/javafx.png";
//
//	/**
//	 * Override this to create your own splash pane parent node.
//	 *
//	 * @return A standard image
//	 */
	public Parent getParent() {
		final ImageView imageView = new ImageView(getClass().getResource(getImagePath()).toExternalForm());

		imageView.setFitWidth(512);
		imageView.setFitHeight(384);
		final ProgressBar splashProgressBar = new ProgressBar();
		splashProgressBar.setPrefWidth(imageView.getFitWidth());

		final VBox vbox = new VBox();
		vbox.getChildren().addAll(imageView, splashProgressBar);

		return vbox;
	}
//
//	/**
//	 * Customize if the splash screen should be visible at all.
//	 *
//	 * @return true by default
//	 */
	public boolean visible() {
		return true;
	}

//	/**
//	 * Use your own splash image instead of the default one.
//	 *
//	 * @return "/splash/javafx.png"
//	 */
	public String getImagePath() {
		return "/bg.jpg";
	}

}
