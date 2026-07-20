package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.DivisionsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDivisions extends CommonToAllTest {

    @Description("Verify that the Divisions page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testDivisionsPageLoad() {
        logger.info("▶ Starting: testDivisionsPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Divisions page
        DivisionsPage divisionsPage = new DivisionsPage();
        divisionsPage.navigateToDivisionsPage();

        // 3. Verify page header contains "Division"
        String headerText = divisionsPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Division"), "The Divisions page header should be visible and contain 'Division'. Actual: " + headerText);
        
        // 4. Verify data table is present
        boolean isTableDisplayed = divisionsPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Divisions data table should be displayed.");
        
        logger.info("✔ Passed: testDivisionsPageLoad");
    }
}
