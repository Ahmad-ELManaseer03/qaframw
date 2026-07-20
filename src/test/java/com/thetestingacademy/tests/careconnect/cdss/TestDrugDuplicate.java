package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugDuplicatePage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDrugDuplicate extends CommonToAllTest {

    @Test
    public void testDrugDuplicateLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        DrugDuplicatePage page = new DrugDuplicatePage();
        page.navigateToDrugDuplicate();
        
        Assert.assertTrue(page.isPageLoaded(), "Drug Duplicate page did not load correctly");
    }
}
