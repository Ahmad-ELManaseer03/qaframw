package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class CdssTransactionHistoryPage extends CommonToAllPage {

    private final By linkLoc = By.xpath("//a[@href='/cdss-management/cdssTransactionHistory']");
    private final By headerLoc = By.xpath("//*[contains(text(), 'Transaction History List')]");

    public CdssTransactionHistoryPage navigateToCdssTransactionHistory() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        try {
            WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
            WebElement link = getDriver().findElement(linkLoc);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
        } catch (Exception e) {
            try {
                // Top level CDSS
                WebElement cdssMenu = getDriver().findElement(By.xpath("//a[.//span[normalize-space(text())='CDSS']]"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", cdssMenu);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", cdssMenu);
                
                Thread.sleep(500); // Give it a moment to expand

                WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
                WebElement link = getDriver().findElement(linkLoc);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
            } catch (Exception ex) {
                System.out.println("Could not click CDSS Transaction History: " + ex.getMessage());
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
        return currentUrl.contains("/cdss-management/cdssTransactionHistory");
    }
}
