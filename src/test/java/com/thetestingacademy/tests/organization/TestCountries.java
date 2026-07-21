package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.CountriesPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCountries extends CommonToAllTest {

    @Description("Verify full CRUD functionality for the Countries page")
    @Owner("QA Team")
    @Test(priority = 2)
    public void testCountriesCRUD() {
        logger.info("▶ Starting: testCountriesCRUD");

        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        CountriesPage countriesPage = new CountriesPage();
        countriesPage.navigateToCountriesPage();

        // Generate unique test data
        long timestamp = System.currentTimeMillis();
        String uniqueName = "ZZTESTCountry" + timestamp;
        String updatedUniqueName = "ZZTESTCountryUpdated" + timestamp;
        
        String randomArabic = "";
        String arabicChars = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 6; i++) {
            randomArabic += arabicChars.charAt(random.nextInt(arabicChars.length()));
        }
        String strictArabicName = "دولة اختبار " + randomArabic;
        String updatedStrictArabicName = "تحديث دولة " + randomArabic;
        
        try {
            // ── CREATE ──
            logger.info("Step 1: Create Country " + uniqueName);
            countriesPage.openCreateDialog();
            countriesPage.fillAndSaveCountry(strictArabicName, uniqueName, String.valueOf(timestamp).substring(8, 11)); // short 3-char numeric code
            String successMsg = countriesPage.getToastMessage();
            if (!successMsg.toLowerCase().contains("success") && !successMsg.contains("Created")) {
                logger.error("Creation failed with toast: " + successMsg);
                // Extract validation errors
                java.util.List<org.openqa.selenium.WebElement> errors = getDriver().findElements(org.openqa.selenium.By.cssSelector(".p-error, .text-danger, .invalid-feedback, mat-error"));
                for (org.openqa.selenium.WebElement error : errors) {
                    if (error.isDisplayed() && !error.getText().trim().isEmpty()) {
                        logger.error("FORM VALIDATION ERROR: " + error.getText());
                    }
                }
                Assert.fail("Creation toast message should indicate success. Actual: " + successMsg);
            }

            // ── READ & SEARCH ──
            logger.info("Step 2: Search for " + uniqueName);
            countriesPage.searchCountry(uniqueName);
            boolean isFound = countriesPage.isCountryInTable(uniqueName);
            Assert.assertTrue(isFound, "The newly created country should be found in the table.");

            // ── UPDATE ──
            logger.info("Step 3: Update Country " + uniqueName);
            countriesPage.searchCountry(uniqueName);
            countriesPage.clickEditForCountry(uniqueName);
            countriesPage.fillAndSaveCountry(updatedStrictArabicName, updatedUniqueName, String.valueOf(timestamp).substring(8));
            String updateMsg = countriesPage.getToastMessage();
            Assert.assertTrue(updateMsg.toLowerCase().contains("success") || updateMsg.contains("Updated"), "Update toast message should indicate success. Actual: " + updateMsg);

            // Verify update was successful by searching for the updated name
            countriesPage.searchCountry(updatedUniqueName);
            boolean isUpdatedFound = countriesPage.isCountryInTable(updatedUniqueName);
            Assert.assertTrue(isUpdatedFound, "The updated country should be found in the table.");

        } finally {
            // ── DELETE (CLEANUP) ──
            // Always try to clean up, whether we failed in the middle or not
            logger.info("Step 4: Cleanup / Delete");
            try {
                // Try deleting the updated name first, fallback to original if update failed
                countriesPage.searchCountry(updatedUniqueName);
                if (countriesPage.isCountryInTable(updatedUniqueName)) {
                    countriesPage.clickDeleteForCountry(updatedUniqueName);
                    countriesPage.confirmDelete();
                } else {
                    countriesPage.searchCountry(uniqueName);
                    if (countriesPage.isCountryInTable(uniqueName)) {
                        countriesPage.clickDeleteForCountry(uniqueName);
                        countriesPage.confirmDelete();
                    }
                }
                logger.info("✔ Passed: testCountriesCRUD");
            } catch (Exception e) {
                logger.error("❌ Cleanup failed: " + e.getMessage());
            }
        }
    }
}
