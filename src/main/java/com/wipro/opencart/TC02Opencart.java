package com.wipro.opencart;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

public class TC02Opencart extends Extentreports

{

	
	Properties pro;
	WebDriver driver;
	String url;
	
	//Step:1 Launch  Open Cart application http://10.207.182.108:81/opencart/
	
	@BeforeClass
	public void login() throws IOException, InterruptedException
	{
	    
		//System.setProperty("webdriver.chrome.driver","D://Selenium Drivers//chromedriver.exe");
		//driver = new ChromeDriver();
		
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
		WebDriverWait wait=new WebDriverWait(driver, 20);
		driver.get("http://10.207.182.108:81/opencart/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
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
	
	//Step 4: Enter the key word in search text box using excel Validation: "apple" (in SearchProd.xlsx)
	@Test(dataProvider="Search",priority=1)
	public void Search(String Ant) throws AWTException	
	{
		logger = extent.startTest("Search");
		//Search
		driver.findElement(By.xpath(pro.getProperty("TC02.Search.xpath"))).sendKeys(Ant);
		Robot rb= new Robot();
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		
		System.out.println("Step 4:searched  Apple in search box");
		logger.log(LogStatus.PASS, "Method \"Search\" is passed"); 

	}
	@DataProvider(name="Search")
	public static Object[][] DataPro() throws IOException
	{
		Object[][] obj = ExcelData1();
		return obj;
	}
	
	
	//Reading the excel for Registration
	public static Object[][] ExcelData1() throws IOException
	{
		// TODO Auto-generated method stub
		File fil = new File("D:\\TC02Search.xlsx");
		FileInputStream fis = new FileInputStream(fil);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sh = wb.getSheet("Sheet1");
		int rowcount = sh.getLastRowNum()+1;
		Row fistrow = sh.getRow(0);
		int colcount = fistrow.getLastCellNum();
		Object[][] obj = new Object[rowcount][colcount];
		for(int i=0; i<rowcount; i++)
		{
			Row row = sh.getRow(i);
			for(int j=0; j<row.getLastCellNum();j++)
			{
				Cell cell = row.getCell(j);
				System.out.print(cell+" ");
				obj[i][j]=cell.getStringCellValue();
			}
			System.out.print("\n");
		}
		return obj;
		
	}

	@Test(priority=2)
	public void ProductComparision() throws InterruptedException, IOException
	{
		logger = extent.startTest("ProductComparision"); 
		Thread.sleep(3000);
		
		//Step 4: Validation: Get the number of items in the grid and output to flat file 
		try
		{
			driver.findElement(By.xpath(pro.getProperty("TC02.Gridxpath"))).click();
		}
		catch(Exception e)
		{
		
		}
			
 		BufferedWriter bw= new BufferedWriter(new FileWriter("D:\\TC02.txt"));
 		List<WebElement> GridItemcount = driver.findElements(By.xpath(pro.getProperty("TC02.Grid.ProductCompare")));
 		GridItemcount.size();
 		System.out.println("Items count = "+GridItemcount.size());
 		bw.write("Items count = "+GridItemcount.size());
 		bw.close();
 		
 		
 		
	  //Step 5: Select "Monitors" under Components in the drop down.  
       WebElement drop= driver.findElement(By.name(pro.getProperty("TC02.AllCategories.name")));
       Select E = new Select(drop);
		E.selectByValue("28");
		System.out.println("Step 5:Selected Monitors from dropdown");
		
		//Step 5: Checkbox
 		driver.findElement(By.name(pro.getProperty("TC02.SubCategory.name"))).click();
 		System.out.println("Step 5:Check box selected");
 		
 		//Step 5: Searchbutton
 		
 		driver.findElement(By.id(pro.getProperty("TC02.Searchbutton.id"))).click();
 		System.out.println("Step 5:Clicked on search button");
 		
 		BufferedWriter bw1= new BufferedWriter(new FileWriter("D:\\TC02_1.txt"));
 		List<WebElement> GridItemcount1 = driver.findElements(By.xpath(pro.getProperty("TC02.Grid.ProductCompare")));
 		GridItemcount1.size();
 		System.out.println("Items count = "+GridItemcount1.size());
 		bw1.write("Items count = "+GridItemcount1.size());
 		bw1.close();
 		
 		
 		//Step 6: Phones and PDA's tab
 		driver.findElement(By.xpath(pro.getProperty("TC02.Phones&PDAs.xpath"))).click();
 			//Step 6: Validation
 		try
		{
			driver.findElement(By.xpath(pro.getProperty("TC02.Gridxpath"))).click();
		}
		catch(Exception e)
 		{
			
		}
 		//Step 6: Validation
			
 		BufferedWriter bw2= new BufferedWriter(new FileWriter("D:\\TC02_2.txt"));
 		List<WebElement> GridItemcount2 = driver.findElements(By.xpath(pro.getProperty("TC02.Grid.ProductCompare")));
 		GridItemcount2.size();
 		System.out.println("Step 6: Items count = "+GridItemcount2.size());
 		bw2.write("Items count = "+GridItemcount2.size());
 		
 		
 		//Step 7: Price sort
 		WebElement drop1= driver.findElement(By.xpath(pro.getProperty("TC02.Pricesort.xpath")));
	       Select F = new Select(drop1);
	 		F.selectByVisibleText("Price (High > Low)");
	 		System.out.println("Step 7:Price (High > Low) is selected");
	 		
	 		Thread.sleep(3000);
	 		
	 	//Step 8: Add to compare for first 3 phones
	 		/*driver.findElement(By.xpath(pro.getProperty("TC02.AddToCompare.product1.xpath"))).click();
	 		Thread.sleep(3000);
	 		driver.findElement(By.xpath(pro.getProperty("TC02.AddToCompare.product2.xpath"))).click();
	 		Thread.sleep(3000);
	 		driver.findElement(By.xpath(pro.getProperty("TC02.AddToCompare.product3.xpath"))).click();
	 		
	 		System.out.println("Step 8:3 products added to compare");
	 		
	 		Thread.sleep(3000);
	 		*/
	 		
	 		ArrayList<String> prodName = new ArrayList<String>();
	 		for (int i =1; i<=GridItemcount2.size();i++)
	 		{
	 			// driver.findElement(By.xpath("(//a[text()='Add to Compare'])[i]")).click();
	 			driver.findElement(By.xpath(".//*[@id='content']/div[4]/div["+i+"]/div[7]/a")).click();
	 			Thread.sleep(2000);
	 			driver.findElement(By.xpath(pro.getProperty("TC02.Grid.ProductCompare.notification"))).click();
	 			Thread.sleep(2000);
	 			System.out.println("Step 8: Product is added" + i);
	 			String Prod1 = driver.findElement(By.xpath(".//*[@id='content']/div[4]/div["+i+"]/div[2]/a")).getText();
	 			prodName.add(Prod1);
	 			System.out.println("Product Name" +prodName);
	 			System.out.println("Step 8:The entered text " + Prod1);
	 			bw2.write("The Product name is " + i + " : "  + Prod1);
	 			bw2.newLine();

	 	     }
	 		bw2.close();
          System.out.println("Step 8:Add comparison is done");
	 		
	 		
	 		
            //Step 9: Click on Product compare
         
             driver.findElement(By.xpath(pro.getProperty("TC02.Productcompare.xpath"))).click();
             System.out.println(("Step 9:clicked on product compare"));
             Thread.sleep(5000);

            //validation
             for (int i =0; i<GridItemcount2.size();i++)
             {
            	 String prodcompname= driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td["+(i+2)+"]/a")).getText();
            	 System.out.println("link compare with value in flat file" + prodcompname);
             	Assert.assertEquals(prodcompname, prodName.get(i));
            	 
            	 
             }
             
             /*String first= driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[2]/a")).getText();
             System.out.println("First link " + first);
            	Assert.assertEquals(first, prodName.get(0));
            	Thread.sleep(5000);
            	String second= driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[3]/a")).getText();
            	System.out.println("Second link " +second);
            	Assert.assertEquals(second, prodName.get(1));
         	Thread.sleep(5000);
            	String third= driver.findElement(By.xpath("//*[@id='content']/table/tbody[1]/tr[1]/td[4]/a")).getText();
            	System.out.println("Third link " + third);
            	Assert.assertEquals(third, prodName.get(2));

*/            	
            //Step 10: click on 1st phone link
            driver.findElement(By.xpath(pro.getProperty("TC02.1stPhoneLink.xpath"))).click();
            System.out.println("Step 10:clicked on the 1st phone link");
            Thread.sleep(3000); 
            
            //Step 11:Fifth feature in the description
            BufferedWriter bw3= new BufferedWriter(new FileWriter("D:\\TC02_3.txt"));
       		String desc= driver.findElement(By.xpath(pro.getProperty("TC03.5thdesc.xpath"))).getText();
       		bw3.write(desc);
       		bw3.close();
   
        
            //Step 12: click on add to cart
            driver.findElement(By.xpath(pro.getProperty("TC02.1stPhone.AddTocart.xpath"))).click();
            System.out.println("Step 12:Add to cart");
            Thread.sleep(3000);
             
             //Step 13: click on Shopping cart
            driver.findElement(By.partialLinkText(pro.getProperty("Ribbon.shoppingcart.partiallinkText"))).click();
            System.out.println("Step 13:Shopping cart link");
            Thread.sleep(3000);
            
            //Step 14: Click on checkout
            driver.findElement(By.partialLinkText(pro.getProperty("Checkout.partiallinkText"))).click();
            System.out.println("Step 14:Checkout done");
            Thread.sleep(3000);
            
            
           //Step 15: Continue1
       	   driver.findElement(By.id(pro.getProperty("Continue1button.id"))).click();
       	   System.out.println("Step 15:clicked on continue1");
       	   Thread.sleep(3000);
       	   
       	   //Continue2
       	   driver.findElement(By.id(pro.getProperty("Continue2button.id"))).click();
       	   System.out.println("Step 15:clicked on continue2");
       	   Thread.sleep(3000);
       	   
       	   //continue3
       	   driver.findElement(By.id(pro.getProperty("Continue3button.id"))).click();
       	   System.out.println("Step 15:clicked on continue3");
       	   Thread.sleep(3000);
       	   
       	  //Step 16: Checkbox
      	   driver.findElement(By.name(pro.getProperty("TermsandConditions.name"))).click();
      	   System.out.println("Step 16:checkbox selected");
      	   Thread.sleep(3000);
      	   
      	   //continue button
      	   driver.findElement(By.id(pro.getProperty("Continuebutton.id"))).click();
      	   System.out.println("Step 16:Continue button");
      	   Thread.sleep(3000);
      	    
      	   //Step 17: confirm order
      	   driver.findElement(By.id(pro.getProperty("Confirmorder.id"))).click();
      	   System.out.println("Step 17:confirm button");
      	   Thread.sleep(3000);
      	   
      	   //Step 18: navigate back
      	   driver.navigate().back();
      	   System.out.println("Step 18:Navigating back");
      	   Thread.sleep(3000);

      	   //Step 19: MyAccount
      	   driver.findElement(By.linkText(pro.getProperty("TC02.MyAccount.LinkText"))).click();
      	   System.out.println("Step 19:My Account");
      	   Thread.sleep(3000);
      	   
      	   //Order History
      	 driver.findElement(By.linkText(pro.getProperty("OrderHistory.linkText"))).click();
    	   System.out.println("Step 19:Order History");
    	   Thread.sleep(3000);
    	   
    	  //Step 20: Subscribe to Newsletter
    	   driver.findElement(By.linkText(pro.getProperty("TC02.Newsletter.LinkText"))).click();
    	   Thread.sleep(3000);
    	   driver.findElement(By.xpath(pro.getProperty("TC02.Newsletter.Continue.xpath"))).click();
    	   Thread.sleep(3000);
    	   driver.findElement(By.linkText(pro.getProperty("TC02.Subscribe/UnSubscribe.LinkText"))).click();
      	   System.out.println("Step 20:Subscribe / unsubscribe to newsletter");
      	   Thread.sleep(3000);
      	   
      	  //Step 21: Specials footer
           driver.findElement(By.xpath(pro.getProperty("TC02.Specials.footer.xpath"))).click();
           System.out.println("Step 21:Specials footer");
         	Thread.sleep(3000);
         	
         	//Step 22: List/Grid
         	
         	try
            {
         		driver.findElement(By.xpath(pro.getProperty("TC02.Listxpath"))).click();
         		//System.out.println("TC02-Step22:List is Enabled Clicked on List Successfully");
         	}
         	catch(Exception e)
         	{
         		driver.findElement(By.xpath(pro.getProperty("TC02.Gridxpath"))).click();
         		System.out.println("TC02-Step22: Clicked on Grid Successfully");
            } 
         	logger.log(LogStatus.PASS, "Method \"ProductComparision\" is passed");
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
        
	 	



