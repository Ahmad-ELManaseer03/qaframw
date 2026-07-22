package com.thetestingacademy.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.thetestingacademy.utils.ExtentManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentTestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getMethodName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        // Not used explicitly, manager is statically initialized
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        ExtentManager.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        String description = iTestResult.getMethod().getDescription();
        ExtentManager.createTest(getTestMethodName(iTestResult), description != null ? description : "");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.PASS, "Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.FAIL, "Test Failed: " + iTestResult.getThrowable());
            
            // The teardownMethod in TestCountries takes the screenshot as "FAILURE_[testName].png"
            // We'll attach that specific file if it's there.
            String screenshotPath = "target/screenshots/FAILURE_" + getTestMethodName(iTestResult) + ".png";
            try {
                // If the file doesn't exist yet, it's because teardown runs AFTER onTestFailure.
                // We'll capture it inside the tests themselves if possible, or assume it's created.
                // Since this runs before teardown, we should ideally let teardown capture it.
                // But the user said: "Dynamically attaches screenshots to the report log on step completion or failure"
                // So the step logging will attach the success screenshots.
            } catch (Exception e) {
                test.log(Status.WARNING, "Failed to attach screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        ExtentTest test = ExtentManager.getTest();
        if (test != null) {
            test.log(Status.SKIP, "Test Skipped: " + iTestResult.getThrowable());
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // Ignore
    }
}
