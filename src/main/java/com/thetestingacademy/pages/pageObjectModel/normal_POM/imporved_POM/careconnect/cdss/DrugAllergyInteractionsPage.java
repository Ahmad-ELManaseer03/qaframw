package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class DrugAllergyInteractionsPage extends CommonToAllPage {

    private final By linkLoc = By.xpath("//a[@href='/cdss-management/drugAllergyInteractions']");
    private final By headerLoc = By.xpath("//*[contains(text(), 'Drug Allergy Interactions List')]");

    public DrugAllergyInteractionsPage navigateToDrugAllergyInteractions() {
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        try {
            WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
            WebElement link = getDriver().findElement(linkLoc);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
        } catch (Exception e) {
            try {
                // Top level CDSS
                WebElement cdssMenu = getDriver().findElement(By.xpath("//a[.//span[normalize-space(text())='CDSS']]"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", cdssMenu);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", cdssMenu);
                


                // Second level CDSS Modules
                WebElement cdssModulesMenu = getDriver().findElement(By.xpath("//a[.//span[normalize-space(text())='CDSS Modules']]"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", cdssModulesMenu);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", cdssModulesMenu);



                WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
                WebElement link = getDriver().findElement(linkLoc);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
            } catch (Exception ex) {
                System.out.println("Could not click Drug Allergy Interactions: " + ex.getMessage());
                try {
                    WebElement link = getDriver().findElement(linkLoc);
                    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
                } catch (Exception e2) {}
            }
        }

        // Handle Select Facility Modal
        try {
            By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
            if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 5)) {
                WebElement dt = getDriver().findElement(facilityDropdownTrigger);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dt);

                By firstItem = By.cssSelector("p-dropdownitem li");
                WaitHelpers.checkVisibility(getDriver(), firstItem, 5);
                WebElement item = getDriver().findElement(firstItem);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);

                By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
                if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 2)) {
                    WebElement confirmBtn = getDriver().findElement(selectFacilityBtn);
                    ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", confirmBtn);
                }
            }
        } catch (Exception e) {}

        return this;
    }

    public boolean isPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), headerLoc, 15);
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/cdss-management/drugAllergyInteractions");
    }
}
