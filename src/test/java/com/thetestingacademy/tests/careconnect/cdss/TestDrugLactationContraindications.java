package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugLactationContraindicationsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDrugLactationContraindications extends CommonToAllTest {

    @Test
    public void testDrugLactationContraindicationsLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DrugLactationContraindicationsPage page = new DrugLactationContraindicationsPage();
        page.navigateToDrugLactationContraindications();
        
        Assert.assertTrue(page.isPageLoaded(), "Drug Lactation Contraindications page did not load correctly");
    }
}
