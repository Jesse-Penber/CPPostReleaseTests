package test;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//8-22-2018
//Create request type, define approval policy, create request, impersonate user, and approve it
public class Requests {

	WebDriver driver;
	
	public static Date date = new Date();
	public static String dateShort = date.toString().replace(" ","").replace(":","");
	
	@BeforeClass
	// set driver properties to Chrome
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jpenber\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Test(priority=0)
	// navigate to Daptiv US and log in 
	public void login() {
		driver.navigate().to("https://login.daptiv.com");
		driver.findElement(By.cssSelector("#email")).sendKeys("jsample@email.com");
		driver.findElement(By.cssSelector("#password")).sendKeys("########");
		driver.findElement(By.cssSelector("#login-btn")).click();
	}
	
	@Test(priority=1, dependsOnMethods="login")
	//navigate to admin zone and begin request type set up 
	public void navigateToCreateRequestType() {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
		driver.findElement(By.xpath("//span[@class='icon user']")).click();
		driver.findElement(By.linkText("Admin")).click();
		driver.switchTo().window("Admin");
		driver.findElement(By.linkText("Create Request Type")).click();
	}
	
	@Test(priority=2, dependsOnMethods="navigateToCreateRequestType")
	//define and assign approval policy, NOT FINISHED
	public void defineApprovalPolicy() {
		driver.findElement(By.id("ctrlPolicyDetail_chkDefinePolicy")).click();
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.switchTo().window("GlobalUserSelector");
		
	}
	
	@Test(priority=3, dependsOnMethods="navigateToCreateRequestType")
	//fill out request type fields and create request type
	public void createRequestType() {
		driver.findElement(By.xpath("//input[@name='txtTypeName']")).sendKeys(date + " Request Type");
		WebElement resourceTypeElement = driver.findElement(By.xpath("//select[@name='ddlProjectType']"));
		Select resourceTypeDropdown = new Select(resourceTypeElement);
		resourceTypeDropdown.selectByIndex(6);
		driver.findElement(By.name("btnSave")).click();
	}
}