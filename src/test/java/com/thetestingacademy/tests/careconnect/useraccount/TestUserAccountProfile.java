package com.thetestingacademy.tests.careconnect.useraccount;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.useraccount.UserAccountProfilePage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestUserAccountProfile extends CommonToAllTest {

    @Test
    public void testUserAccountProfilePageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        UserAccountProfilePage page = new UserAccountProfilePage();
        page.navigateToProfile();
        
        Assert.assertTrue(page.isPageLoaded(), "User Account Profile page did not load correctly");
    }
}
