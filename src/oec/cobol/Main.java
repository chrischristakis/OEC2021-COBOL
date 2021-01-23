package oec.cobol;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  


public class Main 
{
	
	static ArrayList<Student> students = new ArrayList<Student>();
	static HashMap<String, Subject> subjectList = new HashMap<String, Subject>(); //access each subject by name.
	static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
	static ArrayList<TeachersAssistant> assistants = new ArrayList<TeachersAssistant>();
	
	static Period[] period = new Period[6]; //There are 6 periods of classes (1,2,lunch,3,4,extracurriculars)
	
	//Holds the information of the entire spreadsheet program
	private static XSSFWorkbook workbook;
	
	public static void main(String[] args) 
	{
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
		
		System.out.println(period[0]);
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
	
	public static void getZBY1StatusCellInfo(Cell cell, int cellNum) 
	{
		switch (cellNum)	//Identify the info held in the current Column, then switch to the next one for next iteration          
		{  
			case 0: //STUDENTNO 
				if(cell.getCellTypeEnum() == CellType.STRING) return; //Skip the studentNo if its 'N/A'
				System.out.print(cell.getNumericCellValue() + "\t\t\t");
				break;  
			case 1: //LASTNAME
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				break; 
			case 2: //FIRSTNAME
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				break; 
			case 3: //STATUS
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				break; 
			default:  
		}  
	}
	
	
	public static void getTeachingAssistantCellInfo(Cell cell, int cellNum) 
	{
		if(cellNum == 0)
			assistants.add(new TeachersAssistant());
		
		TeachersAssistant current = assistants.get(assistants.size()-1);
		
		switch (cellNum)	//Identify the info held in the current Column, then switch to the next one for next iteration          
		{  
			case 0: //LASTNAME
				System.out.print(cell.getStringCellValue() + "\t\t\t");
				current.setLastName(cell.getStringCellValue());
				break;  
			case 1: //FIRSTNAME
				System.out.print(cell.getStringCellValue() + "\t\t\t"); 
				current.setFirstName(cell.getStringCellValue());
				break; 
			case 2: //PERIOD 1 CLASS
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				break; 
			case 3: //PERIOD 2 CLASS
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				break; 
			case 4: //PERIOD 3 CLASS
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				break; 
			case 5: //PERIOD 4 CLASS
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
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
				System.out.print(cell.getNumericCellValue() + "\t\t\t");
				current.setID((int)cell.getNumericCellValue());
				break;  
			case 1: //LASTNAME
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				current.setLastName(cell.getStringCellValue());
				break; 
			case 2: //FIRSTNAME
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				current.setFirstName(cell.getStringCellValue());
				break; 
			case 3: //CLASS
				System.out.print(cell.getStringCellValue() + "\t\t\t");
				
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
		
		switch (cellNum)	//Identify the info held in the current Column, then switch to the next one for next iteration          
		{  
			case 0: //STUDENTNO 
				System.out.print(cell.getNumericCellValue() + "\t\t\t");		
				current.setID((int)cell.getNumericCellValue());
				break;  
			case 1: //LASTNAME
				System.out.print(cell.getStringCellValue() + "\t\t\t");
				current.setLastName(cell.getStringCellValue());
				break; 
			case 2: //FIRSTNAME
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				current.setFirstName(cell.getStringCellValue());
				break; 
			case 3: //GRADE
				System.out.print(cell.getNumericCellValue() + "\t\t\t"); 
				current.setGrade((int)cell.getNumericCellValue());
				break; 
			case 4: //PERIOD 1 CLASS
				System.out.print(cell.getStringCellValue() + "\t\t\t"); 
				String subject = cell.getStringCellValue();
				if(subjectList.get(subject) == null) //If it doesnt exist in the list yet
				{
					subjectList.put(subject, new Subject(subject)); // then add it!
					//period.add(subjectList.get(subject));
				}
				
				subjectList.get(subject).getStudentList().add(current); //add the current student to the subject in this period.
				break; 
			case 5: //PERIOD 2 CLASS
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				break; 
			case 6: //PERIOD 3 CLASS
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				break; 
			case 7: //PERIOD 4 CLASS
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				break; 
			case 8: //HEALTH CONDITION
				System.out.print(cell.getStringCellValue() + "\t\t\t");  
				current.setHealthConditions(cell.getStringCellValue());
				break; 
			case 9: //EXTRACURRICULAR
				System.out.print(cell.getStringCellValue() + "\t\t\t");
				break; 
			default:  
		}  
	}

}
