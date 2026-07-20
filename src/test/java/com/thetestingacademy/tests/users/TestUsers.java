package com.thetestingacademy.tests.users;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.users.UsersPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestUsers extends CommonToAllTest {

    @Description("Verify that the Users page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testUsersPageLoad() {
        logger.info("▶ Starting: testUsersPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Users page
        UsersPage usersPage = new UsersPage();
        usersPage.navigateToUsersPage();

        // 3. Verify page header contains "Users"
        String headerText = usersPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Users"), "The Users page header should be visible and contain 'Users'. Actual: " + headerText);
        
        // 4. Verify data table is present
        boolean isTableDisplayed = usersPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Users data table should be displayed.");
        
        logger.info("✔ Passed: testUsersPageLoad");
    }
}
