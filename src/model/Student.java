package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Student implements Serializable {
	private String firstName;
	private String lastName;
	private String major;
	private double gpa;
	private String grade; // valid values are a, b, c, d, f, u
	private final static ArrayList<String> VALID_GRADES = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "F", "U"));

	public Student(String firstName, String lastName, String major, double gpa) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.major = major;
		this.gpa = gpa;
		grade = "U";
				
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public boolean validGrade(String grade) {
		return VALID_GRADES.contains(grade);
	}
}
