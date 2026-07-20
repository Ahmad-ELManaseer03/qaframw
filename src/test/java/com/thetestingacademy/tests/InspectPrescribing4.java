package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class InspectPrescribing4 extends CommonToAllTest {

    @Test
    public void inspectViaSidebar() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
            PropertiesReader.readKey("username"),
            PropertiesReader.readKey("password")
        );
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        // Facility modal handling
        try {
            By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
            if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 5)) {
                By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
                if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 3)) {
                    WebElement dt = getDriver().findElement(facilityDropdownTrigger);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dt);
                    By firstItem = By.cssSelector("p-dropdownitem li");
                    WaitHelpers.checkVisibility(getDriver(), firstItem, 5);
                    WebElement item = getDriver().findElement(firstItem);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                }
                WebElement confirmBtn = getDriver().findElement(selectFacilityBtn);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", confirmBtn);
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            }
        } catch (Exception e) {}
        
        // Open Prescribing menu if it's not open
        try {
            WebElement menu = getDriver().findElement(By.xpath("//a[.//span[text()='Prescribing']]"));
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", menu);
            Thread.sleep(1000);
        } catch (Exception e) {}

        clickAndInspect("//a[@href='/prescribe-management/prescriptions']", "Prescriptions");
        clickAndInspect("//a[@href='/prescribe-management/expired-prescriptions']", "Expired Prescriptions");
    }
    
    private void clickAndInspect(String xpath, String label) {
        System.out.println("\n\n========== INSPECTING: " + label + " ==========");
        try {
            WebElement link = getDriver().findElement(By.xpath(xpath));
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Could not click link: " + e.getMessage());
            return;
        }
        
        System.out.println("URL: " + getDriver().getCurrentUrl());
        
        for (String sel : new String[]{"div.page-title", "span.page-title", ".card-title", ".p-card-title"}) {
            for (WebElement e : getDriver().findElements(By.cssSelector(sel))) {
                String txt = e.getText().trim();
                if (!txt.isEmpty()) System.out.println("CSS[" + sel + "]: '" + txt + "'");
            }
        }
        for (WebElement d : getDriver().findElements(By.xpath("//div[contains(@class,'title') or contains(@class,'header')]"))) {
            String txt = d.getText().trim();
            if (!txt.isEmpty() && txt.length() < 60) System.out.println("TITLE-DIV: '" + txt + "'");
        }
        System.out.println("========== END: " + label + " ==========");
    }
}
