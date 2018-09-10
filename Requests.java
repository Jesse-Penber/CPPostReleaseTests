package test;

import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//Request and request type creation and deletion + message verification

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
		driver.findElement(By.cssSelector("#password")).sendKeys("Password1$");
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
	//fill out request type fields and create request type
	public void createRequestType() {
		driver.findElement(By.xpath("//input[@name='txtTypeName']")).sendKeys("!" + date + " Request Type");
		WebElement resourceTypeElement = driver.findElement(By.xpath("//select[@name='ddlProjectType']"));
		Select resourceTypeDropdown = new Select(resourceTypeElement);
		resourceTypeDropdown.selectByIndex(6);
		driver.findElement(By.name("btnSave")).click();
	}
	
	@Test(priority=3, dependsOnMethods="createRequestType")
	//create a request using new type
	public void createRequest() {
		driver.switchTo().window("");
		driver.findElement(By.linkText("Projects")).click();
		driver.findElement(By.linkText("Create Request")).click();
		driver.switchTo().window("IB_BASIC");
		driver.findElement(By.xpath("//input[@type='radio']")).click();
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		driver.findElement(By.className("itembrowser-input-text")).sendKeys("!" + date + " Request");
		driver.findElement(By.name("ivButtonBar$btnFinish")).click();
	}
	
	@Test(priority=4, dependsOnMethods="createRequest")
	// check that "Request creation successful" message is displayed
	public void verifyRequestCreationMessage() {
		driver.switchTo().window("");
		Boolean messageIsDisplayed = driver.findElements(By.id("MessageBox_Positive_msg")).size() > 0;
		Assert.assertTrue(messageIsDisplayed);
	}
	
	@Test(priority=5, dependsOnMethods="createRequest")
	//create request
	public void deleteRequestFromProfileDetails() {
		driver.findElement(By.linkText("Requests")).click();
		List<WebElement> checkboxList = driver.findElements(By.xpath("//input[@type='checkbox']"));
		checkboxList.get(1).click();
		driver.findElement(By.xpath("//*[text()='Checked Item Actions']")).click();
		driver.findElement(By.xpath("//*[text()='Delete Request']")).click();
		driver.findElement(By.id("NeutralYesButton")).click();
	}

	@Test(priority=6, dependsOnMethods="deleteRequestFromProfileDetails") 
	//check that "Request was deleted successfully" message displayed
	public void verifyRequestDeletionMessage() {
		driver.switchTo().window("");
		Boolean messageIsDisplayed = driver.findElements(By.id("MessageBox_Positive_msg")).size() > 0;
		Assert.assertTrue(messageIsDisplayed);
	}
	
	@Test(priority=7, dependsOnMethods="createRequestType")
	//delete request type in Admin zone
	public void deleteRequestType() {
		driver.switchTo().window("");
		driver.findElement(By.xpath("//span[@class='icon user']")).click();
		driver.findElement(By.linkText("Admin")).click();
		driver.switchTo().window("Admin");
		driver.findElement(By.linkText("Request Types List")).click();
		List<WebElement> checkboxList2 = driver.findElements(By.xpath("//input[@type='checkbox']"));
		checkboxList2.get(1).click();
		driver.findElement(By.xpath("//*[text()='Checked Item Actions']")).click();
		//there are two "Delete Request Type" text elements, so made a list to select the second one
		List<WebElement> deleteButtonList = driver.findElements(By.xpath("//*[text()='Delete Request Type']"));
		deleteButtonList.get(1).click();
		driver.findElement(By.id("NeutralYesButton")).click();
	}
		
	@Test(priority=8, dependsOnMethods="deleteRequestType")	
	//checks that Request Type Deleted message displayed
	public void verifyRequestTypeDeletionMessage() {
		Boolean message2IsDisplayed = driver.findElements(By.id("MessageBox_Positive_msg")).size() > 0;
		Assert.assertTrue(message2IsDisplayed);
	}
	
	@AfterClass
	public void closeOut() {
	driver.quit();
	}
}
