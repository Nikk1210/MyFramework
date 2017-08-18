package com.demoaut.newtours.MyFramework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class ReadObjectMap 
{
	static WebDriver driver;
	static Properties properties;
	static boolean stepResult;
	public Properties getObjectMap(String filePath)
	{
		try 
		{
			properties=new Properties();
			FileInputStream file=new FileInputStream(filePath);
			properties.load(file);
			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
		return properties;
	}
	
	public By getLocators(String elementName) throws Exception {
		String locator=properties.getProperty(elementName);
		String locatorType=locator.split(":")[0];
		String locatorValue=locator.split(":")[1];
//		System.out.println("Locator Type-> "+locatorType+" Locator Value-> "+locatorValue);
	
			if(locatorType.toLowerCase().equals("name")){
				return By.name(locatorValue);
			}
			else if(locatorType.toLowerCase().equals("id")){
				return By.id(locatorValue);
			}
			else if(locatorType.toLowerCase().equals("xpath")){
				return By.xpath(locatorValue);
			}
			else if(locatorType.toLowerCase().equals("cssselector")){
				return By.cssSelector(locatorValue);
			}
			else if(locatorType.toLowerCase().equals("classname")){
				return By.className(locatorValue);
			}
			else if(locatorType.toLowerCase().equals("tagname")){
				return By.tagName(locatorValue);
			}
			else if(locatorType.toLowerCase().equals("partiallinktext")){
				return By.partialLinkText(locatorValue);
			}
			else if(locatorType.toLowerCase().equals("linktext")){
				return By.linkText(locatorValue);
			}
			else{
				throw new Exception("Given locator-> "+locatorType+" inside object map is invalid");
			}
	}
	
	public void performOperations(String operation, String object, String textToEnter) throws Exception {
		stepResult=true;
		switch(operation.toLowerCase()){

		//Open a given browser 
				case "openbrowser":
					if (textToEnter.equalsIgnoreCase("chrome")) {
						System.setProperty("webdriver.chrome.driver", properties.getProperty("chromeDriver"));
						driver=new ChromeDriver();
						
					}
					else if (textToEnter.equalsIgnoreCase("firefox")) {
						System.setProperty("webdriver.gecko.driver", properties.getProperty("geckoDriver"));
						driver=new FirefoxDriver();
					}
					else if (textToEnter.equalsIgnoreCase("ie")) {
						System.setProperty("webdriver.ie.driver", properties.getProperty("ieDriver"));
						driver=new InternetExplorerDriver();
					}
					else{
						stepResult=false;
					}
					//return stepResult;
				break;

		//perform navigation to given url
				case "navigate":
					driver.get(properties.getProperty(textToEnter));
					//return stepResult;
				break;
				
		//perform click operation on given element
		case "click":
			driver.findElement(this.getLocators(object)).click();
			//return stepResult;
		break;
		
		//Enter some text in given element
		case "entertext":
			driver.findElement(this.getLocators(object)).sendKeys(textToEnter);
			//return stepResult;
		break;
		
		//Take snapshot of particular page
		case "takesnapshot":
			TakesScreenshot ts = (TakesScreenshot)driver;
			File srcFile=ts.getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(srcFile, new File("C://Selenium//snaps//snapshot.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//return stepResult;
		break;
		
		//close a browser
		case "closebrowser":
			driver.close();
			//return stepResult;
		break;
		
		}
		
	}
	
	public static void main(String[] args) throws Exception{
//		ReadObjectMap map= new ReadObjectMap();
//		map.getObjectMap("C:\\Users\\Atishkadu\\workspace\\MyFramework\\ObjectMap.properties");
//		map.getLocators("txtUsername");
//		String url=properties.getProperty("url");
//		System.out.println(url);
//		
//		System.setProperty("webdriver.chrome.driver", properties.getProperty("chromeDriver"));
//		driver=new ChromeDriver();
//		driver.get(url);
//		map.performOperations(driver, "EnterText", "txtUsername", "tutorial");
//		map.performOperations(driver, "EnterText", "txtPassword", "tutorial");
//		map.performOperations(driver, "Click", "btnLogin", "");
//		map.performOperations(driver, "takesnapshot", "", "");
//		driver.close();
	}
	

}
