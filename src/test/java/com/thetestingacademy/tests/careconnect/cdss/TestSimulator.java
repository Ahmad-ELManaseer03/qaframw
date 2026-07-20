package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.SimulatorPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSimulator extends CommonToAllTest {

    @Test
    public void testSimulatorLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        SimulatorPage page = new SimulatorPage();
        page.navigateToSimulator();
        
        Assert.assertTrue(page.isPageLoaded(), "CDSS Simulator page did not load correctly");
    }
}
