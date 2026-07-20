package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.formulary;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object for the Generics List page.
 * URL: /medications-order/generics
 *
 * Live DOM inspection (2026-07-20) reveals:
 * - Header: TITLE-DIV with exact text "Generics List"
 * - Data table: p-table (1 instance)
 * - Buttons: Clear, Add, Upload, Show Tracking, Audit Log, Export
 * - Pagination: pages 1-2
 * - No facility modal appeared (already selected from session)
 */
public class GenericsPage extends CommonToAllPage {

    @Step("Navigate to Generics page and verify it loaded")
    public boolean isGenericsPageLoaded() {
        // Wait for sidebar to confirm login is settled
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        // Navigate directly
        getDriver().navigate().to("https://qc.care-connect.health/medications-order/generics");

        // Handle facility modal if it appears
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
        } catch (Exception e) {
            // Ignore
        }

        // Wait for header
        By pageHeader = By.xpath("//*[normalize-space(text())='Generics List']");
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);

        return getDriver().getCurrentUrl().contains("/medications-order/generics");
    }
}
