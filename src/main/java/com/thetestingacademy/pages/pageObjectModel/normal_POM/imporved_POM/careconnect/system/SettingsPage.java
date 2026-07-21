package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.system;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class SettingsPage extends CommonToAllPage {

    private final By linkLoc = By.xpath("//a[@href='/settings']");
    private final By headerLoc = By.xpath("//*[self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6][contains(., 'Settings')]");

    public SettingsPage navigateToSettings() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        try {
            WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
            WebElement link = getDriver().findElement(linkLoc);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
        } catch (Exception e) {
            try {
                WebElement sysMenu = getDriver().findElement(By.xpath("//a[.//span[normalize-space(text())='System']]"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", sysMenu);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", sysMenu);
                
                WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
                WebElement link = getDriver().findElement(linkLoc);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
            } catch (Exception ex) {
                System.out.println("Could not click Settings: " + ex.getMessage());
                try {
                    WebElement link = getDriver().findElement(linkLoc);
                    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
                } catch (Exception e2) {}
            }
        }

        return this;
    }

    public boolean isPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), headerLoc, 15);
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/settings");
    }
}
