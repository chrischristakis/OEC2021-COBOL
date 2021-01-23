package oec.cobol;

import java.io.File;  
import java.io.FileInputStream;  
import java.util.Iterator;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  


public class Main 
{
	
	public static void main(String[] args) 
	{
		try  
		{  
			File file = new File("OEC2021 - School Record Book.xlsx");   //creating a new file instance  
			FileInputStream fis = new FileInputStream(file);   //obtaining bytes from the file  
			//This workbook refers to the entire XSLX file, and each sheet is contained in this workbook.
			XSSFWorkbook wb = new XSSFWorkbook(fis);   
			XSSFSheet sheet = wb.getSheetAt(0);     //Start at sheet 0 (Student records)
			
			Iterator<Row> itr = sheet.iterator();
			while (itr.hasNext()) //Iterate each row
			{  
				Row row = itr.next();
				
				Iterator<Cell> cellIterator = row.cellIterator();   //iterating over each column  
				while (cellIterator.hasNext())   
				{  
					Cell cell = cellIterator.next();  
					switch (cell.getCellTypeEnum())               
					{  
						case STRING:    //field that represents string cell type  
							System.out.print(cell.getStringCellValue() + "\t\t\t");  
							break;  
						case NUMERIC:    //field that represents number cell type  
							System.out.print(cell.getNumericCellValue() + "\t\t\t");  
							break;  
						default:  
					} 
				
				}
				System.out.println();
			}  
			wb.close();
		}  
		catch(Exception e)  
		{  
			e.printStackTrace();  
		}  
	}

}
