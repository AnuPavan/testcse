package com.wipro.opencart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class GenericMethods {
	
	
	
	public void dropDownText( WebElement element,String Text){
		
		
		Select E = new Select(element);
 		E.selectByVisibleText(Text);
	}

	public static void sleepW(int seconds) throws InterruptedException
	{
		Thread.sleep(seconds);
		
		
		
	}
public void dropDownIndex( WebElement element,int index){
		
		
		Select E = new Select(element);
 		E.selectByIndex(index);
	}

public void mySendText(WebElement element,String Text){
	
	element.sendKeys(Text);
	
	
	
}
	
	
}
