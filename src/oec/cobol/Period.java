package oec.cobol;

import java.util.HashMap;

public class Period {
	
	private HashMap<String, Subject> subjectList;
	
	public Period() {
		this.subjectList = new HashMap<String, Subject>(10);
	}
	
	//Getters and Setters ---
	
	public HashMap<String, Subject> getSubjectList() {
		return subjectList;
	}
	
	public void addSubject(Subject subject) {
		subjectList.put(subject.getClassName(), subject);
	}
	
	public Subject getSubject(String s)
	{
		return subjectList.get(s);
	}
	
	public void updateInfectivityPerRoom() {
	    /*
	     * For each class in this period, calculate the determined total infectivity that needs to increase for each student (R_nought)
	     * We then increment everyone's likeliness of being infected by that amount (All students, TAs, and teachers)
	     * We also use the infectivity of the people in the room to calculate the subsequent infectivity of the room
	     */
		for (String subject_name: subjectList.keySet()){
			Subject c = subjectList.get(subject_name);
	        double classInfectivity = c.calculateInfectivityFromPersons();
	        c.setInfectivity(c.getInfectivity() + c.calculateRoomInfectivity());
	        for(Student s : c.getStudentList()) {
	            s.setInfectivity(s.getInfectivity() + classInfectivity * s.getMultHealthConditions() * s.getMultGrade());
	        }
	        for(TeachersAssistant ta : c.getTAList()) {
	            ta.setInfectivity(ta.getInfectivity() + classInfectivity);
	        }
	        if(c.getTeacher() != null) c.getTeacher().setInfectivity(c.getTeacher().getInfectivity() + classInfectivity);
	    }
	}
	

}
