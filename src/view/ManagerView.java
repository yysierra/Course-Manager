package view;

import java.util.HashMap;
import java.util.Map;

import control.Controller;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Course;
import model.CreateCourseListener;
import model.RemoveCourseListener;

public class ManagerView extends BorderPane implements CreateCourseListener, RemoveCourseListener {
	private Stage primaryStage;
	private Stage nextStage;
	private VBox courseMenu;
	private Button btnAddCourse;
	private Button btnCourse;
	private Map<Button, Course> courseScenes = new HashMap<>();

	public ManagerView(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Course Manager");
		initializeGUI();
		loadCourses();
	}

	private void initializeGUI() {
		this.setPrefSize(925.0, 601.0);

		courseMenu = new VBox();
		courseMenu.setPrefSize(155.0, 497.0);
		courseMenu.setStyle("-fx-background-color: linear-gradient(to right, #11998e, #38ef7d); -fx-background-radius: 0 20px 20px 0;");
		btnAddCourse = new Button("Add course");
		btnAddCourse.setPrefSize(163.0, 84.0);
		btnAddCourse.setStyle("-fx-background-color: linear-gradient(to right, #11998e, #38ef7d); -fx-text-fill: white; -fx-font-weight: bold");
		courseMenu.getChildren().add(btnAddCourse);

		HBox topMenu = new HBox();
		topMenu.setAlignment(Pos.CENTER);
		topMenu.setPrefSize(925.0, 104.0);
		Label lblWelcome = new Label("Welcome to Course Manager!");
		lblWelcome.setStyle("-fx-text-fill: white; -fx-font-weight: bold");
		lblWelcome.setFont(new Font(30.0));
		topMenu.setStyle("-fx-background-color: linear-gradient(to top, #11998e, #38ef7d)");
		topMenu.getChildren().add(lblWelcome);
		
		Label lblMainInfo = new Label("Choose an existing course or click on Add Course to begin");
		lblMainInfo.setFont(new Font(30.0));

		this.setLeft(courseMenu);
		this.setTop(topMenu);
		this.setCenter(lblMainInfo);

		btnAddCourse.setOnAction(e -> {
			nextStage = new Stage();
			nextStage.initModality(Modality.APPLICATION_MODAL);
			nextStage.setScene(new Scene(new CreateCourseView(this)));
			nextStage.setTitle("Create a Course");
			nextStage.show();
		});
		
		primaryStage.setOnCloseRequest(e -> Controller.getInstance().saveData());
	}

	@Override
	public void createdCourse(Course newCourse) {
		btnCourse = new Button(newCourse.getCourseTitle());
		btnCourse.setPrefSize(163.0, 84.0);
		btnCourse.setStyle("-fx-background-color: linear-gradient(to right, #11998e, #38ef7d); -fx-text-fill: white; -fx-font-weight: bold");

		BorderPane studentList = new CourseView(newCourse), courseInfo = new CourseInformationView(newCourse, this);
		courseScenes.put(btnCourse, newCourse);
		btnCourse.setOnAction(e -> {
			HBox topMenu = new HBox();
			topMenu.setAlignment(Pos.CENTER);
			topMenu.setPrefSize(925.0, 104.0);
			topMenu.setStyle("-fx-background-color: linear-gradient(to top, #11998e, #38ef7d)");
			
			Button btnViewStudents = new Button("View Students"), btnViewCourseInfo = new Button("Course Information");
			btnViewStudents.setPrefSize(163.0, 84.0);
			btnViewCourseInfo.setPrefSize(163.0, 84.0);
			btnViewStudents.setStyle("-fx-background-color: linear-gradient(to top, #11998e, #38ef7d); -fx-text-fill: white; -fx-font-weight: bold");
			btnViewCourseInfo.setStyle("-fx-background-color: linear-gradient(to top, #11998e, #38ef7d); -fx-text-fill: white; -fx-font-weight: bold");
			
			btnViewStudents.setOnAction(event -> {
				this.setCenter(studentList);
			});
			btnViewCourseInfo.setOnAction(event2 -> {
				this.setCenter(courseInfo);
			});
			btnViewCourseInfo.fire();
			topMenu.getChildren().addAll(btnViewStudents, btnViewCourseInfo);
			this.setTop(topMenu);
		});
		courseMenu.getChildren().add(btnCourse);
		nextStage.close();
	}
	
	private void loadCourses() {
		for (Course course: Controller.getInstance().getCourses()) {
			btnCourse = new Button(course.getCourseTitle());
			btnCourse.setPrefSize(163.0, 84.0);
			btnCourse.setStyle("-fx-background-color: linear-gradient(to right, #11998e, #38ef7d); -fx-text-fill: white; -fx-font-weight: bold");
			
			BorderPane studentList = new CourseView(course), courseInfo = new CourseInformationView(course, this);
			courseScenes.put(btnCourse, course);
			btnCourse.setOnAction(e -> {
				HBox topMenu = new HBox();
				topMenu.setAlignment(Pos.CENTER);
				topMenu.setPrefSize(925.0, 104.0);
				topMenu.setStyle("-fx-background-color: linear-gradient(to top, #11998e, #38ef7d)");
				
				Button btnViewStudents = new Button("View Students"), btnViewCourseInfo = new Button("Course Information");
				btnViewStudents.setPrefSize(163.0, 84.0);
				btnViewCourseInfo.setPrefSize(163.0, 84.0);
				btnViewStudents.setStyle("-fx-background-color: linear-gradient(to top, #11998e, #38ef7d); -fx-text-fill: white; -fx-font-weight: bold");
				btnViewCourseInfo.setStyle("-fx-background-color: linear-gradient(to top, #11998e, #38ef7d); -fx-text-fill: white; -fx-font-weight: bold");
				
				btnViewStudents.setOnAction(event -> {
					this.setCenter(studentList);
				});
				btnViewCourseInfo.setOnAction(event2 -> {
					this.setCenter(courseInfo);
				});
				btnViewCourseInfo.fire();
				topMenu.getChildren().addAll(btnViewStudents, btnViewCourseInfo);
				this.setTop(topMenu);
			});
			courseMenu.getChildren().add(btnCourse);
		}
		
	}

	@Override
	public void deleteCourse(Course course) {
		for(Map.Entry<Button, Course> entry : courseScenes.entrySet()) {
			if(entry.getValue() == course) {
				Button removedButton = entry.getKey();
				courseMenu.getChildren().remove(removedButton);
				courseScenes.remove(entry.getValue(), course);
				Controller.getInstance().remove(course);
				break;
			}
		}
		
		HBox topMenu = new HBox();
		topMenu.setAlignment(Pos.CENTER);
		topMenu.setPrefSize(925.0, 104.0);
		Label lblWelcome = new Label("Welcome to Course Manager!");
		lblWelcome.setStyle("-fx-text-fill: white; -fx-font-weight: bold");
		lblWelcome.setFont(new Font(30.0));
		topMenu.setStyle("-fx-background-color: linear-gradient(to top, #11998e, #38ef7d)");
		topMenu.getChildren().add(lblWelcome);
		
		Label lblMainInfo = new Label("Choose an existing course or click on Add Course to begin");
	    lblMainInfo.setFont(new Font(30.0));
	    this.setCenter(lblMainInfo);
	    this.setTop(topMenu);
	}

}
