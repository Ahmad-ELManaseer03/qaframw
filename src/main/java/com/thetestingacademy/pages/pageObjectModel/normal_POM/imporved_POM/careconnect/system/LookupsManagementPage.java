package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.system;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class LookupsManagementPage extends CommonToAllPage {

    private final By linkLoc = By.xpath("//a[@href='/system/lookup']");
    private final By headerLoc = By.xpath("//div[contains(@class, 'panel-header') and contains(., 'Lookup')]");

    public LookupsManagementPage navigateToLookupsManagement() {
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
                System.out.println("Could not click Lookups Management: " + ex.getMessage());
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
        return currentUrl.contains("/system/lookup");
    }
}
