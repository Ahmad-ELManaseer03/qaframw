package com.thetestingacademy.tests.careconnect.ruleEngine;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.ruleEngine.DispensingManualOverrideListPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDispensingManualOverrideList extends CommonToAllTest {

    @Test
    public void testDispensingManualOverrideListLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate and verify
        DispensingManualOverrideListPage dispensingManualOverrideListPage = new DispensingManualOverrideListPage();
        dispensingManualOverrideListPage.navigateToDispensingManualOverrideList();
        
        Assert.assertTrue(dispensingManualOverrideListPage.isPageLoaded(), "Dispensing Manual Override List page did not load correctly");
    }
}
