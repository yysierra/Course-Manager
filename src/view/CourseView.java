package view;

import java.util.Comparator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Course;
import model.EnrollmentListener;
import model.Student;

public class CourseView extends BorderPane implements EnrollmentListener {
	private Stage nextStage;
	private Button btnEnroll;
	private Course course;
	private ObservableList<Student> studentList = FXCollections.observableArrayList();
	private Comparator<Student> currentComparator;

	public CourseView(Course course) {
		this.course = course;
		studentList.addAll(course.getEnrolledStudents());
		initalizeGUI();
	}

	private void initalizeGUI() {
		this.setPadding(new Insets(20, 20, 0, 20));

		Label courseTitleLabel = new Label(course.getCourseTitle());
		this.setTop(courseTitleLabel);

		TableView<Student> studentView = new TableView<>();
		studentView.setEditable(true);

		TableColumn<Student, String> firstNameCol = new TableColumn<>("First Name");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

		TableColumn<Student, String> lastNameCol = new TableColumn<>("Last Name");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		TableColumn<Student, String> majorCol = new TableColumn<>("Major");
		majorCol.setCellValueFactory(new PropertyValueFactory<>("major"));

		TableColumn<Student, Double> gpaCol = new TableColumn<>("GPA");
		gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));

		TableColumn<Student, String> gradeCol = new TableColumn<>("Grade");
		gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
		gradeCol.setCellFactory(TextFieldTableCell.forTableColumn());
		gradeCol.setEditable(false);

		studentView.getColumns().addAll(firstNameCol, lastNameCol, majorCol, gpaCol, gradeCol);
		studentView.setItems(studentList);

		firstNameCol.setSortable(true);
		lastNameCol.setSortable(true);
		majorCol.setSortable(true);
		gpaCol.setSortable(true);
		gradeCol.setSortable(true);
		studentView.setPlaceholder(new Label("Student List is empty. Enroll students to begin."));

		HBox buttonBox = new HBox();
		buttonBox.setPadding(new Insets(6));
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.setSpacing(10);

		ComboBox<String> sortOptionsBox = new ComboBox<>();
		sortOptionsBox.getItems().addAll("First Name", "Last Name", "Major", "GPA");
		sortOptionsBox.setPromptText("Sort By");

		sortOptionsBox.setOnAction(e -> {
			String selectedValue = sortOptionsBox.getValue();
			if ("First Name".equals(selectedValue)) {
				currentComparator = Comparator.comparing(Student::getFirstName);
			} else if ("Last Name".equals(selectedValue)) {
				currentComparator = Comparator.comparing(Student::getLastName);
			} else if ("Major".equals(selectedValue)) {
				currentComparator = Comparator.comparing(Student::getMajor);
			} else if ("GPA".equals(selectedValue)) {
				currentComparator = Comparator.comparing(Student::getGpa);
			}
			studentList.sort(currentComparator);
		});

		btnEnroll = new Button("Enroll Student");
		buttonBox.getChildren().addAll(sortOptionsBox, btnEnroll);
		this.setTop(buttonBox);

		btnEnroll.setOnAction(e -> {
			nextStage = new Stage();
			nextStage.initModality(Modality.APPLICATION_MODAL);
			nextStage.setScene(new Scene(new EnrollStudentView(this)));
			nextStage.setTitle("Enroll a Student");
			nextStage.show();

		});

		ContextMenu contextMenu = new ContextMenu();
		MenuItem dropItem = new MenuItem("Drop Student"), updateItem = new MenuItem("Update Grade");

		dropItem.setOnAction(e -> {
			Student selectedStudent = studentView.getSelectionModel().getSelectedItem();
			if (selectedStudent != null) {
				studentList.remove(selectedStudent);
				course.drop(selectedStudent);
			}
		});

		BooleanProperty editingInProgress = new SimpleBooleanProperty(false);

		updateItem.setOnAction(e -> {
			Student selectedStudent = studentView.getSelectionModel().getSelectedItem();
			if (selectedStudent != null) {
				int selectedIndex = studentView.getSelectionModel().getSelectedIndex();
				gradeCol.setEditable(true);
				studentView.edit(selectedIndex, gradeCol);
				studentView.getFocusModel().focus(selectedIndex, gradeCol);

				gradeCol.setOnEditCommit(event -> {
					Student student = event.getTableView().getItems().get(event.getTablePosition().getRow());
					String newGrade = event.getNewValue().toUpperCase();
					if (student.validGrade(newGrade)) {
						student.setGrade(newGrade);
						studentView.refresh();
					} else {
						Alert invalidGrade = new Alert(AlertType.ERROR);
						invalidGrade.setTitle("Error");
						invalidGrade.setHeaderText("Grade input error");
						invalidGrade.setContentText(
								"Invalid grade!! Please try again..." + "Reminder of valid grades: A, B, C, D, E, F");
						invalidGrade.showAndWait();
						studentView.refresh();
					}
					editingInProgress.set(false);
				});

				gradeCol.setOnEditCancel(event -> {
					gradeCol.setEditable(false);
					editingInProgress.set(false);
				});
				editingInProgress.set(true);
			}
		});

		contextMenu.getItems().addAll(dropItem, updateItem);
		studentView.setContextMenu(contextMenu);

		this.setCenter(studentView);

	}

	@Override
	public void onEnrollment(Student student) {
		studentList.add(student);
		course.enroll(student);
		nextStage.close();
	}

}
