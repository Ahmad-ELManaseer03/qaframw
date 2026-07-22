package com.thetestingacademy.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentManager {

    private static final ExtentReports extentReports = new ExtentReports();
    private static final ThreadLocal<ExtentTest> extentTestMap = new ThreadLocal<>();

    static {
        // Ensure the directory exists
        File reportDir = new File("target/ExtentReports");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/ExtentReports/ExtentReport.html");
        sparkReporter.config().setReportName("CareConnect - Countries Module Automation Report");
        sparkReporter.config().setDocumentTitle("QA Test Execution");
        sparkReporter.config().setTheme(Theme.STANDARD);
        
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Framework", "CareConnect Automation Framework");
        extentReports.setSystemInfo("Environment", "QA");
        extentReports.setSystemInfo("Author", "Senior QA Automation Engineer");
    }

    public static ExtentReports getExtentReports() {
        return extentReports;
    }

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get();
    }

    public static synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest test = extentReports.createTest(testName, description);
        extentTestMap.set(test);
        return test;
    }

    public static synchronized void flush() {
        extentReports.flush();
    }
}
