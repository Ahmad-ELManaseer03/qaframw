package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class InspectPrescribing7 extends CommonToAllTest {

    @Test
    public void testSidebarFlow() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
            PropertiesReader.readKey("username"),
            PropertiesReader.readKey("password")
        );
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        // Open Prescribing menu
        try {
            WebElement menu = getDriver().findElement(By.xpath("//a[.//span[text()='Prescribing']]"));
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", menu);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", menu);
            Thread.sleep(1000);
        } catch (Exception e) {}

        System.out.println("\n\n========== INSPECTING Prescriptions via Sidebar ==========");
        
        try {
            WebElement link = getDriver().findElement(By.xpath("//a[@href='/prescribe-management/prescriptions']"));
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Could not click link: " + e.getMessage());
            return;
        }

        // Handle modal if it appears
        try {
            By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
            if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 5)) {
                System.out.println(">>> MODAL APPEARED! Handling...");
                WebElement dt = getDriver().findElement(facilityDropdownTrigger);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dt);
                
                By firstItem = By.cssSelector("p-dropdownitem li");
                WaitHelpers.checkVisibility(getDriver(), firstItem, 5);
                WebElement item = getDriver().findElement(firstItem);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                
                Thread.sleep(1000);
                
                // Try to click any button like "Select Facility"
                for (WebElement btn : getDriver().findElements(By.tagName("button"))) {
                    String btnText = btn.getText().trim();
                    if (btnText.toLowerCase().contains("select facility") || btnText.toLowerCase().contains("submit")) {
                        System.out.println(">>> CLICKING MODAL BUTTON: " + btnText);
                        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", btn);
                        break;
                    }
                }
                Thread.sleep(3000);
            }
        } catch (Exception e) {}
        
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
        System.out.println("========== END ==========");
    }
}
