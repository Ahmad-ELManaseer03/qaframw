package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.AreasPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestAreas extends CommonToAllTest {

    @Description("Verify that the Areas page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testAreasPageLoad() {
        logger.info("▶ Starting: testAreasPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Areas page
        AreasPage areasPage = new AreasPage();
        areasPage.navigateToAreasPage();

        // 3. Verify page header contains "Area"
        String headerText = areasPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Area"), "The Areas page header should be visible and contain 'Area'. Actual: " + headerText);

        // 4. Verify data table is present
        boolean isTableDisplayed = areasPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Areas data table should be displayed.");

        logger.info("✔ Passed: testAreasPageLoad");
    }
}
