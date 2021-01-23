package oec.cobol;

public class Teacher extends Person {
	private int ID;

	/**
	 * Constructor
	 * @param firstName
	 * @param lastName
	 */
	public Teacher(int ID, String firstName, String lastName) {
		super(firstName, lastName);
		this.ID = ID;
	}
	
	//Default constructor
	public Teacher() {}
	
	
	// Getters and Setters ---
	
	public int getID() {
		return ID;
	}
	
	public void setID(int id) {
		this.ID = id;
	}

}