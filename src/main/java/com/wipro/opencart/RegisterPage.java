package com.wipro.opencart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegisterPage {
	
	 WebDriver driver;
     
	 
     public RegisterPage(WebDriver driver)
     {
           this.driver=driver;
           PageFactory.initElements(driver, this);
     }
    
    
     @FindBy(xpath="//div[@id='welcome']/a[2]") public WebElement CreateAcct;
    
     @FindBy(name="firstname") public  WebElement fnameR;
     @FindBy(name="lastname") public  WebElement LnameR;
     @FindBy(name="email") public  WebElement emailR;
     @FindBy(name="telephone") public  WebElement telephoneR;
     @FindBy(name="fax") public  WebElement faxR;
     @FindBy(name="company") public  WebElement companyR;

     @FindBy(name="country_id") public  WebElement countryidR;

	

}
