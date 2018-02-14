package com.wipro.opencart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

public class TC04Opencart extends Extentreports
{
	Properties pro;
	WebDriver driver;
	List<WebElement> productlist;
	String Orderid;
	
	//Step 1,2:Launch Browser and Opencart
	@BeforeClass
	public void login() throws IOException, InterruptedException
	{
	    
		System.setProperty("webdriver.chrome.driver","D://Selenium Drivers//chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://10.207.182.108:81/opencart/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		Thread.sleep(5000);
		
		 File file = new File("C://Users//an247684//workspace//TopGear1//ObjectRepository.properties");	
		  FileInputStream fis = new FileInputStream(file);
		  pro = new Properties();
		  pro.load(fis); 
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
        
        try
        {
        driver.findElement(By.linkText(pro.getProperty("OrderHistory.linkText"))).click();
        driver.findElement(By.xpath(pro.getProperty("PreviousOrder.view.xpath"))).click();
        Thread.sleep(3000);
        Orderid = driver.findElement(By.xpath(pro.getProperty("OrderHistory.Orderid.xpath"))).getText();
        }
        catch(Exception e)
        {
        }
        
        //Step:4	
        driver.findElement(By.xpath(pro.getProperty("Home.xpath"))).click();
 	}
	
	// Step 5,6:Related products and validation
	@Test
	public void Featured() throws InterruptedException, IOException
	{
		logger = extent.startTest("Featured");
		driver.findElement(By.xpath(pro.getProperty("Home.Featured.AppleCinema.xpath"))).click();
		try
		{
		driver.findElement(By.xpath(pro.getProperty("AppleCinema.Relatedproducts.xpath"))).click();
		}
		catch(Exception e)
		{
			System.out.println("No Related Products available");
		}
		
		
		driver.findElement(By.xpath(pro.getProperty("Home.xpath"))).click();
		Thread.sleep(6000);
		
		
		if(driver.findElement(By.xpath(pro.getProperty("Featured.xpath"))).isDisplayed()){
		 productlist =driver.findElements(By.xpath(pro.getProperty("Featured.xpath")));
		System.out.println("No. of products:"  +productlist.size());
		}
		
		
		int count=0;
	
	for(int i=1; i<=productlist.size();i++)
	{ 
		Thread.sleep(3000);
		driver.findElement(By.xpath("//div[@class='box-product']/div["+i+"]/div[1]/a/img")).click();
		if(!(driver.findElement(By.xpath(pro.getProperty("Alltabs.xpath"))).getText().contains("Related Products")))
		{
		count++;
		}
		driver.navigate().back();
		
		
	}
	
	System.out.println("Products having no Related Products Count is :"  +count);
	
	//Step 7:Add to cart
	driver.findElement(By.xpath(pro.getProperty("Home.Featured.AppleCinema.xpath"))).click();
	driver.findElement(By.xpath(pro.getProperty("AppleCinema.Relatedproducts.xpath"))).click();
	driver.findElement(By.xpath(pro.getProperty("Relatedproducts.iphone.AddTocart.xpath"))).click();
	Thread.sleep(3000);
	
	//Step 8:shopping Cart
	    driver.findElement(By.partialLinkText(pro.getProperty("Ribbon.shoppingcart.partiallinkText"))).click();
	    System.out.println("clicked on Shopping cart");
	    Thread.sleep(3000);
	    
	
	    //Step 9,10:change quantity and update
 	    driver.findElement(By.name(pro.getProperty("Quantity.name"))).clear();
 	    driver.findElement(By.name(pro.getProperty("Quantity.name"))).sendKeys("2");
 	    driver.findElement(By.xpath(pro.getProperty("Update.xpath"))).click();
 	    
 	    System.out.println("changed the quantity");
 	    Thread.sleep(3000);
 	    
 	   
	    String ProductName= driver.findElement(By.xpath(pro.getProperty("Shoppingcart.iphone.ProductName.xpath"))).getText();
	    String Model= driver.findElement(By.xpath(pro.getProperty("Shoppingcart.iphone.Model.xpath"))).getText();
	    String Quantity= driver.findElement(By.xpath(pro.getProperty("Shoppingcart.iphone.Quantity.xpath"))).getAttribute("value");
	    String UnitPrice= driver.findElement(By.xpath(pro.getProperty("Shoppingcart.iphone.UnitPrice.xpath"))).getText();
	    String Total= driver.findElement(By.xpath(pro.getProperty("Shoppingcart.iphone.Total.xpath"))).getText();
	    
	    List<String> ShoppingCart = new ArrayList<String>();
	    ShoppingCart.add(ProductName);
	    ShoppingCart.add(Model);
	    ShoppingCart.add(Quantity);
	    ShoppingCart.add(UnitPrice);
	    ShoppingCart.add(Total);
	    System.out.println(ShoppingCart);	
	    
	 
 	 
 	   //Step 11:Checkout
 	    
 	    driver.findElement(By.linkText(pro.getProperty("Checkout.partiallinkText"))).click();
 	    System.out.println("update and checkout");
 	    Thread.sleep(3000);
 	   
 	   //Step 12:Continue1
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
  	   
  	  //Step 13:Checkbox
 	   driver.findElement(By.name(pro.getProperty("TermsandConditions.name"))).click();
 	   System.out.println("checkbox selected");
 	   Thread.sleep(3000);
 	   
 	   //Step 14:continue button and validation
 	   driver.findElement(By.id(pro.getProperty("Continuebutton.id"))).click();
 	   System.out.println("Continue button");
 	   Thread.sleep(3000);
 	   
 	   //Checkout
 	   
 	  String CheckoutProductName= driver.findElement(By.xpath(pro.getProperty("Checkout.iphone.ProductName.xpath"))).getText();
	    String CheckoutModel= driver.findElement(By.xpath(pro.getProperty("Checkout.iphone.Model.xpath"))).getText();
	    String CheckoutQuantity= driver.findElement(By.xpath(pro.getProperty("Checkout.iphone.Quantity.xpath"))).getText();
	    String CheckoutUnitPrice= driver.findElement(By.xpath(pro.getProperty("Checkout.iphone.Price.xpath"))).getText();
	    String CheckoutTotal= driver.findElement(By.xpath(pro.getProperty("Checkout.iphone.Total.xpath"))).getText();
	    
	    List<String> Checkout = new ArrayList<String>();
	    Checkout.add(CheckoutProductName);
	    Checkout.add(CheckoutModel);
	    Checkout.add(CheckoutQuantity);
	    Checkout.add(CheckoutUnitPrice);
	    Checkout.add(CheckoutTotal);
	    System.out.println(Checkout);	
	    Thread.sleep(3000);
	    
	   
	    FileOutputStream ExcelOut= new FileOutputStream("D:\\Test04.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet=workbook.createSheet();	
		
		for(int i=0;i<ShoppingCart.size();i++)
		{
        sheet.createRow(i).createCell(0).setCellValue(ShoppingCart.get(i));
        }
        
        
   
        for (int i=0;i<Checkout.size();i++)
		{
		sheet.getRow(i).createCell(1).setCellValue(Checkout.get(i));
		}
		
	
	   for(int i=0;i<ShoppingCart.size();i++)
	   {
		   if(ShoppingCart.get(i).contentEquals(Checkout.get(i)))
			 
		   {
		   sheet.getRow(i).createCell(2).setCellValue("true");
		   }
		   else 
			   {
			   sheet.getRow(i).createCell(2).setCellValue("false");
			   }
		   
	   }
	   workbook.write(ExcelOut);
	   workbook.close();
	   
		
		Thread.sleep(3000);
		
 	   //Step 15:confirm order
 	   driver.findElement(By.id(pro.getProperty("Confirmorder.id"))).click();
 	   System.out.println("confirm button");
 	   Thread.sleep(3000);
 	   
 	   //Step 16:Order History
 	   driver.findElement(By.linkText(pro.getProperty("OrderHistory.linkText"))).click();
 	   System.out.println("Order History");
 	   Thread.sleep(3000);
 	   
 	   
 	   
 	   //Step 17:view
 	   driver.findElement(By.xpath(pro.getProperty("PreviousOrder.view.xpath"))).click();
 	   System.out.println("view");
 	   Thread.sleep(3000);
 	   
 	  String od= driver.findElement(By.xpath(pro.getProperty("OrderHistory.Orderid.xpath"))).getText();
 	  System.out.println(od);
 	  try
 	  {
 		  Assert.assertNotEquals(Orderid, od);
 	  }
 	 catch(Exception e)
		{
 		driver.findElement(By.linkText("Logout")).click();
 		System.out.println("Logout successful");
		}
		
 	  	   
 	   //Step 18:Return
 	  driver.findElement(By.xpath(pro.getProperty("Return.xpath"))).click();
	   System.out.println("Return");
	   Thread.sleep(3000);
	   
	 //Step 19:Enter reason
	   driver.findElement(By.xpath(pro.getProperty("ReturnReason.xpath"))).click();
	   System.out.println("Reason");
	   Thread.sleep(8000);
	   
	   //Enter the captcha
	   System.out.println("Enter the captcha manually");
	   
	   //Step 20:Return continue button
	   driver.findElement(By.xpath(pro.getProperty("ReturnContinue.xpath"))).click();
	   System.out.println("Return Continue");
	   Thread.sleep(3000);
	   
	   //Continue button
	   driver.findElement(By.xpath(pro.getProperty("ProductReturns.continue.xpath"))).click();
	   System.out.println("Continue");
	   Thread.sleep(3000);
	   
	   logger.log(LogStatus.PASS, "Method \"Featured\" is passed");
	}
	//Step 21
	@AfterClass
	public void Logout1()
	{
		driver.findElement(By.linkText(pro.getProperty("Logout.linkText"))).click();
 	   
 	 
		String f= driver.findElement(By.xpath(pro.getProperty("Logout.xpath"))).getText();
	 	  Assert.assertEquals(f,"Account Logout");
			System.out.println("Logout Successful");
	}
	

}

	
 

