package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugGenderInteractionsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDrugGenderInteractions extends CommonToAllTest {

    @Test
    public void testDrugGenderInteractionsLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DrugGenderInteractionsPage page = new DrugGenderInteractionsPage();
        page.navigateToDrugGenderInteractions();
        
        Assert.assertTrue(page.isPageLoaded(), "Drug Gender Interactions page did not load correctly");
    }
}
