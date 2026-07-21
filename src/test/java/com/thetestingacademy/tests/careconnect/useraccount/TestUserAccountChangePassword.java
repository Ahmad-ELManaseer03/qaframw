package com.thetestingacademy.tests.careconnect.useraccount;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.useraccount.UserAccountChangePasswordPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestUserAccountChangePassword extends CommonToAllTest {

    @Test
    public void testUserAccountChangePasswordPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        UserAccountChangePasswordPage page = new UserAccountChangePasswordPage();
        page.navigateToChangePassword();
        
        Assert.assertTrue(page.isPageLoaded(), "User Account Change Password page did not load correctly");
    }
}
