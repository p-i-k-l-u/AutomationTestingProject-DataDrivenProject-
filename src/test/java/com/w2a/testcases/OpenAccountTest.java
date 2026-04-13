//package com.w2a.testcases;
//
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
//
//import com.w2a.base.TestBase;
//
//public class OpenAccountTest extends TestBase {
//	
//	@BeforeMethod
//	public void resetBrowser() {
//	    driver.get(config.getProperty("testsiteurl"));
//	}
//
//	@Test(dataProvider = "getData")
//	public void openAccountTest(String customer, String currency) {
//
//		// Step 1: Click Bank Manager Login
//		click("bmlBtn");
//
//		// Step 2: Click Open Account
//		click("openaccount");
//
//		// Step 3: Select Customer
//		select("customer", customer);
//
//		// Step 4: Select Currency
//		select("currency", currency);
//
//		// Step 5: Click Process Button
//		click("processBtn");
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(
//				By.cssSelector(OR.getProperty("customer"))));
//		
//		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
//		alert.accept();
//	}
//
//	@DataProvider
//	public Object[][] getData() {
//
//		String sheetName = "OpenAccountTest";
//		int rows = excel.getRowCount(sheetName);
//		int cols = excel.getColumnCount(sheetName);
//
//		Object[][] data = new Object[rows - 1][cols];
//
//		for (int rowNum = 2; rowNum <= rows; rowNum++) {
//			for (int colNum = 0; colNum < cols; colNum++) {
//				data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
//			}
//		}
//		return data;
//	}
//
//}

//  ----------------------------------- NEW CODE -------------------------

package com.w2a.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class OpenAccountTest extends TestBase {

	@Test(dataProvider = "getData")
	public void openAccountTest(String customer, String currency) {

		// ✅ STEP 1: Always start from homepage
		driver.get(config.getProperty("testsiteurl"));

		// ✅ STEP 2: Click Bank Manager (ONLY ONCE)
		click("bmlBtn");

		// ✅ STEP 3: Add Customer
		click("addCustBtn");

		String firstName = excel.getCellData("AddCustomerTest", "firstname", 2);
		String lastName = excel.getCellData("AddCustomerTest", "lastname", 2);
		String postCode = excel.getCellData("AddCustomerTest", "postcode", 2);

		type("firstname", firstName);
		type("lastname", lastName);
		type("postcode", postCode);

		click("addbtn");

		Alert alert1 = wait.until(ExpectedConditions.alertIsPresent());
		alert1.accept();

		String fullName = firstName + " " + lastName;

		System.out.println("Created Customer: " + fullName);

		// ✅ STEP 4: Click Open Account (NO bmlBtn again)
		click("openaccount");

		// ✅ WAIT for dropdown
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(OR.getProperty("customer"))));

		// ✅ STEP 5: Select Customer
		select("customer", fullName);

		// ✅ STEP 6: Select Currency
		select("currency", currency);

		// ✅ STEP 7: Click Process
		click("processBtn");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
	}

	@DataProvider
	public Object[][] getData() {

		String sheetName = "OpenAccountTest";
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