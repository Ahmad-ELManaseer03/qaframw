package com.thetestingacademy.listeners;

import com.thetestingacademy.driver.DriverManager;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureScreenshotListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // Not needed
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            String status = "Screenshot";
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS:
                    status = "Screenshot on Success";
                    break;
                case ITestResult.FAILURE:
                    status = "Screenshot on Failure";
                    break;
                case ITestResult.SKIP:
                    status = "Screenshot on Skipped";
                    break;
            }
            attachScreenshot(testResult, status);
        }
    }

    private void attachScreenshot(ITestResult result, String statusPrefix) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver != null) {
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String screenshotName = statusPrefix + ": " + result.getName();
                Allure.addAttachment(screenshotName, "image/png", new ByteArrayInputStream(screenshotBytes), "png");
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot in AllureScreenshotListener: " + e.getMessage());
        }
    }
}
