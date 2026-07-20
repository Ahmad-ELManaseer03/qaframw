package com.thetestingacademy.tests.careconnect.dispensing;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.dispensing.DispensationRequestsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDispensationRequests extends CommonToAllTest {

    @Test(priority = 1)
    @Description("Verify that the Dispensation Requests page loads correctly")
    public void testDispensationRequestsPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DispensationRequestsPage page = new DispensationRequestsPage();
        Assert.assertTrue(page.isDispensationRequestsPageLoaded(), "Dispensation Requests page did not load correctly.");
    }
}
