package oec.cobol;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  


public class Main 
{
	
	static ArrayList<Student> students = new ArrayList<Student>();
	static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
	static ArrayList<TeachersAssistant> assistants = new ArrayList<TeachersAssistant>();
	
	static Period[] period = new Period[6]; //There are 6 periods of classes (1,2,lunch,3,4,extracurriculars)
	
	//Holds the information of the entire spreadsheet program
	private static XSSFWorkbook workbook;
	
	public static void main(String[] args) 
	{
		//Initialize all periods
		period[0] = new Period();
		period[1] = new Period();
		//period[2] = new Period();
		period[3] = new Period();
		period[4] = new Period();
		//period[5] = new Period();
		
		try  
		{  
			File file = new File("OEC2021 - School Record Book.xlsx");   //creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//This workbook refers to the entire XSLX file, and each sheet is contained in this workbook.
			workbook = new XSSFWorkbook(fis);   
			parseAllSheets();		
			workbook.close();
		}  
		catch(Exception e)  
		{  
			e.printStackTrace();  
		}  
		
		students.remove(students.get(students.size()-1));//remove last element for a bug fix.
		
		int periodNum = 1;
		for(int j = 0; j < period.length; j++) 
		{
			if(period[j] == null) continue;

			//Transfer infectivity
			if(j > 0 && j != 3)
			{
				for (String subject_name: period[j-1].getSubjectList().keySet())
				{
		            Subject subject = period[j-1].getSubjectList().get(subject_name); //previous subject room infectivity 
		            period[j].getSubjectList().get(subject_name).setInfectivity(subject.calculateRoomInfectivity());  
				}
			}
			period[j].updateInfectivityPerRoom();
			
			System.out.println("PERIOD " + periodNum);
			for(int i = 0; i < students.size(); i++)
				System.out.println(students.get(i).getID() + " " + students.get(i).getFullName() + " " + students.get(i).getInfectivity());
			
			System.out.println("ASSISTANTS:");
			for(int i = 0; i < assistants.size(); i++)
				System.out.println(assistants.get(i).getFullName() + " " + assistants.get(i).getInfectivity());
			System.out.println("\n\n\n\n\n");
			periodNum++;
		}
	}
	
	//parse through every excel spreadsheet
	public static void parseAllSheets() 
	{
		//4 sheets, 0: students, 1: Teachers, 2: Teaching assistants, 3: ZBY1 status
		for(int sheetNo = 0; sheetNo < 4; sheetNo++)
		{
			XSSFSheet sheet = workbook.getSheetAt(sheetNo);
			Iterator<Row> itr = sheet.iterator();
			
			itr.next(); //Skip the first row, since its only labels.
			while (itr.hasNext()) //Iterate each row of the sheet
			{
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				//First, check if the row is empty. We'll check the StudentNo arbitrarily, and if it's empty, then skip this row.
				//Also, the sheet spits out a bunch of random student id's of 0, so we need to check both.
				if(row.getCell(0) == null) //this means its empty
					continue; //Just skip it
				
				int cellNum = 0; //The index of the current cell we are on
				while (cellIterator.hasNext())	//Iterate over each column
				{
					Cell cell = cellIterator.next(); 
					switch(sheetNo)
					{
					case 0:
						getStudentCellInfo(cell, cellNum);
						break;
					case 1:
						getTeacherRecordCellInfo(cell, cellNum);
						break;
					case 2:
						getTeachingAssistantCellInfo(cell, cellNum);
						break;
					case 3:
						getZBY1StatusCellInfo(cell, cellNum);
						break;
					default:
						System.out.println("Error parsing excel sheets, this line shouldn't be showing.");
					}
					cellNum++; //Move onto the next cell
				} 
				System.out.println("");
			} 	
		}
	}
	
	static boolean TA = false;
	public static void getZBY1StatusCellInfo(Cell cell, int cellNum) 
	{
		switch (cellNum)	//Identify the info held in the current Column, then switch to the next one for next iteration          
		{  
			case 0: //STUDENTNO 
				if(cell.getCellTypeEnum() == CellType.STRING) {TA=true; break;} //Skip the studentNo if its 'N/A'
				students.get((int)cell.getNumericCellValue()-1).setInfectivity(1.0); //studentno-1 gives index of student in array of students.
				break;  
			case 1: //LASTNAME
				if(TA)
					for(int i = 0; i < assistants.size(); i++)
						if(assistants.get(i).getLastName().equals(cell.getStringCellValue()))
							assistants.get(i).setInfectivity(1.0);
				TA = false;
				break; 
			case 2: //FIRSTNAME
				break; 
			case 3: //STATUS
				break; 
			default:  
		}  
	}
	
	
	public static void getTeachingAssistantCellInfo(Cell cell, int cellNum) 
	{
		if(cellNum == 0)
			assistants.add(new TeachersAssistant());
		
		TeachersAssistant current = assistants.get(assistants.size()-1);
		
		String subject;
		switch (cellNum)	//Identify the info held in the current Column, then switch to the next one for next iteration          
		{  
			case 0: //LASTNAME
				current.setLastName(cell.getStringCellValue());
				break;  
			case 1: //FIRSTNAME
				current.setFirstName(cell.getStringCellValue());
				break; 
			case 2: //PERIOD 1 CLASS
				subject = cell.getStringCellValue();
				period[0].getSubjectList().get(subject).getTAList().add(current);
				break; 
			case 3: //PERIOD 2 CLASS
				subject = cell.getStringCellValue();
				period[1].getSubjectList().get(subject).getTAList().add(current);
				break; 
			case 4: //PERIOD 3 CLASS
				subject = cell.getStringCellValue();
				period[3].getSubjectList().get(subject).getTAList().add(current);
				break; 
			case 5: //PERIOD 4 CLASS
				subject = cell.getStringCellValue();
				period[4].getSubjectList().get(subject).getTAList().add(current);
				break; 
			default:  
		}  
	}

	public static void getTeacherRecordCellInfo(Cell cell, int cellNum)
	{
		if(cellNum == 0) 
			teachers.add(new Teacher()); //When we reach the first cell in a new row, create a new teacher.
		
		Teacher current = teachers.get(teachers.size()-1);
		
		switch (cellNum)	//Identify the info held in the current Column, then switch to the next one for next iteration          
		{  
			case 0: //TEACHERNO 
				current.setID((int)cell.getNumericCellValue());
				break;  
			case 1: //LASTNAME
				current.setLastName(cell.getStringCellValue());
				break; 
			case 2: //FIRSTNAME
				current.setFirstName(cell.getStringCellValue());
				break; 
			case 3: //CLASS
				String subject = cell.getStringCellValue();
				//Set this teacher as the teacher for a class in each period
				if(period[0].getSubject(subject) != null) period[0].getSubjectList().get(subject).setTeacher(current);
				if(period[1].getSubject(subject) != null) period[1].getSubjectList().get(subject).setTeacher(current);
				if(period[3].getSubject(subject) != null) period[3].getSubjectList().get(subject).setTeacher(current);
				if(period[4].getSubject(subject) != null) period[4].getSubjectList().get(subject).setTeacher(current);
				break; 
			default:  
		}  
	}

	public static void getStudentCellInfo(Cell cell, int cellNum) 
	{
		if(cellNum == 0) 
		{
			if(cell.getNumericCellValue() == 0) return; //to fix a weird bug where id of 0  added.
			else students.add(new Student()); //When we reach the first cell in a new row, create a new student.
		}
		
		Student current = students.get(students.size()-1); //Get the most recent student in the list
		
		String subject;
		switch (cellNum)	//Identify the info held in the current Column, then switch to the next one for next iteration          
		{  
			case 0: //STUDENTNO 
				current.setID((int)cell.getNumericCellValue());
				break;  
			case 1: //LASTNAME
				current.setLastName(cell.getStringCellValue());
				break; 
			case 2: //FIRSTNAME
				current.setFirstName(cell.getStringCellValue());
				break; 
			case 3: //GRADE
				current.setGrade((int)cell.getNumericCellValue());
				break; 
			case 4: //PERIOD 1 CLASS
				subject = cell.getStringCellValue();
				if(period[0].getSubjectList().get(subject) == null) //if period doesnt contains this class
					period[0].addSubject(new Subject(subject)); //then add it
						
				period[0].getSubject(subject).addStudent(current); //add the student to the subject in the subject list of a period
				break; 
			case 5: //PERIOD 2 CLASS
				subject = cell.getStringCellValue();
				if(period[1].getSubjectList().get(subject) == null) //if period doesnt contains this class
					period[1].addSubject(new Subject(subject)); //then add it
						
				period[1].getSubject(subject).addStudent(current); //add the student to the subject in the subject list of a period
				break; 
			case 6: //PERIOD 3 CLASS
				subject = cell.getStringCellValue();
				if(period[3].getSubjectList().get(subject) == null) //if period doesnt contains this class
					period[3].addSubject(new Subject(subject)); //then add it
						
				period[3].getSubject(subject).addStudent(current); //add the student to the subject in the subject list of a period
				break; 
			case 7: //PERIOD 4 CLASS
				subject = cell.getStringCellValue();
				if(period[4].getSubjectList().get(subject) == null) //if period doesnt contains this class
					period[4].addSubject(new Subject(subject)); //then add it
						
				period[4].getSubject(subject).addStudent(current); //add the student to the subject in the subject list of a period
				break; 
			case 8: //HEALTH CONDITION
				current.setHealthConditions(cell.getStringCellValue());
				break; 
			case 9: //EXTRACURRICULAR
				current.getInfectivity();
				break; 
			default:  
		}  
	}

}
