package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.GroupsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestGroups extends CommonToAllTest {

    @Description("Verify that the Groups page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testGroupsPageLoad() {
        logger.info("▶ Starting: testGroupsPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Groups page
        GroupsPage groupsPage = new GroupsPage();
        groupsPage.navigateToGroupsPage();

        // 3. Verify page header contains "Group"
        String headerText = groupsPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Group"), "The Groups page header should be visible and contain 'Group'. Actual: " + headerText);
        
        // 4. Verify data table is present
        boolean isTableDisplayed = groupsPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Groups data table should be displayed.");
        
        logger.info("✔ Passed: testGroupsPageLoad");
    }
}
