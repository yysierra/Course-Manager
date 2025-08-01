package control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import model.Course;

public class Controller implements Serializable {
	private final static Controller instance = new Controller();;
	private final static String VALID_USERNAME = "cse148";
	private final static String VALID_PASSWORD = "sccc1234";
	private ArrayList<Course> courses;

	private Controller() {
		courses = new ArrayList<>();
	}

	public static Controller getInstance() {
		return instance;
	}

	public boolean validateLogin(String username, String password) {
		return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
	}

	public boolean validateCourseInfo(String subject, String title, String credits, String instructor, String descript) {
		try {
			int testCredits = Integer.parseInt(credits);
			if (subject.isEmpty() || title.isEmpty() || credits.isEmpty() || instructor.isEmpty() || descript.isEmpty() ) {
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean validateStudentInfo(String firstName, String lastName, String major, double gpa) {
		if (firstName.isEmpty() || lastName.isEmpty() || major.isEmpty() || String.valueOf(gpa).isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean add(Course course) {
		if (course != null) {
			return courses.add(course);
		}
		return false;
	}

	public boolean remove(Course course) {
		for (Course cour : courses) {
			if (cour != null && cour.getCourseID() == course.getCourseID()) {
				courses.remove(cour);
				return true;
			}
		}
		return false;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void saveData() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data.dat"))) {
			oos.writeObject(instance);
			System.out.println("data was saved successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadData() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data.dat"))) {
			Controller loadedController = (Controller) ois.readObject();
			this.courses = (ArrayList<Course>) loadedController.courses;
			System.out.println("data was loaded successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
