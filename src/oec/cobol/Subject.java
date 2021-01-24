package oec.cobol;

import java.util.ArrayList;

public class Subject {
	/*
	 * This class represents a subject in a SINGLE PERIOD. Meaning that the class Physics A in Period 1 is a different object
	 * from Physics A in Period 2.
	 * Each subject stores the list of students, TAs and teachers in the room, and provides functionality to calculate
	 * the infectivity for students in that class, as well as for the class itself.
	 */
	
	public static final double R_NOUGHT = 3.0;
	public static final double ROOM_INFECTIVITY = 0.001;
	
	private String name;							//Name of the class
	private ArrayList<Student> studentList;			//List of students
	private ArrayList<TeachersAssistant> TAList;	//List of all TAs
	private Teacher teacher;						//The teacher of the class
	private double room_infectivity = 0.0;
	
	/**
	 * Constructor
	 * @param name
	 */
	public Subject(String name, Teacher teacher) {
		this.name = name;
		this.studentList = new ArrayList<Student>(20);
		this.TAList = new ArrayList<TeachersAssistant>(2);
		this.teacher = teacher;
	}
	
	public Subject(String name) {
		this.name = name;
		this.studentList = new ArrayList<Student>(20);
		this.TAList = new ArrayList<TeachersAssistant>(2);
	}
	
	//Default constructor
	public Subject() {
		this.studentList = new ArrayList<Student>(20);
		this.TAList = new ArrayList<TeachersAssistant>(2);
	}

	
	//Iterate through all the persons in the class at the current time to determine the infectivity value to be added to each person
	public double calculateInfectivityFromPersons() {
		double res = 0;
		for(Student s : studentList) {
			res += s.getInfectivity();
		}
		for(TeachersAssistant ta : TAList) {
			res += ta.getInfectivity();
		}
		if(teacher != null) res += teacher.getInfectivity();		//if a teacher variable is assigned
		//The room infectivity will also be added
		return this.room_infectivity + (res*R_NOUGHT)/(studentList.size() - res);
	}
	
	//Iterate through all the persons in the class at the current time to determine the infectivity value to be added to this room
	public double calculateRoomInfectivity() {
		double res = 0;
		for(Student s : studentList) {
			res += s.getInfectivity();
		}
		for(TeachersAssistant ta : TAList) {
			res += ta.getInfectivity();
		}
		if(teacher != null) res += teacher.getInfectivity();		//if a teacher variable is assigned
		return res * ROOM_INFECTIVITY;
	}
	
	//During lunch time, room infectivity will be reset
	public void resetRoomInfectivity() {
		this.room_infectivity = 0.0;
	}
	
	
	//Getters and Setters ---
	
	public String getClassName() {
		return name;
	}
	
	public void setClassName(String name) {
		this.name = name;
	}
	
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	
	public ArrayList<TeachersAssistant> getTAList() {
		return TAList;
	}
	
	public void setStudentList(ArrayList<Student> s) {
		this.studentList = s;
	}
	
	public void setTAList(ArrayList<TeachersAssistant> ta) {
		this.TAList = ta;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher t) {
		this.teacher = t;
	}
	
	public int getNumStudents() {
		return studentList.size();
	}
	
	public int getNumTAs() {
		return TAList.size();
	}
	
	public void addStudent(Student s) {
		studentList.add(s);
	}
	
	public void addTA(TeachersAssistant ta) {
		TAList.add(ta);
	}
	
	public double getInfectivity() {
		return room_infectivity;
	}
	
	public void setInfectivity(double i) {
		if(i > 1) i = 1;
		if(i < 0) i = 0;
		this.room_infectivity = i;
	}
	
}

