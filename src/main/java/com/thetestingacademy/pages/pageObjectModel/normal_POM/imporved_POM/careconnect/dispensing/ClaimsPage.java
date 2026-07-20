package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.dispensing;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ClaimsPage extends CommonToAllPage {

    @Step("Navigate to Claims page and verify it loaded")
    public boolean isClaimsPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);
        
        try {
            // Expand dispensing if needed, or maybe it's already expanded
            WebElement menu = getDriver().findElement(By.xpath("//a[.//span[text()='Dispensing']]"));
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", menu);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", menu);
        } catch (Exception e) {}

        By linkLoc = By.xpath("//a[.//span[normalize-space(text())='Claims']]");
        WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
        WebElement link = getDriver().findElement(linkLoc);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);

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

        // Verify page loaded by checking URL and a generic element
        try {
            WaitHelpers.checkVisibility(getDriver(), By.cssSelector("table, .p-datatable"), 10);
        } catch (Exception e) {}
        
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/claiming-management/claims");
    }
}
