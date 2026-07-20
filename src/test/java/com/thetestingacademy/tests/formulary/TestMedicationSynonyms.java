package com.thetestingacademy.tests.formulary;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.formulary.MedicationSynonymsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestMedicationSynonyms extends CommonToAllTest {

    @Test(priority = 1, groups = {"smoke"})
    @Owner("Ahmad")
    @Description("Verify that the Medication Synonyms List page loads correctly")
    public void testMedicationSynonymsPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        MedicationSynonymsPage page = new MedicationSynonymsPage();
        Assert.assertTrue(page.isMedicationSynonymsPageLoaded(),
                "Medication Synonyms List page failed to load — header not found or URL mismatch.");
    }
}
