package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugDiagnosisContraindicationsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDrugDiagnosisContraindications extends CommonToAllTest {

    @Test
    public void testDrugDiagnosisContraindicationsLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate and verify
        DrugDiagnosisContraindicationsPage page = new DrugDiagnosisContraindicationsPage();
        page.navigateToDrugDiagnosisContraindications();
        
        Assert.assertTrue(page.isPageLoaded(), "Drug Diagnosis Contraindications page did not load correctly");
    }
}
