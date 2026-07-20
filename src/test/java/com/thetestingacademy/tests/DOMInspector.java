package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.thetestingacademy.utils.WaitHelpers;

import java.util.List;

public class DOMInspector extends CommonToAllTest {
    @Test
    public void inspect() throws InterruptedException {
        System.out.println("====== STARTING DOM INSPECTOR ======");
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
            com.thetestingacademy.utils.PropertiesReader.readKey("username"),
            com.thetestingacademy.utils.PropertiesReader.readKey("password")
        );

        try { Thread.sleep(5000); } catch (InterruptedException e) {}
        System.out.println("Logged in and waited 5s. Current URL: " + getDriver().getCurrentUrl());

        // Wait for the 'Patients' sidebar item to be visible and click it
        By dashboardSidebarItem = By.xpath("//*[normalize-space(text())='Patients']");
        WaitHelpers.checkVisibility(getDriver(), dashboardSidebarItem, 15);
        WebElement el = getDriver().findElement(dashboardSidebarItem);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
        
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        System.out.println("Clicked Patients sidebar item.");

        By visitsLink = By.xpath("//a[contains(@href, '/patient-management/visit')]");
        WebElement linkEl = getDriver().findElement(visitsLink);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", linkEl);

        try { Thread.sleep(8000); } catch (InterruptedException ignored) {}
        
        System.out.println("====== TEXT START ======");
        String[] lines = getDriver().findElement(By.tagName("body")).getText().split("\n");
        for (int i = 0; i < Math.min(30, lines.length); i++) {
            System.out.println(lines[i]);
        }
        System.out.println("====== TEXT END ======");

        try {
            System.out.println("Page Title: " + getDriver().getTitle());
            java.util.List<WebElement> headers = getDriver().findElements(By.xpath("//h1 | //h2 | //h3 | //h4 | //*[contains(@class, 'title')] | //*[contains(@class, 'header')]"));
            System.out.println("====== HEADER ELEMENTS ======");
            for (WebElement headerEl : headers) {
                if (headerEl.isDisplayed()) {
                    System.out.println("Tag: " + headerEl.getTagName() + ", Class: " + headerEl.getAttribute("class") + ", Text: " + headerEl.getText().trim());
                }
            }
            System.out.println("====== HEADER ELEMENTS END ======");
        } catch (Exception e) {}
        try {
            System.out.println("====== BUTTON ELEMENTS ======");
            java.util.List<WebElement> buttons = getDriver().findElements(By.tagName("button"));
        } catch (Exception e) {}
        
        // Now go to My Appointments
        try {
            WebElement visitsSidebar = getDriver().findElement(By.xpath("//span[contains(@class, 'layout-menuitem-text') and text()='Visits']"));
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", visitsSidebar);
            Thread.sleep(500);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", visitsSidebar);
            Thread.sleep(1000);
            
            // The modal for facility selection should pop up
            try {
                System.out.println("Handling facility modal...");
                By drp = By.cssSelector("p-dropdown .p-dropdown-trigger");
                if (getDriver().findElements(drp).size() > 0) {
                    WebElement dropdownTrigger = getDriver().findElement(drp);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dropdownTrigger);
                    Thread.sleep(1000);
                    By drpItem = By.cssSelector("p-dropdownitem li");
                    WebElement item = getDriver().findElement(drpItem);
                    ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                    Thread.sleep(2000);
                }
            } catch(Exception e) {}

            System.out.println("Clicking My Appointments sidebar item.");
            WebElement myApptLink = getDriver().findElement(By.xpath("//a[contains(@href, '/patient-management/visit/my-appointments')]"));
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", myApptLink);
            Thread.sleep(500);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", myApptLink);
        } catch (Exception e) {
            System.out.println("Could not click My Appointments: " + e.getMessage());
        }    
        
        try {
            Thread.sleep(5000);
            
            System.out.println("====== TEXT START ======");
            java.util.List<WebElement> texts = getDriver().findElements(By.xpath("//*[string-length(normalize-space(text())) > 0]"));
            java.util.Set<String> uniqueTexts = new java.util.HashSet<>();
            for (WebElement textEl : texts) {
                if (textEl.isDisplayed()) {
                    String t = textEl.getText().trim();
                    if (!t.isEmpty() && uniqueTexts.add(t)) {
                        System.out.println(t);
                    }
                }
            }
            System.out.println("====== TEXT END ======");
            
        } catch (Exception e) {}
    }
}
