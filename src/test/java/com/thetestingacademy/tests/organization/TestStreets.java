package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.StreetsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestStreets extends CommonToAllTest {

    @Description("Verify that the Streets page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testStreetsPageLoad() {
        logger.info("▶ Starting: testStreetsPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Streets page
        StreetsPage streetsPage = new StreetsPage();
        streetsPage.navigateToStreetsPage();

        // 3. Verify page header contains "Street"
        String headerText = streetsPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Street"), "The Streets page header should be visible and contain 'Street'. Actual: " + headerText);

        // 4. Verify data table is present
        boolean isTableDisplayed = streetsPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Streets data table should be displayed.");

        logger.info("✔ Passed: testStreetsPageLoad");
    }
}
