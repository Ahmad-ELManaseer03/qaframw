package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.formulary;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Page Object for the Trades List page.
 * URL: /medications-order/trades
 *
 * Live DOM inspection (2026-07-20) reveals:
 * - Header: TITLE-DIV with exact text "Trades List"
 * - Data table: p-table (1 instance)
 * - Buttons: Clear, Ingredient, Add, Upload, Show Tracking, Audit Log, Export
 * - Pagination: pages 1-5
 */
public class TradesPage extends CommonToAllPage {

    @Step("Navigate to Trades page and verify it loaded")
    public boolean isTradesPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);
        getDriver().navigate().to("https://qc.care-connect.health/medications-order/trades");

        try {
            By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
            if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 5)) {
                By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
                if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 3)) {
                    org.openqa.selenium.WebElement dt = getDriver().findElement(facilityDropdownTrigger);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dt);
                    By firstItem = By.cssSelector("p-dropdownitem li");
                    WaitHelpers.checkVisibility(getDriver(), firstItem, 5);
                    org.openqa.selenium.WebElement item = getDriver().findElement(firstItem);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                }
                org.openqa.selenium.WebElement confirmBtn = getDriver().findElement(selectFacilityBtn);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", confirmBtn);
            }
        } catch (Exception e) {}

        By pageHeader = By.xpath("//*[normalize-space(text())='Trades List']");
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);

        return getDriver().getCurrentUrl().contains("/medications-order/trades");
    }
}
