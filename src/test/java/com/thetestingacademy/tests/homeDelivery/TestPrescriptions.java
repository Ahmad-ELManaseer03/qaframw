package com.thetestingacademy.tests.homeDelivery;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.homeDelivery.PrescriptionsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestPrescriptions extends CommonToAllTest {

    @Test(priority = 1)
    @Owner("Ahmad")
    @Description("Verify that the Home Delivery Prescriptions page loads correctly")
    public void testHomeDeliveryPrescriptionsPageLoads() {
        com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage loginPage = new com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage();
        loginPage.loginWithCredentials(
                com.thetestingacademy.utils.PropertiesReader.readKey("username"),
                com.thetestingacademy.utils.PropertiesReader.readKey("password")
        );

        PrescriptionsPage page = new PrescriptionsPage();
        Assert.assertTrue(page.isPrescriptionsPageLoaded(), "Home Delivery Prescriptions page failed to load or URL mismatch.");
    }
}
