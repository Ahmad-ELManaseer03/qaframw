package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugDrugInteractionsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDrugDrugInteractions extends CommonToAllTest {

    @Test
    public void testDrugDrugInteractionsLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DrugDrugInteractionsPage page = new DrugDrugInteractionsPage();
        page.navigateToDrugDrugInteractions();
        
        Assert.assertTrue(page.isPageLoaded(), "Drug Drug Interactions page did not load correctly");
    }
}
