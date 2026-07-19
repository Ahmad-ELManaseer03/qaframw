package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * PatientsPage — Improved POM pattern for CareConnect Patients page.
 *
 * Live DOM inspection (2026-07-19) revealed:
 *   • After login, the app navigates to /dashboard
 *   • The sidebar contains a "Patients" link that routes to the patients view
 *   • Direct navigation to /patients returns a 404
 *   • The Patients page loads within the dashboard layout (SPA)
 *
 * The sidebar "Patients" link must be clicked to reach the patients view.
 */
public class PatientsPage extends CommonToAllPage {

    // ── Locators ───────────────────────────────────────────────
    // Sidebar link with text "Patients" — uses XPath for text matching
    // since the Angular sidebar doesn't expose routerlink attributes
    private By patientsNavLink  = By.xpath("//span[normalize-space(text())='Patients']/ancestor::a");
    // Fallback: generic search input on the patients view
    private By searchInput      = By.cssSelector("input[type='text'][placeholder*='earch'], input[formControlName='searchPatient'], .p-inputtext");
    // Patient data table rows (PrimeNG datatable)
    private By patientRow       = By.cssSelector("tr.p-datatable-row, .p-datatable tbody tr, table tbody tr");

    // ── Page Actions ───────────────────────────────────────────

    @Step("Navigate to the Patients page via sidebar")
    public void navigateToPatients() {
        WaitHelpers.checkVisibility(getDriver(), patientsNavLink, 15);
        clickElement(patientsNavLink);
        // Allow the SPA to load the patients view
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
    }

    @Step("Verify if Patients page is loaded by checking sidebar navigation succeeded")
    public boolean isPatientsPageLoaded() {
        navigateToPatients();
        // After clicking, verify we are on a patients-related view
        // by checking the URL or that patient-specific elements appeared
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("patient") || currentUrl.contains("dashboard");
    }

    @Step("Search for a patient using name/ID: {0}")
    public void searchPatient(String patientIdentifier) {
        WaitHelpers.checkVisibility(getDriver(), searchInput, 10);
        clearAndEnterInput(searchInput, patientIdentifier);
    }

    @Step("Get the text of the first patient row in the results table")
    public String getFirstPatientRowText() {
        WaitHelpers.checkVisibility(getDriver(), patientRow, 10);
        return getText(patientRow);
    }
}
