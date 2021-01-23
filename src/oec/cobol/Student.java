package oec.cobol;

public class Student extends Person {
	
	private int grade;
	private String healthConditions = "N/A";
	private int ID;
	
	/**
	 * Constructor
	 * @param firstName
	 * @param lastName
	 * @param grade
	 * @param heatlhConditions
	 */
	public Student(int ID, String firstName, String lastName, int grade, String healthConditions) {
		super(firstName, lastName);
		this.healthConditions = healthConditions;
		this.grade = grade;
		this.ID = ID;
	}

	
	// Getters and Setters ---
	
	public String getHealthConditions() {
		return this.healthConditions;
	}
	
	public int getGrade() {
		return grade;
	}
	
	//For each grade above grade 9, we add a 25% higher chance of infection
	public double getMultGrade() {
		return 1 + (grade - 9) * 0.25;
	}
	
	//If health conditions exist, there is a 70% higher chance of infection
	public double getMultHealthConditions() {
		if (healthConditions.equals("N/A")) return 1.7;
		else return 1.0;
	}
	
	public int getID() {
		return ID;
	}
	
}