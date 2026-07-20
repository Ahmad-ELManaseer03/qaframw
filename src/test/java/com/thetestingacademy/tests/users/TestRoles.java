package com.thetestingacademy.tests.users;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.users.RolesPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestRoles extends CommonToAllTest {

    @Description("Verify that the Roles page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testRolesPageLoad() {
        logger.info("▶ Starting: testRolesPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Roles page
        RolesPage rolesPage = new RolesPage();
        rolesPage.navigateToRolesPage();

        // 3. Verify page header contains "Roles"
        String headerText = rolesPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Roles"), "The Roles page header should be visible and contain 'Roles'. Actual: " + headerText);
        
        // 4. Verify data table is present
        boolean isTableDisplayed = rolesPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Roles data table should be displayed.");
        
        logger.info("✔ Passed: testRolesPageLoad");
    }
}
