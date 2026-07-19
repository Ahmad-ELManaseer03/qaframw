package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * PatientsPage — Improved POM pattern for CareConnect Patients page.
 *
 * This page object extends CommonToAllPage to inherit reusable actions.
 * Locators are defined as private By using semantic Angular attributes
 * or custom data attributes whenever possible.
 */
public class PatientsPage extends CommonToAllPage {

    // ── Locators ───────────────────────────────────────────────
    // Using stable semantic locators according to Locators Strategy
    private By pageHeader       = By.cssSelector("h1.patients-title");
    private By addPatientButton = By.cssSelector("button[data-test-id='add-patient-btn']");
    private By searchInput      = By.cssSelector("input[formControlName='searchPatient']");
    private By patientRow       = By.cssSelector("tr.patient-row");

    // ── Page Actions ───────────────────────────────────────────

    @Step("Verify if Patients page is loaded by checking the header visibility")
    public boolean isPatientsPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);
        return isElementDisplayed(pageHeader);
    }

    @Step("Click on 'Add Patient' button")
    public void clickAddPatient() {
        clickElement(addPatientButton);
    }

    @Step("Search for a patient using name/ID: {0}")
    public void searchPatient(String patientIdentifier) {
        clearAndEnterInput(searchInput, patientIdentifier);
        // Depending on the SPA, you might wait for a loading spinner to disappear here.
        // For now, ensuring the input is taken.
    }

    @Step("Get the text of the first patient row in the results table")
    public String getFirstPatientRowText() {
        WaitHelpers.checkVisibility(getDriver(), patientRow, 10);
        return getText(patientRow);
    }
}
