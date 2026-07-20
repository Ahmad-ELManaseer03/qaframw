package com.thetestingacademy.tests.careconnect.system;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.system.SettingsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSettings extends CommonToAllTest {

    @Test
    public void testSettingsPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        SettingsPage page = new SettingsPage();
        page.navigateToSettings();
        
        Assert.assertTrue(page.isPageLoaded(), "Settings page did not load correctly");
    }
}
