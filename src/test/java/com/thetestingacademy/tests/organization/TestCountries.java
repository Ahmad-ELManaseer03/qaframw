package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.CountriesPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCountries extends CommonToAllTest {

    @Description("Verify that the Countries page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testCountriesPageLoad() {
        logger.info("▶ Starting: testCountriesPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Countries page
        CountriesPage countriesPage = new CountriesPage();
        countriesPage.navigateToCountriesPage();

        // 3. Verify page header is "Countries"
        String headerText = countriesPage.getPageHeaderText();
        Assert.assertEquals(headerText, "Countries", "The Countries page header should be visible.");

        // 4. Verify data table is present
        boolean isTableDisplayed = countriesPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Countries data table should be displayed.");

        logger.info("✔ Passed: testCountriesPageLoad");
    }
}
