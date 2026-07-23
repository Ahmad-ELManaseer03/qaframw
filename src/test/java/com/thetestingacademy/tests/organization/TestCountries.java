package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.CountriesPage;
import com.thetestingacademy.utils.AllureEvidence;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("CareConnect QA Automation")
@Feature("Organization Management")
public class TestCountries extends CommonToAllTest {

    private String generateRandomArabic(int length) {
        String arabicChars = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
        java.util.Random random = new java.util.Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(arabicChars.charAt(random.nextInt(arabicChars.length())));
        }
        return sb.toString();
    }

    private void performLoginAndNavigate(CountriesPage countriesPage) {
        Allure.step("Login and Navigate → Expected: Countries page is loaded successfully", () -> {
            LoginPage loginPage = new LoginPage();
            loginPage.loginWithCredentials(
                    PropertiesReader.readKey("username"),
                    PropertiesReader.readKey("password")
            );
            countriesPage.navigateToCountriesPage();
            Assert.assertTrue(countriesPage.isDataTableDisplayed(), "Data table should be visible after navigation.");
            AllureEvidence.attachScreenshot(getDriver(), "Navigation_Success");
        });
    }

    private void cleanupCountry(CountriesPage countriesPage, String uniqueName) {
        Allure.step("Clean up test data → Expected: Created record is removed", () -> {
            Allure.parameter("Cleanup Target: " + uniqueName, uniqueName);
            try {
                countriesPage.searchCountry(uniqueName);
                if (countriesPage.isCountryInTable(uniqueName)) {
                    countriesPage.clickDeleteForCountry(uniqueName);
                    countriesPage.confirmDelete();
                }
                AllureEvidence.attachScreenshot(getDriver(), "Cleanup_Completed");
            } catch (Exception e) {
                AllureEvidence.attachScreenshot(getDriver(), "Cleanup_Failed");
                System.out.println("Cleanup failed or record already deleted: " + e.getMessage());
            }
        });
    }

    @Test(priority = 1, groups = {"organization", "countries", "validation", "countries.validation", "regression"})
    @Story("Country Form Validation")
    @Description("Verify that validation errors are displayed when submitting an empty country creation form")
    @Owner("QA Team")
    public void testCountriesValidation_EmptyFieldsShowErrors() {
        logger.info("▶ Starting: testCountriesValidation_EmptyFieldsShowErrors");
        CountriesPage countriesPage = new CountriesPage();
        performLoginAndNavigate(countriesPage);

        Allure.step("Submit empty creation form → Expected: Validation errors are displayed", () -> {
            countriesPage.openCreateDialog();
            countriesPage.clickSave();
            boolean hasErrors = countriesPage.areValidationErrorsDisplayed();
            Allure.parameter("Validation Errors Present", String.valueOf(hasErrors));
            Assert.assertTrue(hasErrors, "Validation error messages should be displayed for empty required fields.");
            AllureEvidence.attachScreenshot(getDriver(), "Validation_Errors_Displayed");
            countriesPage.closeDialog();
        });
    }

    @Test(priority = 2, groups = {"organization", "countries", "create", "countries.create", "regression"})
    @Story("Country Creation")
    @Description("Verify that a user can successfully create a new country with valid data")
    @Owner("QA Team")
    public void testCountriesCreate_ValidDataSucceeds() {
        logger.info("▶ Starting: testCountriesCreate_ValidDataSucceeds");
        CountriesPage countriesPage = new CountriesPage();
        performLoginAndNavigate(countriesPage);

        long timestamp = System.currentTimeMillis();
        String uniqueName = "ZZTESTCountry" + timestamp;
        String strictArabicName = "دولة اختبار " + generateRandomArabic(6);
        String code = String.valueOf(timestamp).substring(8, 11);

        try {
            Allure.step("Fill and submit country details → Expected: Success toast is displayed", () -> {
                Allure.parameter("Arabic Name", strictArabicName);
                Allure.parameter("English Name", uniqueName);
                Allure.parameter("Code", code);
                
                countriesPage.openCreateDialog();
                countriesPage.fillAndSaveCountry(strictArabicName, uniqueName, code);
                
                String successMsg = countriesPage.getToastMessage();
                Allure.parameter("Create Toast Message", successMsg);
                Assert.assertTrue(successMsg.toLowerCase().contains("success") || successMsg.contains("Created"), 
                        "Success toast message should be displayed.");
                AllureEvidence.attachScreenshot(getDriver(), "Create_Success_Toast");
            });

            Allure.step("Verify the created record appears in the table → Expected: Record is found", () -> {
                Allure.parameter("Create Search Term", uniqueName);
                countriesPage.searchCountry(uniqueName);
                boolean isFound = countriesPage.isCountryInTable(uniqueName);
                Allure.parameter("Create Record Found", String.valueOf(isFound));
                Assert.assertTrue(isFound, "The newly created country should be found in the table.");
                AllureEvidence.attachScreenshot(getDriver(), "Create_Record_Found");
            });
        } finally {
            cleanupCountry(countriesPage, uniqueName);
        }
    }

    @Test(priority = 3, groups = {"organization", "countries", "search", "countries.search", "regression"})
    @Story("Country Search & Filtering")
    @Description("Verify that searching for an existing country returns results and searching for a non-existent country shows an empty state")
    @Owner("QA Team")
    public void testCountriesSearch_FindsExistingAndHandlesEmptyState() {
        logger.info("▶ Starting: testCountriesSearch_FindsExistingAndHandlesEmptyState");
        CountriesPage countriesPage = new CountriesPage();
        performLoginAndNavigate(countriesPage);

        long timestamp = System.currentTimeMillis();
        String uniqueName = "ZZTESTSearch" + timestamp;
        String strictArabicName = "دولة بحث " + generateRandomArabic(6);
        String code = String.valueOf(timestamp).substring(8, 11);

        try {
            Allure.step("Precondition: Create country for search → Expected: Country is created successfully", () -> {
                countriesPage.openCreateDialog();
                countriesPage.fillAndSaveCountry(strictArabicName, uniqueName, code);
                countriesPage.getToastMessage(); // Wait for toast
                AllureEvidence.attachScreenshot(getDriver(), "Search_Precondition_Created");
            });

            Allure.step("Search for an existing term → Expected: Record appears in the table", () -> {
                Allure.parameter("Valid Search Term", uniqueName);
                countriesPage.searchCountry(uniqueName);
                boolean isFound = countriesPage.isCountryInTable(uniqueName);
                Allure.parameter("Valid Record Found", String.valueOf(isFound));
                Assert.assertTrue(isFound, "The created country should be found in the table.");
                AllureEvidence.attachScreenshot(getDriver(), "Search_Existing_Found");
            });

            Allure.step("Search for a non-existent term → Expected: Table displays an empty state", () -> {
                String invalidSearchTerm = "NONEXISTENT_" + timestamp;
                Allure.parameter("Invalid Search Term", invalidSearchTerm);
                countriesPage.searchCountry(invalidSearchTerm);
                boolean isEmptyState = countriesPage.isTableEmptyStateDisplayed();
                Allure.parameter("Empty State Displayed", String.valueOf(isEmptyState));
                Assert.assertTrue(isEmptyState, "Table should display an empty state for non-existent search terms.");
                AllureEvidence.attachScreenshot(getDriver(), "Search_Empty_State");
            });
        } finally {
            cleanupCountry(countriesPage, uniqueName);
        }
    }

    @Test(priority = 4, groups = {"organization", "countries", "update", "countries.update", "regression"})
    @Story("Country Update")
    @Description("Verify that a user can successfully update an existing country record")
    @Owner("QA Team")
    public void testCountriesUpdate_PersistsChanges() {
        logger.info("▶ Starting: testCountriesUpdate_PersistsChanges");
        CountriesPage countriesPage = new CountriesPage();
        performLoginAndNavigate(countriesPage);

        long timestamp = System.currentTimeMillis();
        String uniqueName = "ZZTESTUpdate" + timestamp;
        String updatedUniqueName = "ZZTESTUpdateEd" + timestamp;
        String strictArabicName = "دولة تحديث " + generateRandomArabic(6);
        String updatedStrictArabicName = "تحديث دولة " + generateRandomArabic(6);
        String code = String.valueOf(timestamp).substring(8, 11);
        String updatedCode = String.valueOf(timestamp).substring(9, 12);

        try {
            Allure.step("Precondition: Create country for update → Expected: Country is created successfully", () -> {
                countriesPage.openCreateDialog();
                countriesPage.fillAndSaveCountry(strictArabicName, uniqueName, code);
                countriesPage.getToastMessage();
                AllureEvidence.attachScreenshot(getDriver(), "Update_Precondition_Created");
            });

            Allure.step("Update the country details → Expected: Update success toast is displayed", () -> {
                Allure.parameter("Old English Name", uniqueName);
                Allure.parameter("New English Name", updatedUniqueName);
                Allure.parameter("New Arabic Name", updatedStrictArabicName);
                
                countriesPage.searchCountry(uniqueName);
                countriesPage.clickEditForCountry(uniqueName);
                countriesPage.fillAndSaveCountry(updatedStrictArabicName, updatedUniqueName, updatedCode);
                
                String updateMsg = countriesPage.getToastMessage();
                Allure.parameter("Update Toast Message", updateMsg);
                Assert.assertTrue(updateMsg.toLowerCase().contains("success") || updateMsg.contains("Updated"), 
                        "Update toast message should indicate success.");
                AllureEvidence.attachScreenshot(getDriver(), "Update_Success_Toast");
            });

            Allure.step("Search for the updated record → Expected: Updated record appears in the table", () -> {
                Allure.parameter("Update Search Term", updatedUniqueName);
                countriesPage.searchCountry(updatedUniqueName);
                boolean isUpdatedFound = countriesPage.isCountryInTable(updatedUniqueName);
                Allure.parameter("Updated Record Found", String.valueOf(isUpdatedFound));
                Assert.assertTrue(isUpdatedFound, "The updated country should be found in the table.");
                AllureEvidence.attachScreenshot(getDriver(), "Update_Record_Found");
            });
        } finally {
            cleanupCountry(countriesPage, updatedUniqueName);
            cleanupCountry(countriesPage, uniqueName); // Fallback if update failed
        }
    }

    @Test(priority = 5, groups = {"organization", "countries", "delete", "countries.delete", "regression"})
    @Story("Country Deletion")
    @Description("Verify that a user can successfully delete an existing country record")
    @Owner("QA Team")
    public void testCountriesDelete_RemovesRecord() {
        logger.info("▶ Starting: testCountriesDelete_RemovesRecord");
        CountriesPage countriesPage = new CountriesPage();
        performLoginAndNavigate(countriesPage);

        long timestamp = System.currentTimeMillis();
        String uniqueName = "ZZTESTDelete" + timestamp;
        String strictArabicName = "دولة حذف " + generateRandomArabic(6);
        String code = String.valueOf(timestamp).substring(8, 11);

        Allure.step("Precondition: Create country for deletion → Expected: Country is created successfully", () -> {
            countriesPage.openCreateDialog();
            countriesPage.fillAndSaveCountry(strictArabicName, uniqueName, code);
            countriesPage.getToastMessage();
            AllureEvidence.attachScreenshot(getDriver(), "Delete_Precondition_Created");
        });

        Allure.step("Delete the record → Expected: Record is removed successfully and no longer found", () -> {
            Allure.parameter("Country to delete", uniqueName);
            
            countriesPage.searchCountry(uniqueName);
            countriesPage.clickDeleteForCountry(uniqueName);
            countriesPage.confirmDelete();
            
            String toastMsg = countriesPage.getToastMessage();
            Allure.parameter("Delete Toast Message", toastMsg);
            
            countriesPage.searchCountry(uniqueName);
            boolean isEmpty = countriesPage.isTableEmptyStateDisplayed();
            Allure.parameter("Delete Empty State Displayed", String.valueOf(isEmpty));
            
            Assert.assertTrue(isEmpty, "Table should show empty state after deletion.");
            AllureEvidence.attachScreenshot(getDriver(), "Delete_Success");
        });
        
        // No finally cleanup needed as the test itself is deleting the record.
    }
}
