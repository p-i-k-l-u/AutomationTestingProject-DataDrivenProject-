package com.w2a.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class CustomeListeners extends TestBase implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {

		test = rep.createTest(result.getMethod().getMethodName());
		System.out.println("Extent Test Created: " + result.getMethod().getMethodName());
		// runModes - Y

	}

	@Override
	public void onTestSuccess(ITestResult result) {

		test.log(Status.PASS, "Test Passed");
	}

//	@Override
//	public void onTestFailure(ITestResult result) {
//
//		test.log(Status.FAIL, result.getThrowable());
//	}

	@Override
	public void onTestFailure(ITestResult result) {

		try {
			TestUtil.captureScreenshot();
			test.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "\\target\\surefire-reports\\html\\error.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}

		test.log(Status.FAIL, result.getThrowable());
	}

//	@Override
//	public void onTestSkipped(ITestResult result) {
//
//		test.log(Status.SKIP, "Test Skipped");
//	}

	@Override
	public void onTestSkipped(ITestResult result) {

		test.log(Status.SKIP, result.getMethod().getMethodName() + " SKIPPED");

		if (result.getThrowable() != null) {
			test.log(Status.SKIP, result.getThrowable());
		}
	}

	@Override
	public void onFinish(ITestContext context) {

		rep.flush(); // ✅ VERY IMPORTANT
	}
}
