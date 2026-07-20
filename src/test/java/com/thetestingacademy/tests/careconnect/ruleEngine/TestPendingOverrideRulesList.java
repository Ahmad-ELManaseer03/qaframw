package com.thetestingacademy.tests.careconnect.ruleEngine;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.ruleEngine.PendingOverrideRulesListPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPendingOverrideRulesList extends CommonToAllTest {

    @Test
    public void testPendingOverrideRulesListLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate and verify
        PendingOverrideRulesListPage pendingOverrideRulesListPage = new PendingOverrideRulesListPage();
        pendingOverrideRulesListPage.navigateToPendingOverrideRulesList();
        
        Assert.assertTrue(pendingOverrideRulesListPage.isPageLoaded(), "Pending Override Rules List page did not load correctly");
    }
}
