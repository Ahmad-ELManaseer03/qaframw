package com.thetestingacademy.tests.careconnect;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestLogin — Smoke tests for the CareConnect login page.
 *
 * Extends CommonToAllTest so browser setup/teardown is automatic.
 * Uses the Improved POM LoginPage which inherits from CommonToAllPage.
 */
public class TestLogin extends CommonToAllTest {

    /**
     * Verifies that navigating to the CareConnect login URL
     * correctly loads the page and the title matches "CareConnect".
     */
    @Description("Verify that the CareConnect login page loads and has the correct title")
    @Owner("QA Team")
    @Test(priority = 1)
    public void testLoginPageTitle() {
        logger.info("▶ Starting: testLoginPageTitle");

        LoginPage loginPage = new LoginPage();
        String actualTitle = loginPage.getLoginPageTitle();

        logger.info("Page title: " + actualTitle);

        String expectedTitle = PropertiesReader.readKey("expected_page_title");
        assertThat(actualTitle)
                .as("Login page title should be '%s'", expectedTitle)
                .isNotNull()
                .isNotBlank()
                .isEqualTo(expectedTitle);

        logger.info("✔ Passed: testLoginPageTitle");
    }

    /**
     * Verifies that the login button is visible on the page,
     * confirming the Angular SPA has fully rendered.
     */
    @Description("Verify that the login button is displayed on the login page")
    @Owner("QA Team")
    @Test(priority = 2)
    public void testLoginButtonIsDisplayed() {
        logger.info("▶ Starting: testLoginButtonIsDisplayed");

        LoginPage loginPage = new LoginPage();
        loginPage.navigateToLoginPage();

        boolean isDisplayed = loginPage.isLoginButtonDisplayed();
        logger.info("Login button displayed: " + isDisplayed);

        Assert.assertTrue(isDisplayed, "Login button should be visible on the page");

        logger.info("✔ Passed: testLoginButtonIsDisplayed");
    }

    /**
     * Verifies that logging in with invalid credentials shows
     * an error message on the page.
     */
    @Description("Verify that invalid credentials produce an error message")
    @Owner("QA Team")
    @Test(priority = 3)
    public void testNegativeLogin() {
        logger.info("▶ Starting: testNegativeLogin");

        LoginPage loginPage = new LoginPage();
        String errorMsg = loginPage.loginWithInvalidCredentials(
                PropertiesReader.readKey("invalid_username"),
                PropertiesReader.readKey("invalid_password")
        );

        logger.info("Error message: " + errorMsg);

        assertThat(errorMsg)
                .as("Error message should appear for invalid credentials")
                .isNotNull()
                .isNotBlank();

        logger.info("✔ Passed: testNegativeLogin");
    }
}
