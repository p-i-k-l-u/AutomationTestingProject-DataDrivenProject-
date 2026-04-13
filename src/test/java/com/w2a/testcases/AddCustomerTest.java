//package com.w2a.testcases;
//
//import java.time.Duration;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//import org.testng.Reporter;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import com.aventstack.extentreports.Status;
//import com.w2a.base.TestBase;
//
//public class AddCustomerTest extends TestBase {
//
//	// ✅ VERY IMPORTANT → Reset browser before each test iteration
//	@BeforeMethod
//	public void resetBrowser() {
//		driver.get(config.getProperty("testsiteurl"));
//		log.debug("Browser Reset for New Test");
//		Reporter.log("Browser Reset for New Test");
//		
//	}
//
//	@Test(dataProvider = "getData")
//	public void addCustomer(String firstName, String lastName, String postCode, String alertText) {
//
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
////		log.debug("Starting Add Customer Test");
//		test.log(Status.INFO, "Starting Add Customer Test");
//
//		By managerBtn = By.cssSelector(OR.getProperty("bmlBtn"));
//		By addCustBtn = By.cssSelector(OR.getProperty("addCustBtn"));
//
//		// ✅ Step 1: Click Bank Manager Login
//		wait.until(ExpectedConditions.visibilityOfElementLocated(managerBtn));
//		wait.until(ExpectedConditions.elementToBeClickable(managerBtn));
//		driver.findElement(managerBtn).click();
//
////		log.debug("Clicked Bank Manager Login");
////		Reporter.log("Clicked Bank Manager Login"); // For TestNG 
//		test.log(Status.INFO, "Clicked Bank Manager Login"); // for Extent Report 
//
//		// ✅ Step 2: Click Add Customer
//		wait.until(ExpectedConditions.visibilityOfElementLocated(addCustBtn));
//		wait.until(ExpectedConditions.elementToBeClickable(addCustBtn));
//		driver.findElement(addCustBtn).click();
//
//		log.debug("Clicked Add Customer Button");
//		Reporter.log("Clicked Add Customer Button");
//
//		// ✅ ASSERT: First Name Field visible
//		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("firstname"))), "First Name Field NOT found!");
//
//		// ✅ Step 3: Fill Form
//		driver.findElement(By.cssSelector(OR.getProperty("firstname"))).clear();
//		driver.findElement(By.cssSelector(OR.getProperty("firstname"))).sendKeys(firstName);
//
//		driver.findElement(By.cssSelector(OR.getProperty("lastname"))).clear();
//		driver.findElement(By.cssSelector(OR.getProperty("lastname"))).sendKeys(lastName);
//
//		driver.findElement(By.cssSelector(OR.getProperty("postcode"))).clear();
//		driver.findElement(By.cssSelector(OR.getProperty("postcode"))).sendKeys(postCode);
//
//		log.debug("Entered Customer Details");
//
//		// ✅ Step 4: Click Add Button
//		driver.findElement(By.cssSelector(OR.getProperty("addbtn"))).click();
//
//		log.debug("Clicked Add Customer Submit Button");
//
//		// ✅ Step 5: Handle Alert
//		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//
//		alertText = alert.getText();
//		log.debug("Alert Message: " + alertText);
//
//		// ✅ ASSERT: Verify success message
//		Assert.assertTrue(alertText.contains(alertText), "Customer NOT added!");
//
//		alert.accept();
//
//		log.debug("Add Customer Test Passed");
//	}
//
//	// ✅ Data Provider
//	@DataProvider
//	public Object[][] getData() {
//
//		String sheetName = "AddCustomerTest";
//		int rows = excel.getRowCount(sheetName);
//		int cols = excel.getColumnCount(sheetName);
//
//		Object[][] data = new Object[rows - 1][cols];
//
//		for (int rowNum = 2; rowNum <= rows; rowNum++) {
//			for (int colNum = 0; colNum < cols; colNum++) {
//
//				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
//			}
//		}
//		return data;
//	}
//}

// Full Code for Inside the Extent Log below code is for thta only 

package com.w2a.testcases;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	@BeforeMethod
	public void resetBrowser() {
		driver.get(config.getProperty("testsiteurl"));
	}

	@Test(dataProvider = "getData")
	public void addCustomer(String firstName, String lastName, String postCode, String expectedAlertText) {

		// ✅ RUNMODE CHECK
		if (!TestUtil.isTestRunnable("AddCustomerTest", excel)) {
			throw new SkipException("Skipping AddCustomerTest as Runmode is NO");
		}

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		test.log(Status.INFO, "Starting Add Customer Test");

		click("bmlBtn");
		click("addCustBtn");

		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("firstname"))), "First Name Field NOT found!");

		type("firstname", firstName);
		type("lastname", lastName);
		type("postcode", postCode);

		click("addbtn");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String actualAlertText = alert.getText();

		test.log(Status.INFO, "Alert message: " + actualAlertText);

		Assert.assertTrue(actualAlertText.contains(expectedAlertText), "Customer NOT added!");

		test.log(Status.PASS, "Customer added successfully");

		alert.accept();
	}

	@DataProvider
	public Object[][] getData() {

		String sheetName = "AddCustomerTest";
		int rows = excel.getRowCount(sheetName);
		int cols = excel.getColumnCount(sheetName);

		Object[][] data = new Object[rows - 1][cols];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < cols; colNum++) {
				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
			}
		}
		return data;
	}
}