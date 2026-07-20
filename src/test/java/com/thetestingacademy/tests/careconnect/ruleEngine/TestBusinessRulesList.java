package com.thetestingacademy.tests.careconnect.ruleEngine;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.ruleEngine.BusinessRulesListPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestBusinessRulesList extends CommonToAllTest {

    @Test
    public void testBusinessRulesListLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate and verify
        BusinessRulesListPage businessRulesListPage = new BusinessRulesListPage();
        businessRulesListPage.navigateToBusinessRulesList();
        
        Assert.assertTrue(businessRulesListPage.isPageLoaded(), "Business Rules List page did not load correctly");
    }
}
