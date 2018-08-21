package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateWorkspace820 { 
	
	WebDriver driver;
	
		private static Date date = new Date();
		
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
		
		@Test
		// navigate to Daptiv US and log in 
		public void login() {
			driver.navigate().to("https://login.daptiv.com");
			driver.findElement(By.cssSelector("#email")).sendKeys("jsample@email.com");
			driver.findElement(By.cssSelector("#password")).sendKeys("########");
			driver.findElement(By.cssSelector("#login-btn")).click();
		}
		
		@Test(dependsOnMethods="login")
		//create workspace using Projects tab Create Workspace
		public void createWorkspaceDirect() {
			driver.findElement(By.linkText("Projects")).click();
			driver.findElement(By.linkText("Create Workspace")).click();
			driver.findElement(By.cssSelector("#wiz__workspaceName")).sendKeys("Test Workspace " + date);
			driver.findElement(By.cssSelector("#wiz_btnFinish1")).click();
		}
		
		@Test(dependsOnMethods="createWorkspaceDirect")
		//create template
		public void createTemplate() {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//div[@class='icon settings js-settings-icon']")).click();
			driver.findElement(By.linkText("Generate Template")).click();
			driver.switchTo().window("IB_BASIC");
			driver.findElement(By.xpath("//input[@class='itembrowser-input-text js-title']")).sendKeys(date + " Template");
			driver.findElement(By.name("ivButtonBar$ctl00")).click();
		}
		
		@Test(dependsOnMethods="createTemplate") 
		//verify template creation success message is displayed
			public void createTemplateSuccessMessage() {
			driver.switchTo().window("");
			String pageText = driver.findElement(By.tagName("body")).getText();
			Assert.assertTrue(pageText.contains("New Template Saved"));
		}
		
		@Test(dependsOnMethods="createTemplate")
		//create workspace from template, NOT DONE
		public void createWorkspaceFromTemplate() {
			driver.switchTo().window("");
			driver.findElement(By.linkText("Templates")).click();
			driver.findElement(By.linkText(date + " Template")).click();
			driver.findElement(By.cssSelector("#wiz__workspaceName")).sendKeys(date + " Workspace from Template");
			driver.findElement(By.cssSelector("#wiz_btnFinish1")).click();
		}
		
		@Test(dependsOnMethods="login")
		//create workspace from admin zone, NOT DONE
		public void createWorkspaceinAdminZone() {
			driver.findElement(By.xpath("//span[@class='icon user']")).click();
			driver.findElement(By.linkText("Admin")).click();
			switchToNewTab();
			driver.findElement(By.xpath("//div[@class='nav-button nav-button-projects']")).click();
			driver.findElement(By.linkText("Create Workspace")).click();
			driver.findElement(By.cssSelector("#wiz$_workspaceName")).sendKeys(date + " Workspace from Admin Zone");
			driver.findElement(By.cssSelector("#wiz_btnFinish1")).click();
		}
		
		@AfterClass
		public void closeApp() {
		//driver.quit();
	}

}