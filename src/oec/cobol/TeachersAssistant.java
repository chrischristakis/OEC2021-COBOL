package oec.cobol;

public class TeachersAssistant extends Person {
	
	private String[] subjectArray;

	/**
	 * 
	 * @param firstName
	 * @param lastName
	 */
	public TeachersAssistant(String firstName, String lastName, String firstPeriod, String secondPeriod, String thirdPeriod, String fourthPeriod) {
		super(firstName, lastName);
		String[] subjectArray = {firstPeriod, secondPeriod, thirdPeriod, fourthPeriod};
		this.subjectArray = subjectArray;
	}
	
	public TeachersAssistant() {}

	
	// Getters and Setters ---
	
	public String getPeriodOneSubject() {
		return subjectArray[0];
	}
	
	public String getPeriodTwoSubject() {
		return subjectArray[1];
	}
	
	public String getPeriodThreeSubject() {
		return subjectArray[2];
	}
	
	public String getPeriodFourSubject() {
		return subjectArray[3];
	}
	
	public String[] getSubjectArray() {
		return subjectArray;
	}
	
	public void setSubject(String subject, int index)
	{
		subjectArray[index] = subject;
	}
	
}