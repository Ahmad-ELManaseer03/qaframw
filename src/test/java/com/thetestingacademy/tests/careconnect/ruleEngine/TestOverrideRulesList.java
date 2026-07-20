package com.thetestingacademy.tests.careconnect.ruleEngine;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.ruleEngine.OverrideRulesListPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestOverrideRulesList extends CommonToAllTest {

    @Test
    public void testOverrideRulesListLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate and verify
        OverrideRulesListPage overrideRulesListPage = new OverrideRulesListPage();
        overrideRulesListPage.navigateToOverrideRulesList();
        
        Assert.assertTrue(overrideRulesListPage.isPageLoaded(), "Override Rules List page did not load correctly");
    }
}
