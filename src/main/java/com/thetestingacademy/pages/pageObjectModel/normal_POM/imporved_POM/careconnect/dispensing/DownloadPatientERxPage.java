package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.dispensing;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DownloadPatientERxPage extends CommonToAllPage {

    @Step("Navigate to Download Patient eRx page and verify it loaded")
    public boolean isDownloadPatientERxPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);
        
        // Open Dispensing menu if not open
        try {
            WebElement menu = getDriver().findElement(By.xpath("//a[.//span[text()='Dispensing']]"));
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", menu);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", menu);
        } catch (Exception e) {}

        // Click sidebar link
        By linkLoc = By.xpath("//a[@href='/dispensing-management/dispense']");
        WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
        WebElement link = getDriver().findElement(linkLoc);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);

        // Handle facility modal if it appears
        try {
            By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
            if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 5)) {
                WebElement dt = getDriver().findElement(facilityDropdownTrigger);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dt);
                
                By firstItem = By.cssSelector("p-dropdownitem li");
                WaitHelpers.checkVisibility(getDriver(), firstItem, 5);
                WebElement item = getDriver().findElement(firstItem);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                
                By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
                if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 2)) {
                    WebElement confirmBtn = getDriver().findElement(selectFacilityBtn);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", confirmBtn);
                }
            }
        } catch (Exception e) {}

        // Verify title text
        By titleDiv = By.xpath("//div[contains(@class, 'title') and contains(text(), 'identifiers')]");
        WaitHelpers.checkVisibility(getDriver(), titleDiv, 15);
        
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/dispensing-management/dispense");
    }
}
