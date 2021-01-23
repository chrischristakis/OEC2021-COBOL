package oec.cobol;

public class Person {
	
	private String fName, lName;
	private double infectivity = 0;		//Percent value of a student's chance of being infected
										//100.0 infection means the student is already infected
	
	/**
	 * Constructor 
	 * @param firstName
	 * @param lastName
	 */
	public Person(String firstName, String lastName) {
		this.fName = firstName;
		this.lName = lastName;
	}
	
	
	
	// Getters and Setters ---
	
	public double getInfectivity() {
		return infectivity;
	}
	
	public void setInfectivity(double i) {
		this.infectivity = i;
	}
	
	public String getFirstName() {
		return fName;
	}
	
	public String getLastName() {
		return lName;
	}
	
	public String getFullName() {
		return fName + " " + lName;
	}

}
