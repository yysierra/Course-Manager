package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.Course;
import model.RemoveCourseListener;

public class CourseInformationView extends BorderPane {
	private Course course;
	private Label studentsInfo;
	private RemoveCourseListener removeCourselistener;

	public CourseInformationView(Course course, RemoveCourseListener removeCourselistener) {
		this.removeCourselistener = removeCourselistener;
		this.course = course;
		initializeGUI();
	}

	private void initializeGUI() {
		HBox header = new HBox();
		header.setAlignment(Pos.CENTER_LEFT);
		header.setPrefSize(800.0, 80.5);
		header.setStyle("-fx-background-color: linear-gradient(to top, #0052D4, #4364F7, #6FB1FC)");

		Label lblTop = new Label(course.getCourseTitle());
		lblTop.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
		lblTop.setFont(new Font(30.0));
		header.getChildren().add(lblTop);
		this.setTop(header);

		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setPadding(new Insets(30));
		gp.setHgap(8);
		gp.setVgap(8);

		Label lblSubject = new Label("Course Subject"), subjectInfo = new Label(course.getSubject());
		lblSubject.setStyle("-fx-font-weight: bold");
		gp.add(lblSubject, 0, 0);
		gp.add(subjectInfo, 1, 0);

		Label lblCredits = new Label("Credits"), creditsInfo = new Label(course.getCredits());
		lblCredits.setStyle("-fx-font-weight: bold");
		gp.add(lblCredits, 0, 1);
		gp.add(creditsInfo, 1, 1);

		Label lblInstructor = new Label("Instructor"), instructorInfo = new Label(course.getInstructor());
		lblInstructor.setStyle("-fx-font-weight: bold");
		gp.add(lblInstructor, 0, 2);
		gp.add(instructorInfo, 1, 2);
		
		Label lblStudents = new Label("Enrolled Students");
		studentsInfo = new Label(String.valueOf(course.getEnrolledStudents().size()));;
		lblStudents.setStyle("-fx-font-weight: bold");
		gp.add(lblStudents, 0, 3);
		gp.add(studentsInfo, 1, 3);
		
		Label lblDescription = new Label("Course Description"), descriptionInfo = new Label(course.getDescription());;
		lblDescription.setStyle("-fx-font-weight: bold");
		gp.add(lblDescription, 0, 4);
		gp.add(descriptionInfo, 1, 4);
		this.setCenter(gp);
		
		Label lblCourseID = new Label("Course ID"), courseIdInfo = new Label(String.valueOf(course.getCourseID()));;
		lblCourseID.setStyle("-fx-font-weight: bold");
		gp.add(lblCourseID, 0, 5);
		gp.add(courseIdInfo, 1, 5);
		
		Button btnDeleteCourse = new Button("Delete Course");
		btnDeleteCourse.setPrefSize(150, 83);
		btnDeleteCourse.setStyle("-fx-background-color: crimson; -fx-text-fill: white; -fx-font-weight: bold");
		gp.add(btnDeleteCourse, 0, 7);
		
		btnDeleteCourse.setOnAction(e -> {
			Course removedCourse = course;
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Course Deletetion");
			alert.setHeaderText("Course Deletetion");
			alert.setContentText("This course has been deleted");
			alert.showAndWait();
			if(removeCourselistener != null) {
				removeCourselistener.deleteCourse(removedCourse);
			}
		});
	}
}
