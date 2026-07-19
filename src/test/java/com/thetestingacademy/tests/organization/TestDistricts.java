package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.DistrictsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDistricts extends CommonToAllTest {

    @Description("Verify that the Districts page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testDistrictsPageLoad() {
        logger.info("▶ Starting: testDistrictsPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Districts page
        DistrictsPage districtsPage = new DistrictsPage();
        districtsPage.navigateToDistrictsPage();

        // 3. Verify page header contains "District"
        String headerText = districtsPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("District"), "The Districts page header should be visible and contain 'District'. Actual: " + headerText);

        // 4. Verify data table is present
        boolean isTableDisplayed = districtsPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Districts data table should be displayed.");

        logger.info("✔ Passed: testDistrictsPageLoad");
    }
}
