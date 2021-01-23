package oec.cobol;

public class TeachersAssistant extends Person {
	
	private int ID;
	
	/**
	 * Constructor
	 * @param firstName
	 * @param lastName
	 */
	public TeachersAssistant(int id, String firstName, String lastName) {
		super(firstName, lastName);
		this.ID = id;
	}
	
	//Default constructor
	public TeachersAssistant() {}	
	
	
	//Getters and Setters ---
	
	public void setID(int id) {
		this.ID = id;
	}
	
	public int getID() {
		return ID;
	}
}