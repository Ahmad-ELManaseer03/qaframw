package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.DrugDoseRangePage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;

public class TestDrugDoseRange extends CommonToAllTest {

    @Test
    public void testDrugDoseRangeLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate and verify
        DrugDoseRangePage drugDoseRangePage = new DrugDoseRangePage();
        drugDoseRangePage.navigateToDrugDoseRange();
        
        try {
            Assert.assertTrue(drugDoseRangePage.isPageLoaded(), "Drug Dose Range page did not load correctly");
        } catch (Exception e) {
            try {
                java.nio.file.Files.write(
                    java.nio.file.Paths.get("target/dom_dump.txt"),
                    getDriver().findElement(By.tagName("body")).getAttribute("innerHTML").getBytes()
                );
            } catch (Exception ex) {}
            throw e;
        }
    }
}
