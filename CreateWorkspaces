package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WorkspaceCreationTests { 
	
	WebDriver driver;
	
		public static Date date = new Date();
		
		public void switchToNewTab() {
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
		}
		
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
		//create workspace using Projects tab Create Workspace
		public void createWorkspaceDirect() {
			driver.findElement(By.linkText("Projects")).click();
			driver.findElement(By.linkText("Create Workspace")).click();
			driver.findElement(By.cssSelector("#wiz__workspaceName")).sendKeys("Workspace Direct " + date);
			driver.findElement(By.cssSelector("#wiz_btnFinish1")).click();
		}
		
		@Test(priority=2, dependsOnMethods="createWorkspaceDirect")
		//
		public void verifyWorkspaceCreationDirect() {
			String pageText = driver.findElement(By.tagName("body")).getText();
			Assert.assertTrue(pageText.contains("Workspace Direct " + date));
		}
		
		@Test(priority=3, dependsOnMethods="login")
		//create template
		public void createTemplate() {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//div[@class='icon settings js-settings-icon']")).click();
			driver.findElement(By.linkText("Generate Template")).click();
			driver.switchTo().window("IB_BASIC");
			driver.findElement(By.xpath("//input[@class='itembrowser-input-text js-title']")).sendKeys("Template " + date);
			driver.findElement(By.name("ivButtonBar$ctl00")).click();
		}
		
		@Test(priority=4, dependsOnMethods="createTemplate") 
		//verify template creation success message is displayed
			public void createTemplateSuccessMessage() {
			driver.switchTo().window("");
			String pageText = driver.findElement(By.tagName("body")).getText();
			Assert.assertTrue(pageText.contains("New Template Saved"));
		}
		
		@Test(priority=5, dependsOnMethods="createTemplate")
		//create workspace from template
		public void createWorkspaceFromTemplate() {
			driver.switchTo().window("");
			driver.findElement(By.linkText("Templates")).click();
			driver.findElement(By.xpath("//img[@border='0']")).click();
			driver.findElement(By.linkText("Create Workspace from Template")).click();
			driver.findElement(By.xpath("//input[@name='wiz$_workspaceName']")).sendKeys("Workspace from Template " + date);
			driver.findElement(By.xpath("//input[@type='submit']")).click();
		}
		
		@Test(priority=6, dependsOnMethods="createWorkspaceFromTemplate")
		//verify creation of workspace from template
		public void  verifyWorkspaceCreationTemplate() {
			String pageText = driver.findElement(By.tagName("body")).getText();
			Assert.assertTrue(pageText.contains("Workspace from Template " + date));
		}
		
		@Test(priority=7, dependsOnMethods="login")
		//create workspace from admin zone
		public void createWorkspaceinAdminZone() {
			driver.findElement(By.xpath("//span[@class='icon user']")).click();
			driver.findElement(By.linkText("Admin")).click();
			switchToNewTab();
			driver.findElement(By.xpath("//div[@class='nav-button nav-button-projects']")).click();
			driver.findElement(By.linkText("Create Workspace")).click();
			driver.findElement(By.name("wiz$_workspaceName")).sendKeys("Workspace from Admin Zone " + date);
			driver.findElement(By.name("wiz$btnFinish1")).click();
		}
		
		@Test(priority=8, dependsOnMethods="createWorkspaceinAdminZone")
		//verify creation of workspace in admin zone
		public void verifyWorkspaceCreationAdminZone() {
			driver.findElement(By.xpath("//div[@class='nav-hotbutton nav-hotbutton-projects']")).click();
			WebElement workspaceCreatedName = driver.findElement(By.linkText("Workspace from Admin Zone " + date));
			Assert.assertEquals(true, workspaceCreatedName.isDisplayed());
		}
		
		@AfterClass
		public void closeApp() {
		driver.quit();
	}

}
