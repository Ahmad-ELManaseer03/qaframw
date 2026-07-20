package com.thetestingacademy.tests.careconnect.dispensing;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.dispensing.DispensingAuthPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDispensingAuth extends CommonToAllTest {

    @Test(priority = 1)
    @Description("Verify that the Dispensing Auth page loads correctly")
    public void testDispensingAuthPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DispensingAuthPage page = new DispensingAuthPage();
        Assert.assertTrue(page.isDispensingAuthPageLoaded(), "Dispensing Auth page did not load correctly.");
    }
}
