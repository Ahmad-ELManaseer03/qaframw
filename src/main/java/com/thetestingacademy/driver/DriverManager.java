package com.thetestingacademy.driver;

import com.thetestingacademy.utils.PropertiesReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * DriverManager — Singleton-style WebDriver lifecycle manager.
 *
 * Responsibilities:
 *   • Reads the target browser from data.properties
 *   • Instantiates the correct WebDriver (Chrome, Firefox, Edge)
 *   • Provides a static getter/setter used by all page objects and tests
 *   • Tears down the driver cleanly to prevent session leaks
 */
public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    // ── Getter / Setter ────────────────────────────────────────
    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    // ── Initialization ─────────────────────────────────────────
    public static void init() {
        String browser = PropertiesReader.readKey("browser");

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--guest");
                setDriver(new ChromeDriver(chromeOptions));
                break;
            case "edge":
                setDriver(new EdgeDriver());
                break;
            case "firefox":
                setDriver(new FirefoxDriver());
                break;
            default:
                // Fall back to Chrome if an unrecognized browser is specified
                setDriver(new ChromeDriver());
        }

        getDriver().manage().window().maximize();
    }

    // ── Teardown ───────────────────────────────────────────────
    public static void down() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
