package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.CountriesPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("CareConnect QA Automation")
@Feature("Organization Management")
@Story("Country CRUD Lifecycle")
/**
 * TestCountries — End-to-end CRUD verification for the Countries module.
 * 
 * This is the sole remaining functional test in the framework, serving as a 
 * standalone, maintainable template for future CareConnect UI automation.
 * It demonstrates:
 * 1. Safe WebDriver handling via CommonToAllTest.
 * 2. Proper Page Object initialization (LoginPage, CountriesPage).
 * 3. Step-by-step reporting via Allure annotations.
 * 4. Robust explicit waits replacing brittle Thread.sleep() calls.
 */
public class TestCountries extends CommonToAllTest {

    @Attachment(value = "Screenshot: {name}", type = "image/png")
    private byte[] takeScreenshot(String name) {
        return ((org.openqa.selenium.TakesScreenshot) getDriver()).getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
    }

    @Description("Verify full CRUD functionality and validations for the Countries page")
    @Owner("QA Team")
    @Test(priority = 2)
    public void testCountriesCRUD() {
        logger.info("▶ Starting: testCountriesCRUD");

        CountriesPage countriesPage = new CountriesPage();

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
        String code = String.valueOf(timestamp).substring(8, 11);
        String updatedCode = String.valueOf(timestamp).substring(9, 12);

        // Precondition
        Allure.step("Step 1: Login and Navigate | Expected: Countries page is loaded successfully", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.loginWithCredentials(
                    PropertiesReader.readKey("username"),
                    PropertiesReader.readKey("password")
            );
            countriesPage.navigateToCountriesPage();
            Assert.assertTrue(countriesPage.isDataTableDisplayed(), "Data table should be visible after navigation.");
            takeScreenshot("Step1_Precondition");
        });

        // Validation - Empty fields
        Allure.step("Step 2: Submit empty creation form | Expected: Validation errors are displayed", () -> {
            countriesPage.openCreateDialog();
            countriesPage.clickSave();
            boolean hasErrors = countriesPage.areValidationErrorsDisplayed();
            Assert.assertTrue(hasErrors, "Validation error messages should be displayed for empty required fields.");
            countriesPage.closeDialog();
            takeScreenshot("Step2_Validation_EmptyFields");
        });

        // Create
        Allure.step("Step 3: Create country with valid data | Expected: Success toast is displayed", () -> {
            countriesPage.openCreateDialog();
            countriesPage.fillAndSaveCountry(strictArabicName, uniqueName, code);
            String successMsg = countriesPage.getToastMessage();
            Assert.assertTrue(successMsg.toLowerCase().contains("success") || successMsg.contains("Created"), 
                    "Creation toast message should indicate success. Actual: " + successMsg);
            takeScreenshot("Step3_Create_Valid");
        });

        // Search newly created
        Allure.step("Step 4: Search for the new record | Expected: Record appears in the table", () -> {
            countriesPage.searchCountry(uniqueName);
            boolean isFound = countriesPage.isCountryInTable(uniqueName);
            Assert.assertTrue(isFound, "The newly created country should be found in the table.");
            takeScreenshot("Step4_Search_NewRecord");
        });

        // Search non-existent
        Allure.step("Step 5: Search for a non-existent term | Expected: Table displays an empty state", () -> {
            String invalidSearchTerm = "NONEXISTENT_COUNTRY_" + timestamp;
            countriesPage.searchCountry(invalidSearchTerm);
            boolean isEmptyState = countriesPage.isTableEmptyStateDisplayed();
            Assert.assertTrue(isEmptyState, "Table should display an empty state for non-existent search terms.");
            takeScreenshot("Step5_Search_NonExistent");
        });

        // Update
        Allure.step("Step 6: Update the country details | Expected: Update success toast is displayed", () -> {
            countriesPage.searchCountry(uniqueName);
            countriesPage.clickEditForCountry(uniqueName);
            countriesPage.fillAndSaveCountry(updatedStrictArabicName, updatedUniqueName, updatedCode);
            String updateMsg = countriesPage.getToastMessage();
            Assert.assertTrue(updateMsg.toLowerCase().contains("success") || updateMsg.contains("Updated"), 
                    "Update toast message should indicate success. Actual: " + updateMsg);
            takeScreenshot("Step6_Update_Record");
        });

        // Verify Update
        Allure.step("Step 7: Search for the updated record | Expected: Updated record appears in the table", () -> {
            countriesPage.searchCountry(updatedUniqueName);
            boolean isUpdatedFound = countriesPage.isCountryInTable(updatedUniqueName);
            Assert.assertTrue(isUpdatedFound, "The updated country should be found in the table.");
            takeScreenshot("Step7_Verify_Update");
        });

        // Cleanup
        Allure.step("Step 8: Delete the record (cleanup) | Expected: Record is removed successfully", () -> {
            try {
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
                takeScreenshot("Step8_Cleanup_Completed");
            } catch (Exception e) {
                takeScreenshot("Step8_Cleanup_Failed");
                Assert.fail("Cleanup failed: " + e.getMessage());
            }
        });
    }
}
