package com.thetestingacademy.tests.careconnect.ruleEngine;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.ruleEngine.PendingBusinessRulesListPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPendingBusinessRulesList extends CommonToAllTest {

    @Test
    public void testPendingBusinessRulesListLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate and verify
        PendingBusinessRulesListPage pendingBusinessRulesListPage = new PendingBusinessRulesListPage();
        pendingBusinessRulesListPage.navigateToPendingBusinessRulesList();
        
        Assert.assertTrue(pendingBusinessRulesListPage.isPageLoaded(), "Pending Business Rules List page did not load correctly");
    }
}
