package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.patient_management;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import io.qameta.allure.Step;

/**
 * MyAppointmentsPage
 * 
 * DOM INSPECTION FINDINGS:
 * - Needs facility selection via modal if not already selected.
 * - Accessed via Sidebar: 'Visits' -> 'My Appointments'
 * - Page header identified via text "Appointments List".
 */
public class MyAppointmentsPage extends CommonToAllPage {

    // Locators
    private By visitsSidebarMenu = By.xpath("//span[contains(@class, 'layout-menuitem-text') and text()='Visits']");
    private By myAppointmentsSubMenu = By.xpath("//a[contains(@href, '/patient-management/visit/my-appointments')]");
    
    // Facility Selection Locators
    private By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
    private By facilityDropdownFirstItem = By.cssSelector("p-dropdownitem li");

    // Verification Locator
    private By pageHeader = By.xpath("//*[normalize-space(text())='Appointments List']");

    @Step("Select a facility if modal pops up")
    public void selectFacilityIfRequired() {
        try {
            if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 3)) {
                WebElement dropdownTrigger = getDriver().findElement(facilityDropdownTrigger);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dropdownTrigger);
                
                WaitHelpers.checkVisibility(getDriver(), facilityDropdownFirstItem, 5);
                WebElement item = getDriver().findElement(facilityDropdownFirstItem);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                
                WaitHelpers.waitForElementToBeInvisible(getDriver(), facilityDropdownTrigger, 5);
            }
        } catch (Exception e) {
            // Ignore if already selected or not present
        }
    }

    @Step("Navigate to My Appointments Page via Sidebar")
    public void navigateToMyAppointments() {
        // Wait for Angular/DOM updates
        WaitHelpers.checkVisibility(getDriver(), myAppointmentsSubMenu, 15);

        // Click the actual My Appointments submenu
        try {
            WebElement subMenuEl = getDriver().findElement(myAppointmentsSubMenu);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", subMenuEl);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", subMenuEl);
        } catch (Exception e) {
            System.out.println("Failed to click submenu: " + e.getMessage());
        }

        // The modal to "Select Facility" pops up when we click a module that requires it. Handle it.
        selectFacilityIfRequired();
    }

    @Step("Check if the My Appointments page header is displayed")
    public boolean isMyAppointmentsPageLoaded() {
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
