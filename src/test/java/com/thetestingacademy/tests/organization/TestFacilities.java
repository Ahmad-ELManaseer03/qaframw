package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.FacilitiesPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestFacilities extends CommonToAllTest {

    @Description("Verify that the Facilities page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testFacilitiesPageLoad() {
        logger.info("▶ Starting: testFacilitiesPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Facilities page
        FacilitiesPage facilitiesPage = new FacilitiesPage();
        facilitiesPage.navigateToFacilitiesPage();

        // 3. Verify page header contains "Facilities"
        String headerText = facilitiesPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Facilities"), "The Facilities page header should be visible and contain 'Facilities'. Actual: " + headerText);
        
        // 4. Verify data table is present
        boolean isTableDisplayed = facilitiesPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Facilities data table should be displayed.");
        
        logger.info("✔ Passed: testFacilitiesPageLoad");
    }
}
