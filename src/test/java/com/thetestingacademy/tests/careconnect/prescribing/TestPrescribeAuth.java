package com.thetestingacademy.tests.careconnect.prescribing;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.prescribing.PrescribeAuthPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPrescribeAuth extends CommonToAllTest {

    @Test(priority = 1)
    @Description("Verify that the Prescribe Auth page loads correctly")
    public void testPrescribeAuthPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        PrescribeAuthPage page = new PrescribeAuthPage();
        Assert.assertTrue(page.isPrescribeAuthPageLoaded(), "Prescribe Auth page did not load correctly.");
    }
}
