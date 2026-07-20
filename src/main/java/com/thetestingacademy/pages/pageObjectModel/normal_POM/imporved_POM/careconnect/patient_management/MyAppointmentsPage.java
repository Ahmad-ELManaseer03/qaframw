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
    
    // Verification Locator
    private By pageHeader = By.xpath("//*[normalize-space(text())='Appointments List']");

    @Step("Select a facility if modal pops up")
    public void selectFacilityIfRequired() {
        try {
            By drp = By.cssSelector("p-dropdown .p-dropdown-trigger");
            if (getDriver().findElements(drp).size() > 0) {
                WebElement dropdownTrigger = getDriver().findElement(drp);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dropdownTrigger);
                Thread.sleep(1000);
                By drpItem = By.cssSelector("p-dropdownitem li");
                WebElement item = getDriver().findElement(drpItem);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            // Ignore if already selected or not present
        }
    }

    @Step("Navigate to My Appointments Page via Sidebar")
    public void navigateToMyAppointments() {
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

        // 2. Click the actual My Appointments submenu
        try {
            WebElement subMenuEl = getDriver().findElement(myAppointmentsSubMenu);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", subMenuEl);
            Thread.sleep(500);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", subMenuEl);
        } catch (Exception e) {
            System.out.println("Failed to click submenu: " + e.getMessage());
        }

        // 3. The modal to "Select Facility" pops up when we click a module that requires it. Handle it.
        try { Thread.sleep(2000); } catch (Exception e) {}
        selectFacilityIfRequired();
        try { Thread.sleep(2000); } catch (Exception e) {}
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
