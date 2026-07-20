package com.thetestingacademy.tests.patients;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.patients.PatientsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPatients extends CommonToAllTest {

    @Test(priority = 1, groups = {"smoke"})
    @Owner("Ahmad")
    @Description("Verify that the Patients List page loads correctly")
    public void testPatientsPageLoads() {
        // 1. Login with valid credentials
        com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage loginPage = new com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage();
        loginPage.loginWithCredentials(
                com.thetestingacademy.utils.PropertiesReader.readKey("username"),
                com.thetestingacademy.utils.PropertiesReader.readKey("password")
        );

        PatientsPage patientsPage = new PatientsPage();
        Assert.assertTrue(patientsPage.isPatientsPageLoaded(), "Patients List page failed to load or header text mismatched.");
    }
}
