package oec.cobol;

import java.util.ArrayList;

public class Subject {
	
	public static final double R_NOUGHT = 3.0;
	
	private String name;						//Name of the class
	private ArrayList<Student> studentList;		//List of students
	private double infectivity = 0.0;
	
	/**
	 * Constructor
	 * @param name
	 */
	public Subject(String name) {
		this.name = name;
		this.studentList = new ArrayList<Student>(20);
	}
	
	public Subject() {}
	
	//Getters and Setters ---
	
	public String getClassName() {
		return name;
	}
	
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	
	public int getNumStudents() {
		return studentList.size();
	}
	
	public void addStudent(Student s) {
		studentList.add(s);
	}
	
	public double getInfectivity() {
		return infectivity;
	}
	
	public void setInfectivity(double i) {
		if(i > 1) i = 1;
		if(i < 0) i = 0;
		this.infectivity = i;
	}
	
	public double calculateInfectivityFromStudents() {
		double res = 0;
		for(Student s : studentList) {
			res += s.getInfectivity();
		}
		return (res*R_NOUGHT)/(studentList.size()-res);
	}
	
}
