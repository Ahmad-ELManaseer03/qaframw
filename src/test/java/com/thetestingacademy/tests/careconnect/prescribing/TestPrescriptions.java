package com.thetestingacademy.tests.careconnect.prescribing;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.prescribing.PrescriptionsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPrescriptions extends CommonToAllTest {

    @Test(priority = 1)
    @Description("Verify that the Prescriptions page loads correctly")
    public void testPrescriptionsPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        PrescriptionsPage page = new PrescriptionsPage();
        Assert.assertTrue(page.isPrescriptionsPageLoaded(), "Prescriptions page did not load correctly.");
    }
}
