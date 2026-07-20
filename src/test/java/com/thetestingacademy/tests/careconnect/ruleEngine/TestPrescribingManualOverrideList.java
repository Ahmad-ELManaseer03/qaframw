package com.thetestingacademy.tests.careconnect.ruleEngine;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.ruleEngine.PrescribingManualOverrideListPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPrescribingManualOverrideList extends CommonToAllTest {

    @Test
    public void testPrescribingManualOverrideListLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate and verify
        PrescribingManualOverrideListPage prescribingManualOverrideListPage = new PrescribingManualOverrideListPage();
        prescribingManualOverrideListPage.navigateToPrescribingManualOverrideList();
        
        Assert.assertTrue(prescribingManualOverrideListPage.isPageLoaded(), "Prescribing Manual Override List page did not load correctly");
    }
}
