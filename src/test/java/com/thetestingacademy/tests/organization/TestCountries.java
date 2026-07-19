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
        try {
            String headerText = countriesPage.getPageHeaderText();
            Assert.assertEquals(headerText, "Countries", "The Countries page header should be visible.");
            
            // 4. Verify data table is present
            boolean isTableDisplayed = countriesPage.isDataTableDisplayed();
            Assert.assertTrue(isTableDisplayed, "The Countries data table should be displayed.");
            
            logger.info("✔ Passed: testCountriesPageLoad");
        } catch (org.openqa.selenium.TimeoutException e) {
            String source = getDriver().getPageSource();
            logger.error("TimeoutException hit! Page source length: " + (source != null ? source.length() : 0));
            try {
                java.io.File scrFile = ((org.openqa.selenium.TakesScreenshot)getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
                java.io.File destFile = new java.io.File("target/countries-timeout-debug.png");
                org.apache.commons.io.FileUtils.copyFile(scrFile, destFile);
                logger.error("Saved screenshot to: " + destFile.getAbsolutePath());
            } catch (Exception ex) {
                logger.error("Failed to take screenshot: " + ex.getMessage());
            }
            throw e; // rethrow to fail the test officially
        }
    }
}
