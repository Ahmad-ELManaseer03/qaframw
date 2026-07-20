package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.terminology;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object for the Terminologies List page.
 * URL: /terminology-management/terminology/list
 *
 * Live DOM inspection (2026-07-20) reveals:
 * - Header: div with class containing "title" → exact text "Terminologies List"
 * - Data table: p-table (1 instance), with 5 column filter menus
 * - Buttons: Clear, Add, Upload, Show Tracking, Audit Log, Export
 * - Pagination: First/1/2/3/4/5/Next/Last
 * - A "Select Facility" modal appears on first load — must be dismissed
 */
public class TerminologiesPage extends CommonToAllPage {

    @Step("Navigate to Terminologies page and verify it loaded")
    public boolean isTerminologiesPageLoaded() {
        // Wait for sidebar to confirm login is complete before navigating
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        // Navigate directly to the Terminologies page
        getDriver().navigate().to("https://qc.care-connect.health/terminology-management/terminology/list");

        // Handle the "Select Facility" modal/dialog if it appears.
        // The modal has a p-dropdown for facility selection and a "Select Facility" button to confirm.
        try {
            By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
            if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 5)) {
                // A facility modal is present — pick the first facility from the dropdown
                By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
                if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 3)) {
                    org.openqa.selenium.WebElement dropdownTrigger = getDriver().findElement(facilityDropdownTrigger);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dropdownTrigger);

                    By facilityDropdownFirstItem = By.cssSelector("p-dropdownitem li");
                    WaitHelpers.checkVisibility(getDriver(), facilityDropdownFirstItem, 5);
                    org.openqa.selenium.WebElement item = getDriver().findElement(facilityDropdownFirstItem);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                }

                // Click the "Select Facility" button to confirm
                org.openqa.selenium.WebElement confirmBtn = getDriver().findElement(selectFacilityBtn);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", confirmBtn);
            }
        } catch (Exception e) {
            // Ignore if already selected or not present
        }

        // Wait for the page header "Terminologies List" to appear
        By pageHeader = By.xpath("//*[normalize-space(text())='Terminologies List']");
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);

        return getDriver().getCurrentUrl().contains("/terminology-management/terminology/list");
    }
}
