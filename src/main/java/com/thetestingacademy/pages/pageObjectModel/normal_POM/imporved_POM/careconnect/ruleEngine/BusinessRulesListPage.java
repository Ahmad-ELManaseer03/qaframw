package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.ruleEngine;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class BusinessRulesListPage extends CommonToAllPage {

    // Locators
    private final By linkLoc = By.xpath("//a[@href='/rules-management/rules/workflows']");
    // Verify using page content (like a table header) rather than just the generic body
    private final By headerLoc = By.xpath("//h5[contains(., 'Business Rules List')] | //div[contains(@class, 'header')]//span[contains(text(), 'Business Rules List')] | //th[contains(., 'Rule ID')]");

    public BusinessRulesListPage navigateToBusinessRulesList() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        try {
            WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
            WebElement link = getDriver().findElement(linkLoc);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
        } catch (Exception e) {
            try {
                WebElement menu = getDriver().findElement(By.xpath("//a[.//span[normalize-space(text())='Business Rules Management']]"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", menu);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", menu);
                
                WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
                WebElement link = getDriver().findElement(linkLoc);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
            } catch (Exception ex) {
                System.out.println("Could not click Business Rules List: " + ex.getMessage());
            }
        }

        // Handle Select Facility Modal
        try {
            By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
            if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 5)) {
                WebElement dt = getDriver().findElement(facilityDropdownTrigger);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dt);

                By firstItem = By.cssSelector("p-dropdownitem li");
                WaitHelpers.checkVisibility(getDriver(), firstItem, 5);
                WebElement item = getDriver().findElement(firstItem);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);

                By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
                if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 2)) {
                    WebElement confirmBtn = getDriver().findElement(selectFacilityBtn);
                    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", confirmBtn);
                }
            }
        } catch (Exception e) {}

        return this;
    }

    public boolean isPageLoaded() {
        // Assert specific content exists (allowed to throw on failure)
        WaitHelpers.checkVisibility(getDriver(), headerLoc, 15);
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/rules-management/rules/workflows");
    }
}
