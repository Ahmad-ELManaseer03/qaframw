package com.thetestingacademy.tests.careconnect.system;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.system.LookupsManagementPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestLookupsManagement extends CommonToAllTest {

    @Test
    public void testLookupsManagementPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        LookupsManagementPage page = new LookupsManagementPage();
        page.navigateToLookupsManagement();
        
        Assert.assertTrue(page.isPageLoaded(), "Lookups Management page did not load correctly");
    }
}
