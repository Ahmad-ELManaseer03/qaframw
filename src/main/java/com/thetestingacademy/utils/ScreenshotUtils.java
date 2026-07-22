package com.thetestingacademy.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtils {

    /**
     * Takes a screenshot and saves it to the target/screenshots directory.
     *
     * @param driver         The WebDriver instance.
     * @param screenshotName The name of the screenshot file (e.g., "TC01_CreateCountry_Success.png").
     */
    public static void takeScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) {
            return;
        }

        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            
            Path screenshotDir = Paths.get("target", "screenshots");
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }
            
            Path destinationPath = screenshotDir.resolve(screenshotName);
            Files.copy(screenshotFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("Screenshot saved successfully at: " + destinationPath.toAbsolutePath().toString());
        } catch (Exception e) {
            System.out.println("Failed to save screenshot '" + screenshotName + "': " + e.getMessage());
        }
    }
}
