package com.thetestingacademy.tests.users;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.users.InvitationsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestInvitations extends CommonToAllTest {

    @Description("Verify that the Invitations page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testInvitationsPageLoad() {
        logger.info("▶ Starting: testInvitationsPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Invitations page
        InvitationsPage invitationsPage = new InvitationsPage();
        invitationsPage.navigateToInvitationsPage();

        // 3. Verify page header contains "Invitations"
        String headerText = invitationsPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Invitations"), "The Invitations page header should be visible and contain 'Invitations'. Actual: " + headerText);
        
        // 4. Verify data table is present
        boolean isTableDisplayed = invitationsPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Invitations data table should be displayed.");
        
        logger.info("✔ Passed: testInvitationsPageLoad");
    }
}
