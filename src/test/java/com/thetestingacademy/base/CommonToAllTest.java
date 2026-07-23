package com.thetestingacademy.base;

import com.thetestingacademy.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

/**
 * CommonToAllTest — Base test class.
 *
 * Every test class extends this to get automatic:
 *   • Browser launch before each test   (@BeforeMethod)
 *   • Browser teardown after each test  (@AfterMethod)
 *   • Access to the shared WebDriver and Logger
 */
@org.testng.annotations.Listeners({com.thetestingacademy.listeners.AllureScreenshotListener.class})
public class CommonToAllTest {

    protected WebDriver driver;
    protected Logger logger = LogManager.getLogger(this.getClass());

    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("══════ Setting up WebDriver ══════");
        DriverManager.init();
        driver = DriverManager.getDriver();
        logger.info("Browser launched: " + driver.getClass().getSimpleName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(org.testng.ITestResult result) {
        logger.info("══════ Tearing down WebDriver ══════");
        DriverManager.down();
    }
}
