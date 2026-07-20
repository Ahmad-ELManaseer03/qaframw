package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.formulary;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object for the Release Management page.
 * URL: /medications-order/release
 *
 * Live DOM inspection (2026-07-20) reveals:
 * - Page title (CSS div.page-title): "Release Management"
 * - Sub-header TITLE-DIV: "Pending Generics For Release"
 * - Data table: p-table (1 instance)
 * - Buttons: Clear, Actions, Audit Log, Export
 * - Pagination: page 1 only
 */
public class ReleasesPage extends CommonToAllPage {

    @Step("Navigate to Releases page and verify it loaded")
    public boolean isReleasesPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);
        getDriver().navigate().to("https://qc.care-connect.health/medications-order/release");

        try {
            By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
            if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 5)) {
                By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
                if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 3)) {
                    org.openqa.selenium.WebElement dt = getDriver().findElement(facilityDropdownTrigger);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dt);
                    By firstItem = By.cssSelector("p-dropdownitem li");
                    WaitHelpers.checkVisibility(getDriver(), firstItem, 5);
                    org.openqa.selenium.WebElement item = getDriver().findElement(firstItem);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                }
                org.openqa.selenium.WebElement confirmBtn = getDriver().findElement(selectFacilityBtn);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", confirmBtn);
            }
        } catch (Exception e) {}

        // The Releases page has page-title "Release Management"
        By pageHeader = By.xpath("//*[normalize-space(text())='Release Management']");
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);

        return getDriver().getCurrentUrl().contains("/medications-order/release");
    }
}
