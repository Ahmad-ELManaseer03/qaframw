package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.CitiesPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCities extends CommonToAllTest {

    @Description("Verify that the Cities page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testCitiesPageLoad() {
        logger.info("▶ Starting: testCitiesPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Cities page
        CitiesPage citiesPage = new CitiesPage();
        citiesPage.navigateToCitiesPage();

        // 3. Verify page header contains "Cities"
        String headerText = citiesPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Cities"), "The Cities page header should be visible and contain 'Cities'. Actual: " + headerText);

        // 4. Verify data table is present
        boolean isTableDisplayed = citiesPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Cities data table should be displayed.");

        logger.info("✔ Passed: testCitiesPageLoad");
    }
}
