package com.thetestingacademy.tests.patient_management;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.patient_management.VisitsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestVisits extends CommonToAllTest {

    @Test(description = "Verify that the Visits page loads correctly")
    public void testVisitsPageLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Visits
        VisitsPage visitsPage = new VisitsPage();
        visitsPage.navigateToVisits();

        // 3. Assert page loaded
        boolean isLoaded = visitsPage.isVisitsPageLoaded();
        Assert.assertTrue(isLoaded, "Visits page header should be visible");
        Assert.assertEquals(visitsPage.getHeaderText(), "Visits List", "Header text should match 'Visits List'");
    }
}
