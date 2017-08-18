package com.demoaut.newtours.MyFramework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class ReadKeywordSheet {
	static Properties properties;
	static WebDriver driver;

	@BeforeMethod
	public void beforeTest() {
		
		try {
			ReadObjectMap map= new ReadObjectMap();
			properties=map.getObjectMap("C:\\Users\\Atishkadu\\workspace\\MyFramework\\ObjectMap.properties");				
				System.setProperty("webdriver.chrome.driver", properties.getProperty("chromeDriver"));
//				driver=new ChromeDriver();
			}catch(Exception e){
				System.err.println("Error");
			}
	}
	
	@Test
	public void Test(){
		
		
	}
	
	@DataProvider
	public Object[][] getExcelData(){
		try{
			ReadObjectMap map= new ReadObjectMap();
			properties=map.getObjectMap("C:\\Users\\Atishkadu\\workspace\\MyFramework\\ObjectMap.properties");
		FileInputStream file=new FileInputStream(properties.getProperty("KeywordSheet"));
		
		
		XSSFWorkbook workbook= new XSSFWorkbook(file);
		XSSFSheet sheet=workbook.getSheetAt(0);
		int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
		int cellCount=sheet.getRow(0).getPhysicalNumberOfCells();
		Object[][] obj=new Object[rowCount][cellCount-1];
		System.out.println("Row Count-> "+rowCount+" Column Count->"+cellCount);		
			String Flag, TestCaseName, Description, Keyword, Object, text;
			for (int i = 1; i <= rowCount; i++) {
				Row row=sheet.getRow(i);
				for(int j=0; j<cellCount-1;j++){
				try {
//				Row row=sheet.getRow(i);
					
				obj[i][j]=row.getCell(j).toString();
//				Flag=row.getCell(0).toString();
//				TestCaseName=row.getCell(1).toString();
//				Description=row.getCell(2).toString();
//				Keyword=row.getCell(3).toString();
//				Object=row.getCell(4).toString();
//				text=row.getCell(5).toString();
//					System.out.println(Flag+" || "+TestCaseName+" || "+Description+" || "+Keyword+" || "+Object+" || "+text);
					
				System.out.println(obj[i][j]);
					map.performOperations(row.getCell(3).toString(), row.getCell(4).toString(), row.getCell(5).toString());
					row.createCell(6).setCellValue("Pass");
					Thread.sleep(500);	
				}
				catch (NullPointerException e) {
					System.out.println("Values in cell are null ");
				}
				catch (Exception e) {}
				FileOutputStream fos = new FileOutputStream(properties.getProperty("KeywordSheet"));
				workbook.write(fos);
				fos.close();
				}
			}
		} catch (IOException e) {
			System.out.println("Keyword file not found at "+properties.getProperty("KeywordSheet"));
		}
		
	}
	
	@AfterMethod
	public static void closeSession(){

	} 

	}
