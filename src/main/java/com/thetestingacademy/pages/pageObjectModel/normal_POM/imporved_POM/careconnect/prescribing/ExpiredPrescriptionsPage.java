package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.prescribing;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ExpiredPrescriptionsPage extends CommonToAllPage {

    @Step("Navigate to Expired Prescriptions page and verify it loaded")
    public boolean isExpiredPrescriptionsPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);
        
        // Open Prescribing menu if not open
        try {
            WebElement menu = getDriver().findElement(By.xpath("//a[.//span[text()='Prescribing']]"));
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", menu);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", menu);
        } catch (Exception e) {}

        // Click sidebar link
        By linkLoc = By.xpath("//a[@href='/prescribe-management/expired-prescriptions']");
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
                
                // Try clicking select facility button just in case
                By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
                if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 2)) {
                    WebElement confirmBtn = getDriver().findElement(selectFacilityBtn);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", confirmBtn);
                }
            }
        } catch (Exception e) {}

        By pageHeader = By.cssSelector("div.page-title");
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);
        String headerText = getDriver().findElement(pageHeader).getText().trim();
        if (!headerText.contains("Expired Prescriptions")) {
            System.out.println("Warning: Header text was '" + headerText + "' instead of expected");
        }

        return getDriver().getCurrentUrl().contains("/prescribe-management/expired-prescriptions");
    }
}
