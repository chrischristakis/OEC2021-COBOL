package oec.cobol;

public class Person {
	
	private String fName, lName;
	private double infectivity = 0;		//Percent value of a student's chance of being infected
										//1.0 infection means the student is already infected
	
	/**
	 * Constructor 
	 * @param firstName
	 * @param lastName
	 */
	public Person(String firstName, String lastName) {
		this.fName = firstName;
		this.lName = lastName;
	}
	
	//Default constructor
	public Person() {}
	
	
	// Getters and Setters ---
	
	public double getInfectivity() {
		return infectivity;
	}
	
	public void setInfectivity(double i) {
		if(i > 1) i = 1;
		if(i < 0) i = 0;
		this.infectivity = i;
	}
	
	public void setFirstName(String fName) {
		this.fName = fName;
	}
	
	public void setLastName(String lName) {
		this.lName = lName;
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