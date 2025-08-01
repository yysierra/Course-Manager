package view;

import control.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.EnrollmentListener;
import model.Student;

public class EnrollStudentView extends BorderPane {
	private EnrollmentListener enrollmentListener;
	private Button btnEnroll;
	
	public EnrollStudentView (EnrollmentListener enrollmentListener) {
		this.enrollmentListener = enrollmentListener;
		initializeGUI();
	}
	
	private void initializeGUI() {
		this.setPrefSize(800.0, 500.0);
		
		BorderPane studentInfo = new BorderPane ();
		
		HBox infoHeader = new HBox();
		infoHeader.setAlignment(Pos.CENTER);
		infoHeader.setPrefSize(800.0, 80.5);
		infoHeader.setStyle("-fx-background-color: linear-gradient(to top, #834d9b, #d04ed6)");
		Label lblInfo = new Label("Personal Info");
		lblInfo.setFont(new Font(30.0));
		lblInfo.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
		infoHeader.getChildren().add(lblInfo);
		studentInfo.setTop(infoHeader);
		this.setCenter(studentInfo);
		
		GridPane createStudentArea = new GridPane();
		createStudentArea.setAlignment(Pos.CENTER);
		createStudentArea.setPadding(new Insets(30));
		createStudentArea.setHgap(15);
		createStudentArea.setVgap(10);
		studentInfo.setCenter(createStudentArea);
		
		Label lblFirstName = new Label("First Name"), lblLastName = new Label("Last Name");
		lblFirstName.setStyle("-fx-font-weight: bold");
		lblLastName.setStyle("-fx-font-weight: bold");
		TextField tfFirstName = new TextField(), tfLastName = new TextField();
		createStudentArea.add(lblFirstName, 0, 0);
		createStudentArea.add(tfFirstName, 1, 0);
		createStudentArea.add(lblLastName, 2, 0);
		createStudentArea.add(tfLastName, 3, 0);
		
		Label lblMajor = new Label("Major");
		lblMajor.setStyle("-fx-font-weight: bold");
		TextField tfMajor = new TextField();
		createStudentArea.add(lblMajor, 0, 1);
		createStudentArea.add(tfMajor, 1, 1);
		
		Label lblGPA = new Label("GPA");
		TextField tfGPA = new TextField();
		lblGPA.setStyle("-fx-font-weight: bold");
		createStudentArea.add(lblGPA, 2, 1);
		createStudentArea.add(tfGPA, 3, 1);
		
		// create the enroll button 
		btnEnroll = new Button("Enroll Student");
		btnEnroll.setPrefSize(150, 83);
		btnEnroll.setStyle("-fx-background-color: mediumseagreen; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5px");
		createStudentArea.add(btnEnroll, 1, 4);
		
		btnEnroll.setOnAction(e -> {
			boolean validatedInfo = Controller.getInstance().validateStudentInfo(tfFirstName.getText(), 
					tfLastName.getText(), tfMajor.getText(), Double.parseDouble(tfGPA.getText()));
			Alert alert = null;
			if(validatedInfo) {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Student enrollment is successful!");
				alert.setHeaderText("Student has confirmed enrollment!");
				alert.setContentText("Student has been added to the course!");
				alert.showAndWait();
				Student newStudent = new Student(tfFirstName.getText(), 
						tfLastName.getText(), tfMajor.getText(), Double.parseDouble(tfGPA.getText()));
				if(enrollmentListener != null) {
					enrollmentListener.onEnrollment(newStudent);
				}
			}
			else {
				alert = new Alert(AlertType.ERROR);
				alert.setTitle("Student enrollment is unsuccessful");
				alert.setHeaderText("Student has invalid information");
				alert.setContentText("Please try again...");
				alert.showAndWait();
			}
		});
		
	}
}
