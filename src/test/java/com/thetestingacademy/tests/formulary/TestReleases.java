package com.thetestingacademy.tests.formulary;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.formulary.ReleasesPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestReleases extends CommonToAllTest {

    @Test(priority = 1, groups = {"smoke"})
    @Owner("Ahmad")
    @Description("Verify that the Release Management page loads correctly")
    public void testReleasesPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        ReleasesPage page = new ReleasesPage();
        Assert.assertTrue(page.isReleasesPageLoaded(),
                "Release Management page failed to load — header not found or URL mismatch.");
    }
}
