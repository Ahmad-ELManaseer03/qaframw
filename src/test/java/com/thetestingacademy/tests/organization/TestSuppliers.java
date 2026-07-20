package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.SuppliersPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestSuppliers extends CommonToAllTest {

    @Description("Verify that the Suppliers page loads and displays the data table")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testSuppliersPageLoad() {
        logger.info("▶ Starting: testSuppliersPageLoad");

        // 1. Login with valid credentials
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to Suppliers page
        SuppliersPage suppliersPage = new SuppliersPage();
        suppliersPage.navigateToSuppliersPage();

        // 3. Verify page header contains "Supplier"
        String headerText = suppliersPage.getPageHeaderText();
        Assert.assertTrue(headerText.contains("Supplier"), "The Suppliers page header should be visible and contain 'Supplier'. Actual: " + headerText);
        
        // 4. Verify data table is present
        boolean isTableDisplayed = suppliersPage.isDataTableDisplayed();
        Assert.assertTrue(isTableDisplayed, "The Suppliers data table should be displayed.");
        
        logger.info("✔ Passed: testSuppliersPageLoad");
    }
}
