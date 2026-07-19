package com.thetestingacademy.base;

import com.thetestingacademy.driver.DriverManager;
import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * CommonToAllPage — Base class for ALL page objects.
 *
 * Centralizes reusable page actions so individual page objects
 * never call driver.findElement() directly. This eliminates
 * duplication and makes maintenance much easier.
 *
 * Every page object in the framework extends this class.
 */
public class CommonToAllPage {

    // ── Driver Access ──────────────────────────────────────────
    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    // ── Click Actions ──────────────────────────────────────────
    public void clickElement(By by) {
        WaitHelpers.waitForClickable(getDriver(), by, 10);
        getDriver().findElement(by).click();
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    // ── Input Actions ──────────────────────────────────────────
    public void enterInput(By by, String text) {
        WaitHelpers.checkVisibility(getDriver(), by, 10);
        getDriver().findElement(by).sendKeys(text);
    }

    public void enterInput(WebElement element, String text) {
        element.sendKeys(text);
    }

    // ── Clear and Enter (useful for pre-filled fields) ─────────
    public void clearAndEnterInput(By by, String text) {
        WaitHelpers.checkVisibility(getDriver(), by, 10);
        WebElement element = getDriver().findElement(by);
        element.clear();
        element.sendKeys(text);
    }

    // ── Text Retrieval ─────────────────────────────────────────
    public String getText(By by) {
        return getDriver().findElement(by).getText();
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    // ── Element State Checks ───────────────────────────────────
    public boolean isElementDisplayed(By by) {
        return getDriver().findElement(by).isDisplayed();
    }

    // ── Navigation Helpers ─────────────────────────────────────
    public void openUrl(String propertyKey) {
        getDriver().get(PropertiesReader.readKey(propertyKey));
    }

    public void openCareConnectUrl() {
        getDriver().get(PropertiesReader.readKey("url"));
    }

    // ── Page Info ──────────────────────────────────────────────
    public String getPageTitle() {
        return getDriver().getTitle();
    }
}
