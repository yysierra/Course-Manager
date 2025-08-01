package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
	private String subject;
	private int courseID;
	private String courseTitle;
	private String description;
	private String credits;
	private String instructor;
	private ArrayList<Student> students;

	private static int id = 1;

	public Course(String subject, String courseTitle, int credits, String instructor, String description) {
		this.subject = subject;
		this.courseTitle = courseTitle;
		this.credits = String.valueOf(credits);
		this.instructor = instructor;
		this.description = description;
		this.courseID = generateUniqueID();

		students = new ArrayList<>();
	}
	
	private static int generateUniqueID() {
		return id++;
	}

	public void enroll(Student student) {
		// enroll a student into the course
		if(student == null) {
			return;
		}
		if(students.contains(student)) {
			return;
		}
		students.add(student);
	}

	public boolean drop(Student student) {
		// drop a student based on last name
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i) != null && students.get(i).getLastName().equals(student.getLastName())) {
				students.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Student> getEnrolledStudents() {
		return students;
	}
	
	public boolean isEmpty() {
		return students.size() == 0;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public int getCourseID() {
		return courseID;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}
	
	public String getInstructor() {
		return instructor;
	}
	
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
