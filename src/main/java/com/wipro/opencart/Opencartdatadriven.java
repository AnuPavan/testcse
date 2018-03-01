package com.wipro.opencart;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

public class Opencartdatadriven 
	{
		Properties pro;
		WebDriver driver;
		//String url;
		//RegisterPage RegisterPage;
		//GenericMethods GenericMethods;
		
		@BeforeClass
		public void launchBrowser() throws IOException
			{
				 
			    //Step1: open the browser and Opencart
				System.setProperty("webdriver.chrome.driver","D://Selenium Drivers//chromedriver.exe");
				driver = new ChromeDriver();
				
				/*url = "http://10.159.34.70:4444/wd/hub";
		        try {
		            DesiredCapabilities capabilities = new DesiredCapabilities().chrome();
		            capabilities.setBrowserName("chrome");
		            capabilities.setPlatform(Platform.WINDOWS);
		            driver = new RemoteWebDriver(new URL(url), capabilities);
		        */	
		            driver.get("http://10.207.182.108:81/opencart/");
					driver.manage().window().maximize();
					driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
				
		        /*}
		        catch(Exception e){
		            e.printStackTrace();
		        }
		        */
				
				Assert.assertEquals("Your Store", driver.getTitle());
				System.out.println("Launched OpenCart");
			
				
				
				File file = new File(System.getProperty("user.dir")+"/src/main/java/com/wipro/Testdata/myProp.properties");
					  FileInputStream fis = new FileInputStream(file);
					  pro = new Properties();
					  pro.load(fis); 
			 }
		
			//Step3: Registration through DataProvider
			@Test(dataProvider="proauthen",priority=1)
			void ExcelPrinting(String FirstName, String Lastname, String Email, String Telephone, String Address1, String City, String PostCode,String Password,String Pwdconfirm) throws InterruptedException
			{
				//logger = extent.startTest("ExcelPrinting");
				/*RegisterPage =new RegisterPage(driver);
				
		
				
				GenericMethods=new GenericMethods();
				RegisterPage.CreateAcct.click();
				RegisterPage.fnameR.sendKeys(FirstName);
				*/
				String Emailadd=System.nanoTime()+Email;
				System.out.println("The changed email is " + Emailadd);
				driver.findElement(By.xpath(pro.getProperty("Register.Createacct.xpath"))).click();
				driver.findElement(By.name(pro.getProperty("Register.Firstname.name"))).sendKeys(FirstName);
				
				//GenericMethods.mySendText(RegisterPage.fnameR, FirstName);
				
				driver.findElement(By.name(pro.getProperty("Register.Lastname.name"))).sendKeys(Lastname);
				driver.findElement(By.name(pro.getProperty("Register.Email.name"))).sendKeys(Emailadd);
				driver.findElement(By.name(pro.getProperty("Register.Telephone.name"))).sendKeys(Telephone);
				driver.findElement(By.name(pro.getProperty("Register.Address1.name"))).sendKeys(Address1);
				driver.findElement(By.name(pro.getProperty("Register.city.name"))).sendKeys(City);
				driver.findElement(By.name(pro.getProperty("Register.Postcode.name"))).sendKeys(PostCode);
				
			WebElement drop=driver.findElement(By.name(pro.getProperty("Register.Country.name")));
			Select E = new Select(drop);
	 		E.selectByVisibleText("India");

				/*GenericMethods.dropDownText(RegisterPage.countryidR,"India");
				GenericMethods.sleepW(1000);
				GenericMethods.dropDownIndex(RegisterPage.countryidR,0);
	 		*/
	 		WebElement drop1 = driver.findElement(By.name(pro.getProperty("Register.Region.name")));
	 		Select F = new Select(drop1);
	 		F.selectByVisibleText("Andhra Pradesh");
	 		
	 		  driver.findElement(By.name(pro.getProperty("Register.Password.name"))).sendKeys(Password);
	 		  driver.findElement(By.name(pro.getProperty("Register.PasswordConfirm.name"))).sendKeys(Pwdconfirm);
	 		  driver.findElement(By.name(pro.getProperty("Register.Checkbox.name"))).click();
	 		  driver.findElement(By.xpath(pro.getProperty("Register.Continue.xpath"))).click();
	 		  
	 		  
	 		 String e= driver.findElement(By.xpath(pro.getProperty("Register.AccountCreatedText.xpath"))).getText();
		 	  Assert.assertEquals(e,"Your Account Has Been Created!");
	 		 System.out.println(e);
				System.out.println("Account created");
				//logger.log(LogStatus.PASS, "Method \"ExcelPrinting\" is passed");
				
				driver.findElement(By.linkText(pro.getProperty("Logout.linkText"))).click();
				driver.findElement(By.xpath(pro.getProperty("Register.Createacct.xpath"))).click();

			}
			//DataProvider

			@DataProvider(name="proauthen")
			public static Object[][] DataPro() throws IOException
			{
				Object[][] obj = ExcelData1();
				return obj;
			}
			
			
			//Reading the excel for Registration
			public static Object[][] ExcelData1() throws IOException
			{
				// TODO Auto-generated method stub
				File fil = new File(System.getProperty("user.dir")+"/src/main/java/com/wipro/Testdata/TestExcel.xlsx");
				FileInputStream fis = new FileInputStream(fil);
				Workbook wb = new XSSFWorkbook(fis);
				Sheet sh = wb.getSheet("Sheet1");
				int rowcount = sh.getLastRowNum();
				Row fistrow = sh.getRow(0);
				int colcount = fistrow.getLastCellNum();
				Object[][] obj = new Object[rowcount][colcount];
				for(int i=1; i<=rowcount; i++)
				{
					Row row = sh.getRow(i);
					for(int j=0; j<row.getLastCellNum();j++)
					{
						Cell cell = row.getCell(j);
						System.out.print(cell+" ");
						obj[i-1][j]=cell.getStringCellValue();
					}
					System.out.print("\n");
				}
				return obj;
				
			}


}
