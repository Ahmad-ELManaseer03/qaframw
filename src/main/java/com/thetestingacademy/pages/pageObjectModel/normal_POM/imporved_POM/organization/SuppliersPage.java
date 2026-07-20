package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * SuppliersPage — Page Object for the Organization > Suppliers page.
 *
 * DOM Inspection Findings:
 * - Page Header: <div class="panel-header ..."> Suppliers </div>
 *   Locator: By.cssSelector("div.panel-header")
 * - Search Input: <input type="text" placeholder="Search...">
 *   Locator: By.cssSelector("input[placeholder='Search...']")
 * - Create Button: <button ...><span class="p-button-label">Create</span></button>
 *   Locator: By.xpath("//button[.//span[text()='Create']]")
 * - Data Table: <p-table ... data-pc-name="table">
 *   Locator: By.tagName("p-table")
 */
public class SuppliersPage extends CommonToAllPage {

    // ── Locators ───────────────────────────────────────────────
    private By pageHeader = By.cssSelector("div.panel-header");
    private By searchInput = By.cssSelector("input[placeholder='Search...']");
    private By createButton = By.xpath("//button[.//span[text()='Create']]");
    private By dataTable = By.tagName("p-table");

    // ── Page Actions ───────────────────────────────────────────

    @Step("Navigate directly to the Suppliers page")
    public void navigateToSuppliersPage() {
        // Wait for the 'Patients' sidebar item to be visible,
        // proving the SPA dashboard has fully mounted and authenticated.
        By dashboardSidebarItem = By.xpath("//*[normalize-space(text())='Patients']");
        WaitHelpers.checkVisibility(getDriver(), dashboardSidebarItem, 15);
        
        // Now navigate directly
        getDriver().get("https://qc.care-connect.health/organization/supplier");
    }

    @Step("Get the text of the page header to verify page load")
    public String getPageHeaderText() {
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);
        return getText(pageHeader);
    }

    @Step("Check if the data table is displayed")
    public boolean isDataTableDisplayed() {
        WaitHelpers.checkVisibility(getDriver(), dataTable, 10);
        return getDriver().findElement(dataTable).isDisplayed();
    }
}
