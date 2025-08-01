package view;

import control.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoginView extends GridPane {
	private final Stage currentStage;
	private TextField tfUsername;
	private PasswordField pfPassword;
	private Button btnLogin;

	public LoginView(Stage currentStage) {
		this.currentStage = currentStage;
		initializeGUI();
	}

	private void initializeGUI() {
		this.setPrefSize(500, 400);
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(10));
		this.setHgap(6);
		this.setVgap(6);

		Label lblUsername = new Label("Username"), lblPassword = new Label("Password");
		lblUsername.setStyle("-fx-font-family: tahoma; -fx-text-fill: white;");
		lblPassword.setStyle("-fx-font-family: tahoma; -fx-text-fill: white;");
		tfUsername = new TextField();
		pfPassword = new PasswordField();

		this.add(lblUsername, 0, 0);
		this.add(tfUsername, 1, 0);
		this.add(lblPassword, 0, 1);
		this.add(pfPassword, 1, 1);

		HBox loginRegisterButtonArea = new HBox();
		loginRegisterButtonArea.setAlignment(Pos.BOTTOM_CENTER);
		loginRegisterButtonArea.setSpacing(10);

		btnLogin = new Button("Login");
		btnLogin.setPrefSize(90, 40);
		this.add(btnLogin, 1, 3);
		
		this.setStyle("-fx-background-color: linear-gradient(to right, #11998e, #38ef7d); -fx-font-weight: bold;");

		btnLogin.setOnAction(e -> {
			boolean validated = Controller.getInstance().validateLogin(tfUsername.getText(), pfPassword.getText());
			Alert alert = null;
			if (validated) {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("User login is successful");
				alert.setHeaderText("Valid Credentials!");
				alert.setContentText("Enjoy Course Manager!");
				alert.showAndWait();
				Stage managerStage = new Stage();
				managerStage.setScene(new Scene(new ManagerView(managerStage)));
				managerStage.show();
				
				currentStage.close();
			} else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("User login is unsuccessful");
				alert.setHeaderText("Invalid Credentials!");
				alert.setContentText("Please try again...");
				alert.showAndWait();
			}
		});
	}

}
