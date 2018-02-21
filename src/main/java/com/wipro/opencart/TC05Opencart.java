package com.wipro.opencart;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC05Opencart 
{

	Properties pro;
	WebDriver driver;
	String url;
	@Test
	public void login() throws IOException, InterruptedException
	{
	    
		/*System.setProperty("webdriver.chrome.driver","D://Selenium Drivers//chromedriver.exe");
		driver = new ChromeDriver();
		*/ 

		url = "http://10.159.34.70:4444/wd/hub";
	        try {
	            DesiredCapabilities capabilities = new DesiredCapabilities();
	            capabilities.setBrowserName("chrome");
	            capabilities.setPlatform(Platform.WINDOWS);
	            driver = new RemoteWebDriver(new URL(url), capabilities);
	        	 }
	        catch(Exception e){
	            e.printStackTrace();
	        }

	        driver.get("http://10.207.182.108:81/opencart/");
			driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			Thread.sleep(1000);
			 driver.findElement(By.linkText(pro.getProperty("Login.LinkText"))).click();
		 		//Step 3:Login
		 		driver.findElement(By.name(pro.getProperty("Login.Username.name"))).sendKeys("Demo25.User@gmail.com");
		 		driver.findElement(By.name(pro.getProperty("Login.Password.name"))).sendKeys("Abc@123456");
		 		driver.findElement(By.xpath(pro.getProperty("Login.Loginbutton.xpath"))).click();
		 		Thread.sleep(3000);

		 		//validation
		 		//String Loginvalue= driver.findElement(By.xpath(pro.getProperty("Login.User.xpath"))).getText();
		 		String Loginvalue= driver.findElement(By.xpath(pro.getProperty("Login.xpath"))).getText();
		 		Assert.assertEquals(Loginvalue,"Demo25");
		 		System.out.println("Logged in with the User Demo25");
		 		driver.findElement(By.xpath(pro.getProperty("Home.xpath"))).click();
		        Reporter.log("Login successful");
		       driver.findElement(By.xpath(pro.getProperty("TC05.Extra.GiftVouchers.xpath"))).click();
		       
		   }
}
