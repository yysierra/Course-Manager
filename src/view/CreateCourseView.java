package view;

import control.Controller;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Course;
import model.CreateCourseListener;

public class CreateCourseView extends BorderPane {
	private Alert invalidInput;
	private CreateCourseListener listener;

	public CreateCourseView(CreateCourseListener listener) {
		this.listener = listener;
		intializeGUI();
	}

	private void intializeGUI() {
		this.setPrefSize(800.0, 500.0);

		HBox header = new HBox();
		header.setAlignment(Pos.CENTER);
		header.setPrefSize(800.0, 80.5);
		header.setStyle("-fx-background-color: linear-gradient(to top, #FDC830, #F37335)");

		Label lblTop = new Label("Create a New Course");
		lblTop.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
		lblTop.setFont(new Font(30.0));
		header.getChildren().add(lblTop);

		GridPane gp = new GridPane();
		gp.setAlignment(Pos.TOP_LEFT);
		gp.setPadding(new Insets(30));
		gp.setHgap(8);
		gp.setVgap(8);

		Label lblSubject = new Label("Course Subject");
		lblSubject.setStyle("-fx-font-weight: bold");
		ComboBox<String> subjects = new ComboBox<>();
		subjects.setPromptText("Please select a Course");
		subjects.getItems().addAll("MAT", "BIO", "HIS", "CSE");
		gp.add(lblSubject, 0, 0);
		gp.add(subjects, 1, 0);

		Label lblTitle = new Label("Course Title");
		lblTitle.setStyle("-fx-font-weight: bold");
		TextField tfTitle = new TextField();
		tfTitle.setPromptText("Provide the full name of your course");
		gp.add(lblTitle, 0, 1);
		gp.add(tfTitle, 1, 1);

		Label lblCredits = new Label("Credits");
		lblCredits.setStyle("-fx-font-weight: bold");
		TextField tfCredits = new TextField();
		gp.add(lblCredits, 0, 2);
		gp.add(tfCredits, 1, 2);

		Label lblInstructor = new Label("Instructor");
		lblInstructor.setStyle("-fx-font-weight: bold");
		TextField tfInstructor = new TextField();
		gp.add(lblInstructor, 0, 3);
		gp.add(tfInstructor, 1, 3);

		Label lblDescription = new Label("Course Description");
		lblDescription.setStyle("-fx-font-weight: bold");
		TextArea tfDescript = new TextArea();
		tfDescript.setPromptText("Provide a descriptive info about the course. Description must be less than 30 words");
		gp.add(lblDescription, 0, 4);
		gp.add(tfDescript, 1, 4);

		// create the 'create course' button
		Button btnCreateCourse = new Button("Create Course");
		btnCreateCourse.setPrefSize(150, 83);
		btnCreateCourse.setStyle("-fx-background-color: mediumseagreen; -fx-text-fill: white; -fx-font-weight: bold");
		gp.add(btnCreateCourse, 1, 5);

		tfDescript.setOnKeyTyped(e -> {
			if (!"\b".equals(e.getCharacter())) {
				String[] words = tfDescript.getText().split("\\s+");
				if (words.length >=30) {
					showErrorAlert("Exceeding Description length",
							"Your description MUST be less than 30 words!!\nPlease try again...");
					btnCreateCourse.setDisable(true);
					e.consume();
				}
			} else {
				btnCreateCourse.setDisable(false);
			}
		});

		btnCreateCourse.setOnAction(e -> {
			boolean validatedCourse = Controller.getInstance().validateCourseInfo(subjects.getValue(), tfTitle.getText(), tfCredits.getText(), tfInstructor.getText(), tfDescript.getText());
			Alert alert = null;
			if (validatedCourse) {
				alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Course Creation");
				alert.setHeaderText("Course creation confirmation");
				alert.setContentText("Course has been successfully created");
				alert.showAndWait();
				Course newCourse = new Course(subjects.getValue(), tfTitle.getText(),
						Integer.parseInt(tfCredits.getText()), tfInstructor.getText(), tfDescript.getText());
				Controller.getInstance().add(newCourse);
				if (listener != null) {
					listener.createdCourse(newCourse);
				}
			} else {
				showErrorAlert("Input Error", "One or two more inputs are blank...Please try again");
			}
		});

		this.setTop(header);
		this.setCenter(gp);
	}

	private void showErrorAlert(String header, String content) {
		invalidInput = new Alert(AlertType.ERROR);
		invalidInput.setTitle("Error");
		invalidInput.setHeaderText(header);
		invalidInput.setContentText(content);
		invalidInput.showAndWait();
	}

}
