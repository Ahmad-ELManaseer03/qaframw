package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugPregnancyContraindicationsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDrugPregnancyContraindications extends CommonToAllTest {

    @Test
    public void testDrugPregnancyContraindicationsLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DrugPregnancyContraindicationsPage page = new DrugPregnancyContraindicationsPage();
        page.navigateToDrugPregnancyContraindications();
        
        Assert.assertTrue(page.isPageLoaded(), "Drug Pregnancy Contraindications page did not load correctly");
    }
}
