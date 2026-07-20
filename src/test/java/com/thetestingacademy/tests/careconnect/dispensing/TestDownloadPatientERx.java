package com.thetestingacademy.tests.careconnect.dispensing;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.dispensing.DownloadPatientERxPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDownloadPatientERx extends CommonToAllTest {

    @Test(priority = 1)
    @Description("Verify that the Download Patient eRx page loads correctly")
    public void testDownloadPatientERxPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DownloadPatientERxPage page = new DownloadPatientERxPage();
        Assert.assertTrue(page.isDownloadPatientERxPageLoaded(), "Download Patient eRx page did not load correctly.");
    }
}
