package com.thetestingacademy.tests.careconnect.system;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.system.TutorialManagementPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestTutorialManagement extends CommonToAllTest {

    @Test
    public void testTutorialManagementPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        TutorialManagementPage page = new TutorialManagementPage();
        page.navigateToTutorialManagement();
        
        Assert.assertTrue(page.isPageLoaded(), "Tutorial Management page did not load correctly");
    }
}
