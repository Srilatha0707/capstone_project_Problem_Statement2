package com.qa.testscript;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Flipkart_001 extends TestBase{
	@Test(priority = 0)
	public void checkPopUpCloseButton() throws IOException {
		boolean close=fp.getPopUpCloseButton().isEnabled();
		if(close) {
			Assert.assertTrue(true);
		}
		else {
			captureScreenshot(driver,"checkPopUpCloseButton");
			Assert.assertTrue(false,"popup button is not enabled");
		}
		fp.getPopUpCloseButton().click();
	}
	@Test(priority = 1,dependsOnMethods = "checkPopUpCloseButton")
	public void checkPosition() throws IOException {
		if(fp.getFlipKartLogo().isDisplayed()) {
			Assert.assertTrue(true,"Flipkart logo is present on page");
			int logolocation=fp.getFlipKartLogo().getLocation().getX();
			if(logolocation<500) {
				Assert.assertTrue(true,"Flipkart Logo is on left side of the page");
			}
			else {
				captureScreenshot(driver,"checkPosition");
				Assert.assertTrue(false,"Flipkart Logo is not available on left side");
			}
		}
		else {
			captureScreenshot(driver,"checkPosition");
			Assert.assertTrue(false,"Flipkart logo is not present on the page");
		}
	}
	
	@Test(priority = 2,dependsOnMethods = "checkPopUpCloseButton")
	public void checkSearch() throws InterruptedException, IOException {
		fp.getSearchbar().sendKeys("iphone 14");
		Thread.sleep(2000);
		boolean search_button=fp.getSearchSymbol().isEnabled();
		if(search_button) {
			Assert.assertTrue(true);
		}
		else {
			captureScreenshot(driver,"checkSearch");
			Assert.assertTrue(false);
		}
		fp.getSearchSymbol().click();
		int items=fp.getSearchedItem().size();
		System.out.println(" Number of Items Displayed "+items);
		String firstItem=fp.getSearchedItem().get(0).getText();
		//click on first displayed Search Item
        fp.getSearchedItem().get(0).click();
        Set<String> handler=driver.getWindowHandles();
        Iterator<String> li=handler.iterator();
        String ParentHandleID=li.next();
        String ChildHandleID=li.next();
        driver.switchTo().window(ChildHandleID);
        String selectedfirstitem=driver.getTitle();
        if(driver.getPageSource().contains(firstItem) && selectedfirstitem.contains("APPLEiPhone 14 ( 128 GB Storage ) Online at Best Price On Flipkart.com")) {
        	Assert.assertTrue(true);
        }
        else {
        	captureScreenshot(driver,"checkSearch");
        	Assert.assertTrue(false,"Not Naviagted to correct page");
        }
        Assert.assertEquals(selectedfirstitem,"APPLE iPhone 14 ( 128 GB Storage ) Online at Best Price On Flipkart.com",
        		"title is not correct");
	}
}
