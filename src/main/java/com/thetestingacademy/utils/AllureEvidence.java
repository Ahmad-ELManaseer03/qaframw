package com.thetestingacademy.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;

/**
 * Utility class to manually attach evidence (e.g. screenshots) to the Allure report.
 * This is used inside Allure.step lambdas to ensure screenshots are captured at the exact
 * moment a step completes, without relying on AspectJ weaving.
 */
public class AllureEvidence {

    /**
     * Captures a screenshot and attaches it to the current Allure step/test.
     * 
     * @param driver The WebDriver instance.
     * @param name   The name to give the screenshot attachment in the report.
     */
    public static void attachScreenshot(WebDriver driver, String name) {
        if (driver instanceof TakesScreenshot) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
        }
    }
}
