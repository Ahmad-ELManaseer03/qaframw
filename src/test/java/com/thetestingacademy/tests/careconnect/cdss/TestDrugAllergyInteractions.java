package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugAllergyInteractionsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDrugAllergyInteractions extends CommonToAllTest {

    @Test
    public void testDrugAllergyInteractionsLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DrugAllergyInteractionsPage page = new DrugAllergyInteractionsPage();
        page.navigateToDrugAllergyInteractions();
        
        Assert.assertTrue(page.isPageLoaded(), "Drug Allergy Interactions page did not load correctly");
    }
}
