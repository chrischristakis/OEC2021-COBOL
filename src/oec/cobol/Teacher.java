package oec.cobol;

public class Teacher extends Person {
	
	private String subject;
	private int ID;

	/**
	 * Contructor
	 * @param firstName
	 * @param lastName
	 */
	public Teacher(int ID, String firstName, String lastName, String subject) {
		super(firstName, lastName);
		this.ID = ID;
	}
	
	
	// Getters and Setters ---
	
	public String getSubject() {
		return this.subject;
	}
	
	public int getID() {
		return ID;
	}

}