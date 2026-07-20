package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.ruleEngine;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class SimulatorPage extends CommonToAllPage {

    // Locators
    private final By linkLoc = By.xpath("//a[@href='/rules-management/simulator']");
    // Verify using page content rather than just the generic body
    private final By headerLoc = By.xpath("//h5[contains(., 'Simulator')] | //div[contains(@class, 'header')]//span[contains(text(), 'Simulator')] | //p[contains(., 'Test and validate business rules')]");

    public SimulatorPage navigateToSimulator() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        try {
            WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
            WebElement link = getDriver().findElement(linkLoc);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
        } catch (Exception e) {
            try {
                WebElement menu = getDriver().findElement(By.xpath("//a[.//span[normalize-space(text())='Rules Tool']]"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", menu);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", menu);
                
                WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
                WebElement link = getDriver().findElement(linkLoc);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
            } catch (Exception ex) {
                System.out.println("Could not click Simulator: " + ex.getMessage());
            }
        }

        return this;
    }

    public boolean isPageLoaded() {
        // Assert specific content exists (allowed to throw on failure)
        WaitHelpers.checkVisibility(getDriver(), headerLoc, 15);
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/rules-management/simulator");
    }
}
