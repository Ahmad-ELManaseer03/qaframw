package com.thetestingacademy.tests.terminology;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.terminology.TerminologiesPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTerminologies extends CommonToAllTest {

    @Test(priority = 1, groups = {"smoke"})
    @Owner("Ahmad")
    @Description("Verify that the Terminologies List page loads correctly with header 'Terminologies List' and exact URL")
    public void testTerminologiesPageLoads() {
        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate and verify the Terminologies page
        TerminologiesPage terminologiesPage = new TerminologiesPage();
        Assert.assertTrue(terminologiesPage.isTerminologiesPageLoaded(),
                "Terminologies List page failed to load — header text 'Terminologies List' not found or URL mismatch.");
    }
}
