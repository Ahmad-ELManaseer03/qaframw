package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugLabInteractionsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDrugLabInteractions extends CommonToAllTest {

    @Test
    public void testDrugLabInteractionsLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DrugLabInteractionsPage page = new DrugLabInteractionsPage();
        page.navigateToDrugLabInteractions();
        
        Assert.assertTrue(page.isPageLoaded(), "Drug Lab Interactions page did not load correctly");
    }
}
