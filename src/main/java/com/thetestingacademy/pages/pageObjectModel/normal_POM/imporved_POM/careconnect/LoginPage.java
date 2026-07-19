package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import io.qameta.allure.Step;

/**
 * LoginPage — Improved POM pattern for CareConnect login.
 *
 * This page object extends CommonToAllPage to inherit reusable
 * actions (clickElement, enterInput, getText). It uses By locators
 * rather than @FindBy so elements are resolved at interaction time,
 * which avoids StaleElementReferenceException on Angular SPAs.
 *
 * ──────────────────────────────────────────────────────────────
 * TODO: After running the first test, open DevTools on
 *       https://qc.care-connect.health/login and verify/update
 *       the locators below to match the actual rendered DOM.
 * ──────────────────────────────────────────────────────────────
 */
public class LoginPage extends CommonToAllPage {

    // ── Locators ───────────────────────────────────────────────
    // Using stable Angular & PrimeNG specific attributes based on app bundle analysis
    private By usernameField   = By.cssSelector("input[formControlName='username']");
    // In PrimeNG, the actual input element is inside the p-password tag
    private By passwordField   = By.cssSelector("p-password[formControlName='password'] input, input[type='password']");
    private By loginButton     = By.cssSelector("button[type='submit'].btn-login");
    private By errorMessage    = By.cssSelector(".error-text");

    // ── Page Actions ───────────────────────────────────────────

    /**
     * Navigates to the CareConnect login page and waits for
     * the username field to be visible (Angular SPA needs time
     * to render after initial navigation).
     */
    @Step("Navigate to the CareConnect login page")
    public void navigateToLoginPage() {
        openCareConnectUrl();
        WaitHelpers.checkVisibility(getDriver(), usernameField, 15);
    }

    /**
     * Performs login with the given credentials.
     */
    @Step("Login with username: {0} and password: {1}")
    public void loginWithCredentials(String user, String pwd) {
        navigateToLoginPage();
        clearAndEnterInput(usernameField, user);
        clearAndEnterInput(passwordField, pwd);
        clickElement(loginButton);
    }

    /**
     * Performs login with invalid credentials and returns the
     * error message text shown on the page.
     */
    @Step("Attempt login with invalid credentials and capture error message")
    public String loginWithInvalidCredentials(String user, String pwd) {
        loginWithCredentials(user, pwd);
        WaitHelpers.checkVisibility(getDriver(), errorMessage, 10);
        return getText(errorMessage);
    }

    /**
     * Returns the page title — useful for verifying navigation
     * succeeded on the Angular SPA.
     */
    @Step("Get the title of the login page")
    public String getLoginPageTitle() {
        navigateToLoginPage();
        return getPageTitle();
    }

    /**
     * Checks if the login button is visible on the page.
     */
    @Step("Check if the login button is displayed")
    public boolean isLoginButtonDisplayed() {
        return isElementDisplayed(loginButton);
    }
}
