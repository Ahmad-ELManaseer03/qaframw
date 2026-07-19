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
 * TestPatients — Tests for the CareConnect Patients page.
 *
 * Extends CommonToAllTest to leverage automatic driver setup and teardown.
 *
 * Live DOM inspection (2026-07-19) showed:
 *   • After valid login → /dashboard
 *   • Sidebar contains "Patients" link to navigate to the patients view
 *   • Direct /patients URL returns 404
 */
public class TestPatients extends CommonToAllTest {

    @Description("Verify that the Patients page loads after navigating via sidebar")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testPatientSearch() {
        logger.info("▶ Starting: testPatientSearch");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Patients page via sidebar
        PatientsPage patientsPage = new PatientsPage();

        // 3. Verify the patients view loaded
        boolean isLoaded = patientsPage.isPatientsPageLoaded();
        Assert.assertTrue(isLoaded, "The Patients page should be accessible via sidebar navigation.");

        logger.info("✔ Passed: testPatientSearch — Patients page loaded successfully");
    }
}
