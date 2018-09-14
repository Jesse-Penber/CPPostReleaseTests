package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

//utility functions to change user cultural, time, and language settings to American and to EU
//realized that changing the language setting will break the other suites that rely on link text

public class UserSettings {
	
	WebDriver driver;
	
	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\jpenber\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Test(priority=0)
	public void login() {
		driver.navigate().to("https://login.daptiv.com");
		driver.findElement(By.cssSelector("#email")).sendKeys("jsample@email.com");
		driver.findElement(By.cssSelector("#password")).sendKeys("########");
		driver.findElement(By.cssSelector("#login-btn")).click();
	}
	
	@Test(priority=1)
	public void goToUserSettings() {
		driver.findElement(By.xpath("//*[@class='daptiv-dropdownmenu-button']/a/following::span")).click();
		driver.findElement(By.linkText("Edit User Settings")).click();
	}
	
	
	@Test(priority=2)
	public void changeTimeZoneCultureLanguagetoEU() {
		driver.findElement(By.id("timeZone")).click();
		driver.findElement(By.id("timeZone")).sendKeys("(GMT) Greenwich");
		driver.findElement(By.id("ddlLanguage")).click();
		driver.findElement(By.id("ddlLanguage")).sendKeys("fr");
		driver.findElement(By.id("culture")).click();
		driver.findElement(By.id("culture")).sendKeys("fr");
		driver.findElement(By.name("btnSave")).click();
	}
	
	
	@Test(priority=3)
	public void changeTimeZoneCultureLanguagetoUS() {
		driver.findElement(By.xpath("//*[@class='daptiv-dropdownmenu-button']/a/following::span")).click();
		driver.findElement(By.linkText("Modifier des paramètres utilisateur")).click();
		
		driver.findElement(By.id("timeZone")).click();
		driver.findElement(By.id("timeZone")).sendKeys("(GMT-08:00) Pacific");
		driver.findElement(By.id("ddlLanguage")).click();
		driver.findElement(By.id("ddlLanguage")).sendKeys("En");
		driver.findElement(By.id("culture")).click();
		driver.findElement(By.id("culture")).sendKeys("English (United States)");
		driver.findElement(By.name("btnSave")).click();
	}
	
	