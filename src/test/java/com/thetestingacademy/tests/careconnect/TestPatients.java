package com.thetestingacademy.tests.careconnect;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.PatientsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestPatients — Sample tests for the CareConnect Patients page.
 *
 * Extends CommonToAllTest to leverage automatic driver setup and teardown.
 */
public class TestPatients extends CommonToAllTest {

    @Description("Verify that the Patients page loads and search functionality returns relevant results")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testPatientSearch() {
        logger.info("▶ Starting: testPatientSearch");

        // 1. Pre-requisite: Login to access the Patients page
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Instantiate the PatientsPage object
        PatientsPage patientsPage = new PatientsPage();
        
        // 3. Verify the page is fully loaded before interacting
        boolean isLoaded = patientsPage.isPatientsPageLoaded();
        Assert.assertTrue(isLoaded, "The Patients page header should be visible indicating a successful load.");

        // 4. Perform an action (e.g., search for a patient)
        String searchName = PropertiesReader.readKey("search_patient_name");
        patientsPage.searchPatient(searchName);

        // 5. Retrieve state from the Page Object and Assert
        String firstRowText = patientsPage.getFirstPatientRowText();
        
        logger.info("First row result: " + firstRowText);

        assertThat(firstRowText)
                .as("The search result row should contain the searched patient name")
                .isNotNull()
                .contains(searchName);

        logger.info("✔ Passed: testPatientSearch");
    }
}
