package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugSmokingInteractionsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDrugSmokingInteractions extends CommonToAllTest {

    @Test
    public void testDrugSmokingInteractionsLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DrugSmokingInteractionsPage page = new DrugSmokingInteractionsPage();
        page.navigateToDrugSmokingInteractions();
        
        Assert.assertTrue(page.isPageLoaded(), "Drug Smoking Interactions page did not load correctly");
    }
}
