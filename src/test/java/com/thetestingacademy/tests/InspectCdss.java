package com.thetestingacademy.tests;

import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class InspectCdss extends com.thetestingacademy.base.CommonToAllTest {

    @Test
    public void inspectPage() {
        com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage loginPage = new com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        // Click target link
        try {
            By linkLoc = By.xpath("//a[@href='/cdss-management/drugDoseRange']");
            WaitHelpers.checkVisibility(getDriver(), linkLoc, 5);
            WebElement link = getDriver().findElement(linkLoc);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
        } catch (Exception e) {
            System.out.println("DEBUG - Could not click target link directly: " + e.getMessage());
            
            // Try clicking CDSS menu first
            try {
                WebElement menu = getDriver().findElement(By.xpath("//a[.//span[normalize-space(text())='CDSS']]"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", menu);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", menu);
                Thread.sleep(1000);
                
                WebElement link = getDriver().findElement(By.xpath("//a[@href='/cdss-management/drugDoseRange']"));
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
            } catch (Exception ex) {
                System.out.println("DEBUG - Could not click even after parent menu: " + ex.getMessage());
            }
        }

        // Select facility (triggered by navigation)
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

        try {
            Thread.sleep(5000); // Wait for page load
        } catch (Exception e) {}

        System.out.println("DEBUG - Current URL after navigation attempt: " + getDriver().getCurrentUrl());
        
        System.out.println("================= DOM DUMP ===================");
        try {
            System.out.println(getDriver().findElement(By.tagName("body")).getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("==============================================");
    }
}
