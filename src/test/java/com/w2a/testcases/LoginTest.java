package com.w2a.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class LoginTest extends TestBase {

//	@Test
//	public void loginBankManager() throws InterruptedException {
//		String locator = OR.getProperty("bmlBtn");
//	    System.out.println(locator);
//	    String locator2 = OR.getProperty("addCustBtn");
//	    System.out.println(locator2);
//
////		driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
//	    
//	    log.debug("Inside the Login Test !!!!!!");
//	    driver.findElement(By.cssSelector(locator)).click();
//	    Thread.sleep(2000);
//	    
//	    Assert.assertTrue(isElemetPresent(By.cssSelector(locator2)));
//	    log.debug("Login Successfully Executed !!! ");
//	}

	@Test
	public void loginBankManager() {

		if (!TestUtil.isTestRunnable("loginBankManager", excel)) {
			throw new SkipException("Skipping Test Case as Runmode is NO");
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		String bankManagerBtn = OR.getProperty("bmlBtn");
		String addCustomerBtn = OR.getProperty("addCustBtn");

		// ✅ ASSERT 1: Bank Manager Button Present
		Assert.assertTrue(isElementPresent(By.cssSelector(bankManagerBtn)), "Bank Manager Button NOT Found");

		log.debug("Clicking Bank Manager Login");
		Reporter.log("Clicking Bank Manager Login");
		// Click Bank Manager
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(bankManagerBtn))).click();

//		log.debug("Clicked Bank Manager");
		Reporter.log("Clicked Bank Manager => Report Log ");

		// ✅ ASSERT 2: Add Customer Button Visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(addCustomerBtn)));

		Assert.assertTrue(isElementPresent(By.cssSelector(addCustomerBtn)),
				"Add Customer Button NOT Visible After Login");

//		log.debug("Add Customer Button is visible");
		Reporter.log("Add Customer Button is visible");

		// Click Add Customer
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(addCustomerBtn))).click();

		log.debug("Clicked Add Customer");
		Reporter.log("Clicked Add Customer");

		// ✅ ASSERT 3: Verify Navigation (Example)
		String expectedUrlPart = "manager"; // adjust if needed
		Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrlPart), "Navigation to Manager Page FAILED");

		log.debug("Login Test Passed Successfully");
		Reporter.log("Login Test Passed Successfully ");
		// log.debug("Clicking Bank Manager Login");

//
//	    // Step 1: Click Bank Manager
//	    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(bankManagerBtn))).click();
//
//	    log.debug("Clicked Bank Manager");
//
//	    // ✅ IMPORTANT: Wait for Add Customer button to be visible (page load)
//	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(addCustomerBtn)));
//
//	    // Step 2: Click Add Customer
//	    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(addCustomerBtn))).click();
//
//	    log.debug("Clicked Add Customer");
	}

}
