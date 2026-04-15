
package com.w2a.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static WebDriverWait wait;

	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

//	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static Logger log = Logger.getLogger(TestBase.class);

	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\Book1.xlsx");

	public static ExtentReports init() {

		try {
			if (rep == null) {
				ExtentSparkReporter spark = new ExtentSparkReporter("target/extent-report.html");
				rep = new ExtentReports();
				rep.attachReporter(spark);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rep;
	}

	@BeforeSuite
	public void setUp() {

		// ✅ Log4j setup
		PropertyConfigurator
				.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\log4j.properties");

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
				config.load(fis);
//				log.debug("Config File Loaded");
				Reporter.log("Config file Loaded", true);

			} catch (IOException e) {
				log.error("Failed to load Config file", e);
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
				OR.load(fis);
//				log.debug("OR File Loaded");
				Reporter.log("OR File Loaded", true);
			} catch (IOException e) {
				log.error("Failed to load OR file", e);
			}

			// ✅ Browser Setup
			if (config.getProperty("browser").equalsIgnoreCase("chrome")) {

//				WebDriverManager.chromedriver().setup();
				WebDriverManager.chromedriver().browserVersion("146").setup();

				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--disable-notifications");
				options.addArguments("--start-maximized");
				options.addArguments("--headless=new");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--no-sandbox");

				driver = new ChromeDriver(options);

				Reporter.log("Chrome Launched !!!", true);
//				log.debug("Chrome Launched");
			}

			// ✅ Initialize Explicit Wait
			wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			// ✅ Open URL
			driver.get(config.getProperty("testsiteurl"));
//			log.debug("Navigated to: " + config.getProperty("testsiteurl"));
			Reporter.log("Navigated to: " + config.getProperty("testsiteurl"), true);

			driver.manage().window().maximize();

			// ✅ Implicit Wait (keep small)
			driver.manage().timeouts()
					.implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
		}
	}

	// ========================= GENERIC METHODS =========================

	// ✅ Click Method (Safe)
	public static void click(String locatorKey) {

		By locator = By.cssSelector(OR.getProperty(locatorKey));

		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		wait.until(ExpectedConditions.elementToBeClickable(locator));

		driver.findElement(locator).click();

//		log.debug("Clicked on: " + locatorKey);
//		Reporter.log("Clicked on: " + locatorKey, true); // for TestNG
		test.log(Status.INFO, "Clicked on: " + locatorKey); // For Extent Report

	}

	// ✅ Type Method
	public static void type(String locatorKey, String value) {

		By locator = By.cssSelector(OR.getProperty(locatorKey));

		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(value);

//		log.debug("Typed in: " + locatorKey + " value: " + value);
		test.log(Status.INFO, "Typed: " + locatorKey + " = " + value); // For Extent Report

	}

	public static void select(String locatorKey, String value) {

		By locator = By.cssSelector(OR.getProperty(locatorKey));

		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		Select select = new Select(driver.findElement(locator));

		// ✅ Wait until dropdown options are loaded
		wait.until(driver -> select.getOptions().size() > 1);
		boolean found = false;

		for (WebElement option : select.getOptions()) {

			System.out.println("OPTION: " + option.getText()); // DEBUG

			if (option.getText().contains(value)) {
				option.click();
				found = true;
				break;
			}
		}

		if (!found) {
			throw new RuntimeException("Value NOT found in dropdown: " + value);
		}
		select.selectByVisibleText(value);

		Reporter.log("Selected: " + value + " from " + locatorKey, true);
	}

	// ✅ Check element present
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	@AfterSuite
	public void tearDown() {

		if (driver != null) {
			driver.quit();
			log.debug("Browser Closed");
		}

		if (rep != null) {
			rep.flush();
			System.out.println("FLUSH FROM BASE");
		}
//		log.debug("Test Execution Completed");
		Reporter.log("Testing Execution Completed !!!!");
	}
}
