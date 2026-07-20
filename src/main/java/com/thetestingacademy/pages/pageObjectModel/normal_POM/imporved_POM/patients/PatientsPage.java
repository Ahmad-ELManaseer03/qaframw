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
        // Wait for the 'Patients' sidebar item to be visible and click it (might just open dropdown)
        By dashboardSidebarItem = By.xpath("//*[normalize-space(text())='Patients']");
        WaitHelpers.checkVisibility(getDriver(), dashboardSidebarItem, 15);
        org.openqa.selenium.WebElement el = getDriver().findElement(dashboardSidebarItem);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
        
        try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
        
        // Now click the actual link
        By patientLink = By.xpath("//a[contains(@href, '/patient-management/patient')]");
        org.openqa.selenium.WebElement linkEl = getDriver().findElement(patientLink);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", linkEl);
        
        // Allow the SPA to render the new route
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        
        // Wait for the table to appear, confirming we landed somewhere that loaded data
        WaitHelpers.checkVisibility(getDriver(), table, 15);

        return getDriver().getCurrentUrl().contains("patient");
    }
}
