package com.thetestingacademy.tests.careconnect.prescribing;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.prescribing.ExpiredPrescriptionsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestExpiredPrescriptions extends CommonToAllTest {

    @Test(priority = 1)
    @Description("Verify that the Expired Prescriptions page loads correctly")
    public void testExpiredPrescriptionsPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        ExpiredPrescriptionsPage page = new ExpiredPrescriptionsPage();
        Assert.assertTrue(page.isExpiredPrescriptionsPageLoaded(), "Expired Prescriptions page did not load correctly.");
    }
}
