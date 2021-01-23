package oec.cobol;

import java.util.ArrayList;

public class Period {
	
	private ArrayList<Subject> subjectList;
	
	public Period() {
		this.subjectList = new ArrayList<Subject>(10);
	}
	
	
	//Getters and Setters ---
	
	public ArrayList<Subject> getSubjectList() {
		return subjectList;
	}
	
	public void setSubjectList(ArrayList<Subject> sl) {
		subjectList = sl;
	}
	
	public void addSubject(Subject s) {
		subjectList.add(s);
	}
	

}
