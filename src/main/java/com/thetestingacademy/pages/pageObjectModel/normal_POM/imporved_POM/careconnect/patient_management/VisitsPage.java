package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.patient_management;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

/**
 * VisitsPage
 * 
 * DOM INSPECTION FINDINGS:
 * - Direct navigation to /patient-management/visit causes a 404 or missing facility issue.
 * - Need to navigate via Sidebar: 'Visits' -> 'Visits' (submenu)
 * - Requires a selected facility. A top bar "Select Facility" button is available.
 * - Page header identified via text "Visits List" or "Sort Visits By".
 */
public class VisitsPage extends CommonToAllPage {

    // Locators
    private By visitsSidebarMenu = By.xpath("//span[contains(@class, 'layout-menuitem-text') and text()='Visits']");
    private By visitsSubMenu = By.xpath("//a[contains(@href, '/patient-management/visit')]");
    
    // Facility Selection Locators
    private By selectFacilityBtn = By.xpath("//button[contains(normalize-space(), 'Select Facility')]");
    private By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
    private By facilityDropdownFirstItem = By.cssSelector("p-dropdownitem li");

    // Verification Locator
    private By pageHeader = By.xpath("//*[normalize-space(text())='Visits List']");

    @Step("Select a facility if not already selected")
    public void selectFacilityIfRequired() {
        try {
            if (isElementDisplayed(selectFacilityBtn)) {
                clickElement(selectFacilityBtn);
                WaitHelpers.checkVisibility(getDriver(), facilityDropdownTrigger, 5);
                clickElement(facilityDropdownTrigger);
                WaitHelpers.checkVisibility(getDriver(), facilityDropdownFirstItem, 5);
                java.util.List<WebElement> items = getDriver().findElements(facilityDropdownFirstItem);
                if (!items.isEmpty()) {
                    items.get(0).click();
                }
            }
        } catch (Exception e) {
            // Ignore if already selected or not present
        }
    }

    @Step("Navigate to Visits Page via Sidebar")
    public void navigateToVisits() {
        // Wait for Angular/DOM updates
        try { Thread.sleep(2000); } catch (Exception e) {}
        
        // 1. Click the main 'Visits' sidebar toggle
        try {
            WebElement sidebarEl = getDriver().findElement(visitsSidebarMenu);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", sidebarEl);
            Thread.sleep(500);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", sidebarEl);
            Thread.sleep(1000);
        } catch (Exception e) {}

        // 2. The modal to "Select Facility" pops up when we click a module that requires it. Handle it.
        selectFacilityIfRequired();
        try { Thread.sleep(2000); } catch (Exception e) {}

        // 3. Click the actual Visits submenu
        try {
            WebElement subMenuEl = getDriver().findElement(visitsSubMenu);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", subMenuEl);
            Thread.sleep(500);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", subMenuEl);
        } catch (Exception e) {
            System.out.println("Failed to click submenu: " + e.getMessage());
        }
    }

    @Step("Check if the Visits page header is displayed")
    public boolean isVisitsPageLoaded() {
        try {
            WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);
            return isElementDisplayed(pageHeader);
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get exact header text")
    public String getHeaderText() {
        return getText(pageHeader);
    }
}
