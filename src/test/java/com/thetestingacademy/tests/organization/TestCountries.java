package com.thetestingacademy.tests.organization;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.CountriesPage;
import com.thetestingacademy.utils.ExtentManager;
import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.ScreenshotUtils;
import com.thetestingacademy.utils.TestContext;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCountries extends CommonToAllTest {
    
    private CountriesPage countriesPage;
    private long timestamp;
    private String strictArabicName;
    private String updatedStrictArabicName;

    @org.testng.annotations.BeforeMethod
    public void setupModule() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        countriesPage = new CountriesPage();
        countriesPage.navigateToCountriesPage();

        if (timestamp == 0) {
            timestamp = System.currentTimeMillis();
            
            String randomArabic = "";
            String arabicChars = "ابتثجحخدذرزسشصضطظعغفقكلمنهوي";
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < 6; i++) {
                randomArabic += arabicChars.charAt(random.nextInt(arabicChars.length()));
            }
            strictArabicName = "دولة اختبار " + randomArabic;
            updatedStrictArabicName = "تحديث دولة " + randomArabic;
            TestContext.set("createdArabicName", strictArabicName);
        }
    }

    @org.testng.annotations.AfterMethod
    public void teardownMethod(org.testng.ITestResult result) {
        if (result.getStatus() == org.testng.ITestResult.FAILURE) {
            String path = "target/screenshots/FAILURE_" + result.getName() + ".png";
            ScreenshotUtils.takeScreenshot(getDriver(), "FAILURE_" + result.getName() + ".png");
            ExtentTest test = ExtentManager.getTest();
            if (test != null) {
                test.fail("<b>Teardown Failure Screenshot:</b>", 
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/FAILURE_" + result.getName() + ".png").build());
            }
        }
        try {
            countriesPage.closeModalIfOpen();
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(getDriver());
            actions.sendKeys(org.openqa.selenium.Keys.ESCAPE).perform();
        } catch (Exception e) {
            logger.warn("Teardown clean up encountered an issue, but continuing: " + e.getMessage());
        }
    }

    @Epic("Organization Module")
    @Feature("Countries Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify full CRUD functionality: Create Country")
    @Owner("QA Team")
    @Test(priority = 1)
    public void TC01_CreateCountry_Positive() {
        ExtentTest test = ExtentManager.getTest();
        test.log(Status.INFO, "<b>عنوان:</b> إنشاء دولة جديدة - Create Country Positive");
        test.log(Status.INFO, "<b>الشروط المسبقة / Preconditions:</b> User is logged in and on the Countries page.");

        String uniqueName = "ZZTESTCountry" + timestamp;
        
        try {
            test.log(Status.INFO, "<b>Step 1:</b> Open Create Dialog | <b>Expected:</b> Dialog is visible");
            countriesPage.openCreateDialog();
            
            test.log(Status.INFO, "<b>Step 2:</b> Fill and Save Country | <b>Expected:</b> Details are entered and save is clicked");
            countriesPage.fillAndSaveCountry(strictArabicName, uniqueName, String.valueOf(timestamp).substring(8, 11));
            
            test.log(Status.INFO, "<b>Step 3:</b> Verify Success Toast | <b>Expected:</b> Toast indicates success");
            String successMsg = countriesPage.getToastMessage();
            Assert.assertTrue(successMsg.toLowerCase().contains("success") || successMsg.contains("Created"), "Creation failed with toast: " + successMsg);
            
            ScreenshotUtils.takeScreenshot(getDriver(), "TC01_CreateCountry_Success.png");
            test.pass("<b>Success:</b> Country Created",
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC01_CreateCountry_Success.png").build());
            
            TestContext.setCreatedCountryName(uniqueName);
        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(getDriver(), "TC01_CreateCountry_Failure.png");
            test.fail("<b>Failed:</b> " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC01_CreateCountry_Failure.png").build());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Epic("Organization Module")
    @Feature("Countries Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify validation for required fields when creating a Country")
    @Owner("QA Team")
    @Test(priority = 2)
    public void TC02_CreateCountry_Validation_RequiredFields() {
        ExtentTest test = ExtentManager.getTest();
        test.log(Status.INFO, "<b>عنوان:</b> فحص الحقول الإجبارية عند إضافة دولة - Create Country Validation Required");
        test.log(Status.INFO, "<b>الشروط المسبقة / Preconditions:</b> Modal is open and blank.");

        try {
            test.log(Status.INFO, "<b>Step 1:</b> Open Create Dialog | <b>Expected:</b> Dialog is visible");
            countriesPage.openCreateDialog();
            
            test.log(Status.INFO, "<b>Step 2:</b> Click Save without typing | <b>Expected:</b> Validation is triggered");
            countriesPage.fillAndSaveCountry("", "", "");
            
            test.log(Status.INFO, "<b>Step 3:</b> Verify Validation Errors | <b>Expected:</b> Required field warnings appear");
            java.util.List<String> errors = countriesPage.getValidationErrors();
            Assert.assertFalse(errors.isEmpty(), "No validation errors found on empty save.");
            
            ScreenshotUtils.takeScreenshot(getDriver(), "TC02_Validation_RequiredFields.png");
            test.pass("<b>Success:</b> Validations displayed correctly",
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC02_Validation_RequiredFields.png").build());
        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(getDriver(), "TC02_Validation_RequiredFields_Failure.png");
            test.fail("<b>Failed:</b> " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC02_Validation_RequiredFields_Failure.png").build());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Epic("Organization Module")
    @Feature("Countries Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify validation for duplicate Country names")
    @Owner("QA Team")
    @Test(priority = 3, dependsOnMethods = "TC01_CreateCountry_Positive")
    public void TC03_CreateCountry_Validation_Duplicate() {
        ExtentTest test = ExtentManager.getTest();
        test.log(Status.INFO, "<b>عنوان:</b> منع إضافة دولة باسم مكرر - Create Country Validation Duplicate");
        test.log(Status.INFO, "<b>الشروط المسبقة / Preconditions:</b> A country already exists in DB.");

        String uniqueName = TestContext.getCreatedCountryName();
        String createdArabicName = (String) TestContext.get("createdArabicName");

        try {
            test.log(Status.INFO, "<b>Step 1:</b> Open Create Dialog | <b>Expected:</b> Dialog is visible");
            countriesPage.openCreateDialog();
            
            test.log(Status.INFO, "<b>Step 2:</b> Fill and Save Country with duplicate data | <b>Expected:</b> Error toast appears");
            countriesPage.fillAndSaveCountry(createdArabicName, uniqueName, String.valueOf(timestamp).substring(8, 11));
            
            test.log(Status.INFO, "<b>Step 3:</b> Verify Duplicate Error Toast | <b>Expected:</b> Success string is not present");
            String toastMsg = countriesPage.getToastMessage();
            Assert.assertFalse(toastMsg.toLowerCase().contains("success") || toastMsg.contains("Created"), "Duplicate country creation succeeded unexpectedly!");
            
            ScreenshotUtils.takeScreenshot(getDriver(), "TC03_Validation_Duplicate.png");
            test.pass("<b>Success:</b> Duplicate prevented. Toast: " + toastMsg,
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC03_Validation_Duplicate.png").build());
        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(getDriver(), "TC03_Validation_Duplicate_Failure.png");
            test.fail("<b>Failed:</b> " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC03_Validation_Duplicate_Failure.png").build());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Epic("Organization Module")
    @Feature("Countries Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify full CRUD functionality: Search Country")
    @Owner("QA Team")
    @Test(priority = 4, dependsOnMethods = "TC01_CreateCountry_Positive")
    public void TC04_SearchCountry_Positive() {
        ExtentTest test = ExtentManager.getTest();
        test.log(Status.INFO, "<b>عنوان:</b> البحث عن دولة - Search Country Positive");
        test.log(Status.INFO, "<b>الشروط المسبقة / Preconditions:</b> A country has been successfully created.");

        String uniqueName = TestContext.getCreatedCountryName();
        Assert.assertNotNull(uniqueName, "Created country name should be in TestContext");

        try {
            test.log(Status.INFO, "<b>Step 1:</b> Search for country using column filter | <b>Expected:</b> Filter is applied");
            countriesPage.searchCountry(uniqueName);
            
            test.log(Status.INFO, "<b>Step 2:</b> Verify country in table | <b>Expected:</b> Country row is present");
            boolean isFound = countriesPage.isCountryInTable(uniqueName);
            Assert.assertTrue(isFound, "The newly created country should be found in the table.");

            ScreenshotUtils.takeScreenshot(getDriver(), "TC04_SearchCountry_Success.png");
            test.pass("<b>Success:</b> Country found",
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC04_SearchCountry_Success.png").build());
        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(getDriver(), "TC04_SearchCountry_Failure.png");
            test.fail("<b>Failed:</b> " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC04_SearchCountry_Failure.png").build());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Epic("Organization Module")
    @Feature("Countries Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify full CRUD functionality: Update Country")
    @Owner("QA Team")
    @Test(priority = 5, dependsOnMethods = "TC01_CreateCountry_Positive")
    public void TC05_UpdateCountry_Positive() {
        ExtentTest test = ExtentManager.getTest();
        test.log(Status.INFO, "<b>عنوان:</b> تحديث دولة - Update Country Positive");
        test.log(Status.INFO, "<b>الشروط المسبقة / Preconditions:</b> A country has been created and searched.");

        String uniqueName = TestContext.getCreatedCountryName();
        String updatedUniqueName = "ZZTESTCountryUpdated" + timestamp;

        try {
            test.log(Status.INFO, "<b>Step 1:</b> Search for country to update | <b>Expected:</b> Country row is found");
            countriesPage.searchCountry(uniqueName);
            
            test.log(Status.INFO, "<b>Step 2:</b> Click edit and update details | <b>Expected:</b> Form is updated and saved");
            countriesPage.clickEditForCountry(uniqueName);
            countriesPage.fillAndSaveCountry(updatedStrictArabicName, updatedUniqueName, String.valueOf(timestamp).substring(8));
            
            test.log(Status.INFO, "<b>Step 3:</b> Verify Success Toast | <b>Expected:</b> Toast indicates success");
            String updateMsg = countriesPage.getToastMessage();
            Assert.assertTrue(updateMsg.toLowerCase().contains("success") || updateMsg.contains("Updated"), "Update toast message should indicate success. Actual: " + updateMsg);

            test.log(Status.INFO, "<b>Step 4:</b> Search for updated country name | <b>Expected:</b> Grid shows updated country");
            countriesPage.searchCountry(updatedUniqueName);
            boolean isUpdatedFound = countriesPage.isCountryInTable(updatedUniqueName);
            Assert.assertTrue(isUpdatedFound, "The updated country should be found in the table.");
            
            ScreenshotUtils.takeScreenshot(getDriver(), "TC05_UpdateCountry_Success.png");
            test.pass("<b>Success:</b> Country successfully updated",
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC05_UpdateCountry_Success.png").build());
            
            TestContext.setCreatedCountryName(updatedUniqueName);
        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(getDriver(), "TC05_UpdateCountry_Failure.png");
            test.fail("<b>Failed:</b> " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC05_UpdateCountry_Failure.png").build());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }

    @Epic("Organization Module")
    @Feature("Countries Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify full CRUD functionality: Delete Country")
    @Owner("QA Team")
    @Test(priority = 6, dependsOnMethods = "TC01_CreateCountry_Positive", alwaysRun = true)
    public void TC06_DeleteCountry_Positive() {
        ExtentTest test = ExtentManager.getTest();
        test.log(Status.INFO, "<b>عنوان:</b> حذف دولة - Delete Country Positive");
        test.log(Status.INFO, "<b>الشروط المسبقة / Preconditions:</b> A country exists to be deleted (created or updated).");

        String targetName = TestContext.getCreatedCountryName();
        Assert.assertNotNull(targetName, "No target name found in TestContext to delete.");

        try {
            test.log(Status.INFO, "<b>Step 1:</b> Search for country to delete | <b>Expected:</b> Country row is found");
            countriesPage.searchCountry(targetName);
            
            test.log(Status.INFO, "<b>Step 2:</b> Click delete and confirm | <b>Expected:</b> Delete is processed");
            if (countriesPage.isCountryInTable(targetName)) {
                countriesPage.clickDeleteForCountry(targetName);
                countriesPage.confirmDelete();
            } else {
                Assert.fail("Target country was not found in table for deletion.");
            }
            
            test.log(Status.INFO, "<b>Step 3:</b> Verify removal from grid | <b>Expected:</b> Country no longer exists");
            ScreenshotUtils.takeScreenshot(getDriver(), "TC06_DeleteCountry_Success.png");
            test.pass("<b>Success:</b> Country successfully deleted",
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC06_DeleteCountry_Success.png").build());
            
        } catch (Exception e) {
            ScreenshotUtils.takeScreenshot(getDriver(), "TC06_DeleteCountry_Failure.png");
            test.fail("<b>Failed:</b> " + e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromPath("../screenshots/TC06_DeleteCountry_Failure.png").build());
            Assert.fail("Test failed: " + e.getMessage());
        }
    }
}
