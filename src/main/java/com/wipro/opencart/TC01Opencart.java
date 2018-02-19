package com.wipro.opencart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.wipro.opencart.GenericMethods;
import org.openqa.selenium.remote.RemoteWebDriver;
public class TC01Opencart extends Extentreports
{
	Properties pro;
	WebDriver driver;
	String url;
	//RegisterPage RegisterPage;
	//GenericMethods GenericMethods;
	
	@BeforeClass
	public void launchBrowser() throws IOException
		{
			 
		    //Step1: open the browser and Opencart
			/*System.setProperty("webdriver.chrome.driver","D://Selenium Drivers//chromedriver.exe");
			driver = new ChromeDriver();*/
			
			url = "http://10.159.34.70:4444/wd/hub";
	        try {
	            DesiredCapabilities capabilities = new DesiredCapabilities();
	            capabilities.setBrowserName("chrome");
	            capabilities.setPlatform(Platform.WINDOWS);
	            driver = new RemoteWebDriver(new URL(url), capabilities);
	        	
	            driver.get("http://10.207.182.108:81/opencart/");
				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			
	        }
	        catch(Exception e){
	            e.printStackTrace();
	        }
	        
			Assert.assertEquals("Your Store", driver.getTitle());
			System.out.println("Launched OpenCart");
		
			
			
			File file = new File("C://Users//an247684//workspace//TopGear1//ObjectRepository.properties");
				  FileInputStream fis = new FileInputStream(file);
				  pro = new Properties();
				  pro.load(fis); 
		 }
	
		//Step3: Registration through DataProvider
		@Test(dataProvider="proauthen",priority=1)
		void ExcelPrinting(String FirstName, String Lastname, String Email, String Telephone, String Address1, String City, String PostCode,String Password,String Pwdconfirm) throws InterruptedException
		{
			logger = extent.startTest("ExcelPrinting");
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
	 	  //Assert.assertEquals(e,"Your Account Has Been Created!");
 		 System.out.println(e);
			System.out.println("Account created");
			logger.log(LogStatus.PASS, "Method \"ExcelPrinting\" is passed");
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
			File fil = new File("D:\\TestExcel.xlsx");
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

		@Test(priority=2)
		public void SamsungTab()
		{
			logger = extent.startTest("SamsungTab");
			/* 
			  RP = new RegistrationPage(driver); 
			  RP.fnameR.sendKeys(fname); 
			   RP.Register.click();	 
			      RegistrationPage RP; */
			   

			driver.findElement(By.xpath(pro.getProperty("Home.xpath"))).click();
			System.out.println("Home page");
		    driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.xpath"))).click();
			System.out.println("Samsung galaxy tab");
			driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Review.xpath"))).click();
			logger.log(LogStatus.PASS, "Method \"SamsungTab\" is passed");
		}
		
			
			//Samsung Galaxy tab, Review
		
			@Test(dataProvider="Review",priority=3)
			public void ReviewPrinting(String Name,String Review,String Rating) throws InterruptedException
			{
				logger = extent.startTest("ReviewPrinting");
			int Rate = Integer.parseInt(Rating);
			
			
			driver.findElement(By.name(pro.getProperty("SamsungGalaxytab.Review.Yourname.name"))).clear();
	 		driver.findElement(By.name(pro.getProperty("SamsungGalaxytab.Review.Yourname.name"))).sendKeys(Name);
	 		driver.findElement(By.name(pro.getProperty("SamsungGalaxytab.Review.YourReview.name"))).clear();
	 		driver.findElement(By.name(pro.getProperty("SamsungGalaxytab.Review.YourReview.name"))).sendKeys(Review);
	 		
	
			if(Rate==1)
	 		{
	 		driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Review.Ratingbutton1.xpath"))).click();
			}
			else if(Rate==2)
	 		{
	 		driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Review.Ratingbutton2.xpath"))).click();
			}
			else if(Rate==3)
	 		{
	 		driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Review.Ratingbutton3.xpath"))).click();
			}
			else if(Rate==4)
	 		{
	 		driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Review.Ratingbutton4.xpath"))).click();
			}
			else if(Rate==5)
	 		{
	 		driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Review.Ratingbutton5.xpath"))).click();
			}
			Thread.sleep(10000);
			
			driver.findElement(By.id(pro.getProperty("SamsungGalaxytab.Review.Continuebutton.id"))).click();
			if(Review.length()<25)
	 		{
	 			Thread.sleep(3000);
	 			String warmess= driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Review.Warning.xpath"))).getText();
	 		    Assert.assertTrue(warmess.contains("Warning"),"Warning Message not Displayed");
	 		}
	 		else
	 		{
	 			Thread.sleep(3000);
	 			String Success= driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Review.Warning.xpath"))).getText();
	 	        Assert.assertTrue(Success.contains("Thank you"),"Success Message not Displayed");
	 		}
	 		
	 		Thread.sleep(3000);
	 		
	 		System.out.println("Review done");
	 		logger.log(LogStatus.PASS, "Method \"ReviewPrinting\" is passed");
			}
			
			//Dataprovider for Review
			@DataProvider(name="Review")
			public static Object[][] ReviewDataPro() throws IOException
			{
				Object[][] obj = ExcelData();
				return obj;
			}
			
			
			//Reading Review from excel
			public static Object[][] ExcelData() throws IOException
			{
				// TODO Auto-generated method stub
				File fil = new File("D:\\Review.xlsx");
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

			
			
		@Test(priority=4)
		public void AddToCart() throws InterruptedException, IOException
		{
			logger = extent.startTest("AddToCart");
			
	 		
	 	   //Add to Wishlist
	 		driver.findElement(By.partialLinkText(pro.getProperty("SamsunGalaxytab.Wishlist.partialLinkText"))).click();
	 	     System.out.println("Added to WishList");
	 	     Thread.sleep(2000);
	 	    driver.findElement(By.xpath(pro.getProperty("SamsunGalaxytab.Wishlist.close.xpath"))).click();
	 	   System.out.println("close the success message of wishlist");
	 	   //Go to Wishlist
	 	  driver.findElement(By.partialLinkText(pro.getProperty("SamsungGalaxytab.Wishlist.partiaLinkText"))).click();
	 		System.out.println("clicked on Wish list link");	
	 		Thread.sleep(3000);
	 		
	 		//Change the currency and writing to flat file
	 		driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Currency.Euro.xpath"))).click();
	 		System.out.println("Clicked on Euro");
	 		Thread.sleep(3000);
	 		BufferedWriter bw= new BufferedWriter(new FileWriter("D:\\Filename.txt"));
	 		Thread.sleep(3000);
	 		String Euro= driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.UnitPrice.Euro.xpath"))).getText();
	 		System.out.println(Euro);
	 		bw.write(Euro);
	 
	 		
	 		driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.Currency.dollar.xpath"))).click();
	 		String Dollar = driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.UnitPrice.Dollar.xpath"))).getText();
	 		System.out.println(Dollar);
	 				bw.write(Dollar);
	 				bw.close();
	 				
	 				
	 		//Add to cart
	 				  driver.findElement(By.xpath(pro.getProperty("MyWishlist.Addtocart.xpath"))).click();
	 		           System.out.println("Added to cart");
	 		           Thread.sleep(2000);
	 		 	 
	 		 //close the success message of cart button
	 		 	
	 		 	    driver.findElement(By.xpath(pro.getProperty("MyWishlist.Closecartmsg.xpath"))).click();
	 		 	 	System.out.println("close the success message of cart");
	 		 			 		 		
	 		 	//My wish list remove icon
	 		 		//driver.findElement(By.partialLinkText(pro.getProperty("MyWishlist.Remove.partialLinkText"))).click();
	 		 		//Thread.sleep(1000);
	 		 		driver.findElement(By.xpath(pro.getProperty("MyWishlist.Remove.xpath"))).click();
	 		 		System.out.println("Removed icon in wishlist");
	 		 		Thread.sleep(3000);
	 		 		
	 		 		
	 		 	driver.findElement(By.linkText(pro.getProperty("MyWishlist.Continue.linkText"))).click();
	 		 	logger.log(LogStatus.PASS, "Method \"AddToCart\" is passed");
	 		 		}
		
 		
		
	@AfterClass
	public void Logout() throws InterruptedException
	{

		driver.findElement(By.linkText(pro.getProperty("Logout.linkText"))).click();
		Thread.sleep(3000);
	   
 	 
		String f= driver.findElement(By.xpath(pro.getProperty("Logout.xpath"))).getText();
	 	  Assert.assertEquals(f,"Account Logout");
			System.out.println("Logout Successful");
			
	}
	

}
