package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * CountriesPage — Page Object for the Organization > Countries page.
 *
 * DOM Inspection Findings (2026-07-19):
 * - Page Header: <h1 class="header pb-4 pt-4">Countries</h1>
 *   Locator: By.cssSelector("h1.header") (Stable because it uses a specific semantic tag and class)
 * - Search Input: <input type="text" placeholder="Search...">
 *   Locator: By.cssSelector("input[placeholder='Search...']") (Stable property)
 * - Create Button: <button ...><span class="p-button-label">Create</span></button>
 *   Locator: By.xpath("//button[.//span[text()='Create']]") (Matches button text reliably)
 * - Data Table: <p-table ... data-pc-name="table">
 *   Locator: By.tagName("p-table") (PrimeNG specific tag)
 */
public class CountriesPage extends CommonToAllPage {

    // ── Locators ───────────────────────────────────────────────
    private By pageHeader = By.cssSelector("h1.header");
    private By searchInput = By.cssSelector("input[placeholder='Search...']");
    private By createButton = By.xpath("//button[.//span[text()='Create']]");
    private By dataTable = By.tagName("p-table");

    private By countriesNavLink = By.xpath("//*[normalize-space(text())='Countries']");

    // ── Page Actions ───────────────────────────────────────────

    @Step("Navigate directly to the Countries page")
    public void navigateToCountriesPage() {
        // Concrete post-login check: wait for the 'Patients' sidebar item to be visible,
        // which proves the SPA dashboard has fully mounted and authenticated.
        By dashboardSidebarItem = By.xpath("//*[normalize-space(text())='Patients']");
        WaitHelpers.checkVisibility(getDriver(), dashboardSidebarItem, 15);
        
        // Now navigate directly
        getDriver().get("https://qc.care-connect.health/organization/country");
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
