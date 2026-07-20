package com.thetestingacademy.tests.careconnect.dispensing;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.dispensing.ClaimsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClaims extends CommonToAllTest {

    @Test(priority = 1)
    @Description("Verify that the Claims page loads correctly")
    public void testClaimsPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        ClaimsPage page = new ClaimsPage();
        Assert.assertTrue(page.isClaimsPageLoaded(), "Claims page did not load correctly.");
    }
}
