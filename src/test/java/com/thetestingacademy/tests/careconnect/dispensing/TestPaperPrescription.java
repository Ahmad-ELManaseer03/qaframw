package com.thetestingacademy.tests.careconnect.dispensing;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.dispensing.PaperPrescriptionPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPaperPrescription extends CommonToAllTest {

    @Test(priority = 1)
    @Description("Verify that the Paper Prescription page loads correctly")
    public void testPaperPrescriptionPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        PaperPrescriptionPage page = new PaperPrescriptionPage();
        Assert.assertTrue(page.isPaperPrescriptionPageLoaded(), "Paper Prescription page did not load correctly.");
    }
}
