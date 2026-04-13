package com.w2a.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.w2a.base.TestBase;

public class TestUtil extends TestBase {

	public static String screenshotPath;
	public static String screenshotName;

	public static void captureScreenshot() throws IOException {

		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		screenshotName = "error";

		FileUtils.copyFile(srcFile, new File(
				System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\" + screenshotName + ".jpg"));

	}

	public static boolean isTestRunnable(String testName, ExcelReader excel) {

		int rows = excel.getRowCount("test_suite");
		for (int rNum = 2; rNum <= rows; rNum++) {
			String testCase = excel.getCellData("test_suite", "TCID", rNum);

			if (testCase.equalsIgnoreCase(testName)) {
				String runMode = excel.getCellData("test_suite", "Runmode", rNum);

				if (runMode.equalsIgnoreCase("Y")) {
					return true;
				} else {
					return false;
				}
			}
		}

		return false;

	}

}
