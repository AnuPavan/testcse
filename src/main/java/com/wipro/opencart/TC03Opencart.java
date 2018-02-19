package com.wipro.opencart;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

public class TC03Opencart extends Extentreports
{
	Properties pro;
	WebDriver driver;
	WebDriverWait wait;
	String url;
	//Step:1 Launch  Open Cart application http://10.207.182.108:81/opencart/
	@BeforeClass
	public void login() throws IOException, InterruptedException
	{
	
	/*System.setProperty("webdriver.chrome.driver","D://Selenium Drivers//chromedriver.exe");
	driver = new ChromeDriver();
	*///wait=new WebDriverWait(driver, 20);
		
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
        
		Thread.sleep(5000);
	
	 File file = new File("C://Users//an247684//workspace//TopGear1//ObjectRepository.properties");	
	  FileInputStream fis = new FileInputStream(file);
	  pro = new Properties();
	  pro.load(fis); 
	  

	//Step 2: Click on "Login" Link
	//Step3: Enter Email Address and Password and click on "Login" Button.
		driver.findElement(By.linkText(pro.getProperty("Login.LinkText"))).click();
		driver.findElement(By.name(pro.getProperty("Login.Username.name"))).sendKeys("Demo.User@gmail.com");
		driver.findElement(By.name(pro.getProperty("Login.Password.name"))).sendKeys("Abc@12345");
		driver.findElement(By.xpath(pro.getProperty("Login.Loginbutton.xpath"))).click();
		Thread.sleep(3000);
		
		//Check point:User's first name as a link
		String Loginvalue= driver.findElement(By.linkText(pro.getProperty("Login.User.LinkText"))).getText();
		Assert.assertEquals(Loginvalue,"Demo");
		System.out.println("Step 1,2,3:Logged in with the User Demo");
		driver.findElement(By.xpath(pro.getProperty("Home.xpath"))).click();
    Reporter.log("Login successful");
    
   
}

    @Test
      public void AddPhones() throws InterruptedException, IOException
      {
    	 logger = extent.startTest("AddPhones");
	 		//Step 4:Home page
    	  Thread.sleep(3000);
	 		driver.findElement(By.xpath(pro.getProperty("Home.xpath"))).click();
	 		System.out.println("Step 4:Home page");
	 		Thread.sleep(3000);

	        //Step 5:Samsung galaxy
	 		driver.findElement(By.xpath(pro.getProperty("SamsungGalaxytab.xpath"))).click();
	 		System.out.println("Step 5:Clicked on Samsung galaxy ");
	 		Thread.sleep(3000);
	 		
	 		//Step 6:Samsung galaxy image
	 		driver.findElement(By.xpath(pro.getProperty("TC03.SamsungGalaxytab.image.xpath"))).click();
	 		System.out.println("Step 6:Clicked on image");
	 		Thread.sleep(3000);
	 	
	 	
	 		//Validation
	 		String image=driver.findElement(By.xpath("//div[@id='cboxCurrent']")).getText();
		    String image1=image.substring(11);
	 		System.out.println(image1);
	 		int imageint=Integer.parseInt(image1);
	 		System.out.println(imageint);
	 	    BufferedWriter imageoutput=new BufferedWriter(new FileWriter("D://TC03_step6.txt"));
	 	    imageoutput.write("Step 6: validation:Image count is " +imageint);
	 	    imageoutput.close();
	 				     
	 			 
	 	   //Step 7:image count and validation
	 		for(int i=1;i<=imageint;i++)	
	 		{
	 			driver.findElement(By.xpath(pro.getProperty("TC03.SamsungGalaxytab.Next.xpath"))).click();
	 			int count = 0;
	 			count= count+i;
	 			System.out.println("Step 7:image  "+count);
	 		}
	 			
	 	   //Step 8:close
	 		driver.findElement(By.xpath(pro.getProperty("TC03.SamsungGalaxytab.close.xpath"))).click();
	 		System.out.println("Step 8:Closed");
	 		Thread.sleep(3000);
	 		
	 		
	 		//Step 9:Add to cart
	 		driver.findElement(By.xpath(pro.getProperty("TC02.1stPhone.AddTocart.xpath"))).click();
	 		System.out.println("Step 9:Added to cart");
	 		Thread.sleep(3000);
	 		
	 		//Step 10:click on Shopping cart
	 		driver.findElement(By.partialLinkText(pro.getProperty("Ribbon.shoppingcart.partiallinkText"))).click();
	        System.out.println("Step 10:Shopping cart link");
	        Thread.sleep(3000);
	        
	        //Step 11: Radio button estimate
	        driver.findElement(By.id(pro.getProperty("TC03.Shoppingcart.EstimateRadio.id"))).click();
	        System.out.println("Step 11:Estimate Shipping & Taxes");
	        Thread.sleep(3000);
	        
	      //Step 12:Get Quotes
	        driver.findElement(By.id(pro.getProperty("TC03.Shoppingcart.GetQuotes.id"))).click();
	        System.out.println("Step 12:Get Quotes");
	        Thread.sleep(3000);
	        
	        //Flat radio button
	        driver.findElement(By.xpath(pro.getProperty("TC03.Flatradio.xpath"))).click();
	        driver.findElement(By.id(pro.getProperty("TC03.Shipping.id"))).click();
	        System.out.println("Flat radio button");
	        Thread.sleep(3000);
	        
	        //validation
	        BufferedWriter bw=new BufferedWriter(new FileWriter("D://TC03_step12.txt"));
	        String Totalamt= driver.findElement(By.xpath(pro.getProperty("TC03.Shoppingcart.Total.xpath"))).getText();
	        bw.write("Step 12:  " +Totalamt);
	 	    bw.close();
	 		
	        
	        //Step 13:Coupon radio button
	        driver.findElement(By.id(pro.getProperty("TC03.Couponradio.id"))).click();
	        System.out.println("Step 13:Coupon radio button clicked");
	        Thread.sleep(3000);
	        
	        //Step 14: Apply coupon
	        driver.findElement(By.name(pro.getProperty("TC03.Coupon.name"))).sendKeys("Abc");
	        driver.findElement(By.xpath(pro.getProperty("TC03.Apply.Coupon"))).click();
	        System.out.println("Step 14:Coupon radio button");
	        Thread.sleep(3000);
	        
	      //validation
	        BufferedWriter bw1=new BufferedWriter(new FileWriter("D://TC03_step14.txt"));
	        String Warning= driver.findElement(By.xpath(pro.getProperty("TC03.Shoppingcart.warning"))).getText();
	        bw1.write("Step 14:  " +Warning);
	 	    bw1.close();
	        
	        
	        //Step 15:Checkout
	        driver.findElement(By.partialLinkText(pro.getProperty("Checkout.partiallinkText"))).click();
	        System.out.println("Step 15:Checkout successfull");
	        Thread.sleep(3000);
	        
	        
	        //Step 16:Billing address        
	        driver.findElement(By.id(pro.getProperty("TC03.Paymentaddressnew.id"))).click();
	        System.out.println("Step 16: New billing address");
	        Thread.sleep(3000);
	        
	        //Step 17:create new address
	        driver.findElement(By.name("firstname")).sendKeys("Demo11");
			driver.findElement(By.name("lastname")).sendKeys("User");
			driver.findElement(By.name("address_1")).sendKeys("Plot No:C-55,Kakatiya Nagar");
			driver.findElement(By.name("city")).sendKeys("Hyderabad");
			WebElement drop = driver.findElement(By.name("country_id"));
			Select E = new Select(drop);
	 		E.selectByVisibleText("India");
	 		WebElement drop1 = driver.findElement(By.name("zone_id"));
	 		Thread.sleep(3000);
	 		Select F = new Select(drop1);
	 		F.selectByVisibleText("Andhra Pradesh");
	 		
	 		 //Continue1
	   	   driver.findElement(By.id(pro.getProperty("Continue1button.id"))).click();
	   	   System.out.println("clicked on continue1");
	   	   Thread.sleep(3000);
	   	   System.out.println("Step 17:Filled the details of New Billing address");
	   	   
	   	   //Step 18:delivery address change
	   	   
	   	WebElement deliverydrop = driver.findElement(By.xpath(pro.getProperty("TC03.Delivery.List.xpath")));
		Select Deld = new Select(deliverydrop);
		Deld.selectByIndex(Deld.getOptions().size()-1);
	   	   
	   	 //driver.findElement(By.xpath(".//*[@id='shipping-existing']/select/option[8]")).click();
	   	   //System.out.println("address change");
	   	   //Thread.sleep(3000);
	   	  System.out.println("Step 18:Select new address");
	   	   
	   	 //Continue2
	  	   driver.findElement(By.id(pro.getProperty("Continue2button.id"))).click();
	  	   System.out.println("clicked on continue2");
	  	   Thread.sleep(3000);
	  	   
	  	   //Step 19: Add comments and continue (Continue3)
	  	   driver.findElement(By.xpath(pro.getProperty("TC03.Deliverymethod.Textarea.xpath"))).sendKeys("abc");
	  	   Thread.sleep(3000);
	  	   
	  	  //continue3
	   	  driver.findElement(By.id(pro.getProperty("Continue3button.id"))).click();
	   	   System.out.println("Step 19:clicked on continue3");
	   	   Thread.sleep(3000);
	   	   
	   	 //Step 20:Checkbox
	  	   driver.findElement(By.name(pro.getProperty("TermsandConditions.name"))).click();
	  	   System.out.println("Step 20:checkbox selected");
	  	   Thread.sleep(3000);
	  	   
	  	   //Terms and Conditions link
	  	   driver.findElement(By.xpath(pro.getProperty("TC03.TermsandConditionslink.xpath"))).click();
	  	   System.out.println("Step 20:Terms and Conditions link clicked");
	  	   
	  	   //validation
	  	    BufferedWriter bw2=new BufferedWriter(new FileWriter("D://TC03_step20.txt"));
	  	    Thread.sleep(5000);
	        String Termlen= driver.findElement(By.xpath(pro.getProperty("TC03.Terms&Conditions.xpath"))).getText();
	        System.out.println(Termlen);
	        Termlen.length();
	        bw2.write("Step 20:  " +Termlen.length());
	 	    bw2.close();
	        
	  	   //Step 21:close
	  	   driver.findElement(By.xpath(pro.getProperty("TC03.TermsandConditions.close.xpath"))).click();
	  	   System.out.println("Step 21:Close button");
	  	   Thread.sleep(3000);
	  	   
	  	     	   
	  	   //continue order
	  	   driver.findElement(By.id(pro.getProperty("Continuebutton.id"))).click();
	  	   System.out.println("Click on continue");
	  	   Thread.sleep(3000);
	  	   
	  	   //validation
	  	 String CheckoutPrice= driver.findElement(By.xpath(pro.getProperty("TC03.Checkout.Samsung.price.xpath"))).getText();
		 String CheckoutTotal= driver.findElement(By.xpath(pro.getProperty("TC03.Checkout.Samsung.total.xpath"))).getText();

		 
		    List<String> Checkout = new ArrayList<String>();
		    Checkout.add(CheckoutPrice);
		    Checkout.add(CheckoutTotal);
		    System.out.println(Checkout);	
		    Thread.sleep(3000);
		    FileOutputStream ExcelOut= new FileOutputStream("D://TC03_step21.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet=workbook.createSheet();	
			
			for(int i=0;i<Checkout.size();i++)
		      {
		    	   sheet.createRow(i).createCell(0).setCellValue(Checkout.get(i));
		    	   		    	  		    
		    	   }
	     	
			 workbook.write(ExcelOut);
			   workbook.close();
			   
	            
	  	   
	  	   //Step 22:Billing details rollup
	  	   driver.findElement(By.xpath(pro.getProperty("TC03.BillingDetails.Modify.xpath"))).click();
	  	  System.out.println("Step 22:Billing details rollup");
	  	   Thread.sleep(2000);
	  	   //Step 23: First address
	  	   driver.findElement(By.xpath(pro.getProperty("TC03.BillingDetails.1stoption.xpath"))).click();
	  	   System.out.println("Step 23:First address");
	  			   
	  			 //Continue1
	  			 driver.findElement(By.id(pro.getProperty("Continue1button.id"))).click();
	  		   	   System.out.println("clicked on continue1");
	  		   	   Thread.sleep(3000);
	  		   	 //Continue2
	  		   	driver.findElement(By.id(pro.getProperty("Continue2button.id"))).click();
	  		  	   System.out.println("clicked on continue2");
	  		  	   Thread.sleep(3000);
	  		  	   
	  		  	   		  	   
	  		  	  //continue3
	  		  	driver.findElement(By.id(pro.getProperty("Continue3button.id"))).click();
	  		   	   System.out.println("clicked on continue3");
	  		   	   Thread.sleep(3000);
	  		   	   
	  		  	//Checkbox
	  		   	driver.findElement(By.name(pro.getProperty("TermsandConditions.name"))).click();
	  		  	   System.out.println("checkbox selected");
	  		  	   Thread.sleep(3000);
	  		  	   
	  		  	//Continue
	  		  	  driver.findElement(By.id(pro.getProperty("Continuebutton.id"))).click();
	  		  	   System.out.println("continue");
	  		  	   Thread.sleep(3000);
	  		  
	  		  //Step 24:confirm order
	  	      driver.findElement(By.id(pro.getProperty("Confirmorder.id"))).click();
	  	      System.out.println("Step 24:confirm button");
	  	       Thread.sleep(3000);
	  	     logger.log(LogStatus.PASS, "Method \"AddPhones\" is passed");  
      }
	  	   
      @AfterClass
  	public void Logout()
  	{
  		driver.findElement(By.linkText(pro.getProperty("Logout.linkText"))).click();
   	   
   	 
  		String f= driver.findElement(By.xpath(pro.getProperty("Logout.xpath"))).getText();
  	 	  Assert.assertEquals(f,"Account Logout");
  			System.out.println("Logout Successful");
  	}
}






