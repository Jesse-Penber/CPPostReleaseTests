package test;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//User tests to add user in Admin zone, edit user details, and remove user

public class AddEditDeleteUser {
	
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
	// add a licensed user in the admin zone
	public void addUserAdminZone() {
		driver.findElement(By.xpath("//span[@class='icon user']")).click();
		driver.findElement(By.linkText("Admin")).click();
		driver.switchTo().window("Admin");
		driver.findElement(By.xpath("//div[@class='nav-button nav-button-users']")).click();
		driver.findElement(By.linkText("Create User")).click();
		driver.findElement(By.xpath("//input[@name='tbFirstName']")).sendKeys(dateShort + " Test");
		driver.findElement(By.xpath("//input[@name='tbLastName']")).sendKeys("User");
		driver.findElement(By.xpath("//input[@name='tbEmail']")).sendKeys(dateShort + "@email.com");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
	}
	
	@Test(priority=2, dependsOnMethods="addUserAdminZone")
	// checks that new user is present in user list
	public void verifyUserAddedAdminZone() {
		driver.findElement(By.xpath("//span[@tabindex='-11']")).click();
		driver.findElement(By.linkText("Show all items")).click();
		String pageText = driver.findElement(By.tagName("body")).getText();
		Assert.assertTrue(pageText.contains(dateShort + "@email.com"));
	}
	
	@Test(priority=3, dependsOnMethods="addUserAdminZone")
	// navigates to user details page 
	public void navigateToUserDetails() {
		driver.findElement(By.linkText(dateShort + " Test User")).click();
		driver.findElement(By.linkText("Edit User Details")).click();
	}
	
	@Test(priority=4, dependsOnMethods="navigateToUserDetails")
	// changes user's last name
	public void editUserName() {
		driver.findElement(By.xpath("//input[@name='tbLastName']")).clear();
		driver.findElement(By.xpath("//input[@name='tbLastName']")).sendKeys("User Updated");
	}
	
	@Test(priority=5, dependsOnMethods="navigateToUserDetails")
	// change user's language
	public void editUserLanguage() {
		WebElement picklistLanguageElement = driver.findElement(By.xpath("//select[@name='ddlLanguage']"));
		Select picklistLanguageDropdown = new Select(picklistLanguageElement);
		picklistLanguageDropdown.selectByIndex(3);
	}	
	
	@Test(priority=6, dependsOnMethods="navigateToUserDetails")
	// change user's resource manager
	public void editUserRM() {
		WebElement picklistRMElement = driver.findElement(By.xpath("//select[@name='ddlResourceManager']"));
		Select picklistRMDropdown = new Select(picklistRMElement);
		picklistRMDropdown.selectByIndex(6);
	}
		
	@Test(priority=7, dependsOnMethods="navigateToUserDetails")
	// add a skill to user
	public void editUserSkills() {
		driver.findElement(By.xpath("//button[@onclick='getSkillSelector()']")).click();
		driver.switchTo().window("SelectSkills");
		driver.findElement(By.xpath("//img[@src='/WebResource.axd?d=R5KNte39B-iy7Svjmme6NjXLlDX55U6sZCyDTlvlL0Kxtidw0ykC4_jZBKxt0uTTmmzEGWeAgPoBwcAS04rnZwZ52IdG8TuaupmfWlaFnXGHX0Fk0&t=636475831203255666']")).click();
		driver.findElement(By.xpath("//img[@src='/WebResource.axd?d=R5KNte39B-iy7Svjmme6NjXLlDX55U6sZCyDTlvlL0Kxtidw0ykC4_jZBKxt0uTTmmzEGWeAgPoBwcAS04rnZwZ52IdG8TuaupmfWlaFnXGHX0Fk0&t=636475831203255666']")).click();
		driver.findElement(By.id("ctl01n2CheckBox")).click();		
		driver.findElement(By.xpath("//input[@type='submit']")).click();
	}
	
	@Test(priority=8, dependsOnMethods="navigateToUserDetails")
	// add an internal rate to user
	public void editUserRate() { 
		driver.switchTo().window("Admin");
		driver.findElement(By.xpath("//img[@id='_internalRate__showSelector']")).click();
		driver.switchTo().window("BILLING_RATE");
		driver.findElement(By.id("_ratesGrid_ctl02_sel_0")).click();
		driver.findElement(By.id("_saveAndClose")).click();
	}
	
	@Test(priority=9, dependsOnMethods="navigateToUserDetails")
	// change user's enterprise role
	public void editUserRole() {
		driver.switchTo().window("Admin");
		WebElement picklistRoleElement = driver.findElement(By.xpath("//select[@name='ddlEnterpriseRole']"));
		Select picklistRoleDropdown = new Select(picklistRoleElement);
		picklistRoleDropdown.selectByIndex(3);
	}
	
	@Test(priority=10, dependsOnMethods="navigateToUserDetails")
	// save edited user settings
	public void submitEditedUserSettings() {
		driver.findElement(By.id("Button1")).click();
	}
	
	@Test(priority=11, dependsOnMethods="navigateToUserDetails")
	// convert user to External Resource
	public void convertToER() {
		driver.findElement(By.linkText("Convert to External Resource")).click();
		driver.findElement(By.xpath("//button[@data='res:Text_Yes']")).click();
	}
	

	@Test(priority=12, dependsOnMethods="navigateToUserDetails") 
	// remove user
	public void removeUser() {
		driver.findElement(By.linkText("Remove from Enterprise")).click();
		driver.findElement(By.xpath("//button[@data='res:Text_Yes']")).click();
	}
	
}
