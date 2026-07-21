package com.thetestingacademy.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Wait strategy hierarchy:
 *   1. Explicit waits (WebDriverWait)  — preferred
 *   2. Fluent waits                     — for polling scenarios
 *   3. Thread.sleep                     — avoid entirely
 */
public class WaitHelpers {

    // ── Implicit Wait ──────────────────────────────────────────
    public static void waitImplicitWait(WebDriver driver, int timeInSeconds) {
        driver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
    }

    // ── Explicit Wait — Visibility ─────────────────────────────
    public static void checkVisibility(WebDriver driver, By locator, int timeInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ── Explicit Wait — Visibility (default 10s) ───────────────
    public static void checkVisibility(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // ── Explicit Wait — Text Present ───────────────────────────
    public static void checkVisibilityOfAndTextToBePresentInElement(
            WebDriver driver, By locator, String text, int timeInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds))
                .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    // ── Explicit Wait — Presence ───────────────────────────────
    public static WebElement presenceOfElement(WebDriver driver, By locator, int timeInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // ── Explicit Wait — Clickable ──────────────────────────────
    public static WebElement waitForClickable(WebDriver driver, By locator, int timeInSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // ── Fluent Wait ────────────────────────────────────────────
    public static void checkVisibilityByFluentWait(WebDriver driver, By locator) {
        new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // 🔥 Explicit Wait - Optional Element (returns true if visible, false if times out) 🔥
    public static boolean waitForOptionalElement(WebDriver driver, By locator, int timeInSeconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    // 🔥 Explicit Wait - Invisibility 🔥
    public static void waitForElementToBeInvisible(WebDriver driver, By locator, int timeInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}
