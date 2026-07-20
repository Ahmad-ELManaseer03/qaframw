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

        // Wait for the 'Patients' sidebar item to be visible and click it
        By dashboardSidebarItem = By.xpath("//*[normalize-space(text())='Patients']");
        WaitHelpers.checkVisibility(getDriver(), dashboardSidebarItem, 15);
        WebElement el = getDriver().findElement(dashboardSidebarItem);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", el);
        
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        java.util.List<WebElement> allLinks = getDriver().findElements(By.xpath("//ul[contains(@class, 'layout-menu')]//a"));
        System.out.println("=== ALL SIDEBAR LINKS ===");
        for (WebElement link : allLinks) {
            String href = link.getAttribute("href");
            String text = link.getText().trim();
            if (text.isEmpty()) {
                text = (String) ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("return arguments[0].textContent;", link);
                if (text != null) text = text.trim();
            }
            System.out.println("Text: '" + text + "', Href: " + href);
        }
        System.out.println("=========================");
        
        org.testng.Assert.fail("Dumping sidebar links complete");
    }
}
