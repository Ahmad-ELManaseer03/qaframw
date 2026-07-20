package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.patients;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object for the Patients List page.
 * URL: /patient-management/patient
 * 
 * Live DOM inspection reveals:
 * - The page header is a div with class "page-title" and text "Patients List"
 * - The data table uses p-table
 */
public class PatientsPage extends CommonToAllPage {

    private By pageHeader = By.cssSelector("div.page-title");
    private By table = By.cssSelector("p-table");

    @Step("Navigate to Patients page and verify it loaded")
    public boolean isPatientsPageLoaded() {
        By patientLink = By.xpath("//a[@href='/patient-management/patient']");
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15); // ensure menu is loaded
        
        java.util.List<org.openqa.selenium.WebElement> links = getDriver().findElements(patientLink);
        for (org.openqa.selenium.WebElement linkEl : links) {
            try {
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", linkEl);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", linkEl);
                break;
            } catch (Exception e) {}
        }
        
        // The modal to "Select Facility" pops up when we click a module that requires it. Handle it.
        try {
            By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
            By facilityDropdownFirstItem = By.cssSelector("p-dropdownitem li");
            if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 3)) {
                org.openqa.selenium.WebElement dropdownTrigger = getDriver().findElement(facilityDropdownTrigger);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dropdownTrigger);
                
                WaitHelpers.checkVisibility(getDriver(), facilityDropdownFirstItem, 5);
                org.openqa.selenium.WebElement item = getDriver().findElement(facilityDropdownFirstItem);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                
                WaitHelpers.waitForElementToBeInvisible(getDriver(), facilityDropdownTrigger, 5);
            }
        } catch (Exception e) {
            // Ignore if already selected or not present
        }

        // Wait for the page header to appear, confirming we landed somewhere that loaded data
        By pageHeader = By.xpath("//*[normalize-space(text())='Patients List']");
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);

        return getDriver().getCurrentUrl().endsWith("/patient-management/patient");
    }
}
