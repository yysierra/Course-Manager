package view;

import control.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.CreateCourseListener;

public class Start extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Controller.getInstance().loadData();
		
		Scene scene = new Scene(new LoginView(stage));
		stage.setScene(scene);
		stage.setTitle("Course Manager");
		stage.show();
		
	}

}
