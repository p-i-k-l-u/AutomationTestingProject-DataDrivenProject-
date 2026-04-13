//package com.w2a.utilities;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
//public class ExtentManager {
//
//	private static ExtentReports extent;
//
//	public static ExtentReports getInstance() {
//
//		if (extent == null) {
//
//			String reportPath = System.getProperty("user.dir") + "/target/surefire-reports/html/extent.html";
//
//			ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
//			reporter.config().setReportName("Automation Test Report");
//			reporter.config().setDocumentTitle("Test Results");
//
//			extent = new ExtentReports();
//			extent.attachReporter(reporter);
//		}
//
//		return extent;
//	}
//}

//  ------------------------- NEW Code ----------------
package com.w2a.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	private static ExtentReports extent;

	public static ExtentReports getInstance() {

		if (extent == null) {

			ExtentSparkReporter spark = new ExtentSparkReporter("target/surefire-reports/html/extent-report.html");
			spark.config().setReportName("Automation Report");

			extent = new ExtentReports();
			extent.attachReporter(spark);
		}

		return extent;
	}
}