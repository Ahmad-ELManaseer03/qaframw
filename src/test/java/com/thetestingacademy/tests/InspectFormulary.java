package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

/**
 * One-off discovery tool to inspect all Formulary module pages.
 * NOT part of testng.xml — run manually with -Dtest=InspectFormulary.
 */
public class InspectFormulary extends CommonToAllTest {

    private void inspectPage(String url, String label) {
        System.out.println("\n\n========== INSPECTING: " + label + " ==========");
        getDriver().navigate().to(url);

        // Handle facility modal
        try {
            By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
            if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 4)) {
                System.out.println(">>> FACILITY MODAL DETECTED");
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
            } else {
                System.out.println(">>> No facility modal");
            }
        } catch (Exception e) {
            System.out.println(">>> Facility modal handling: " + e.getMessage());
        }

        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        System.out.println("URL: " + getDriver().getCurrentUrl());

        // Headings
        for (String tag : new String[]{"h1", "h2", "h3", "h4"}) {
            for (WebElement h : getDriver().findElements(By.tagName(tag))) {
                String txt = h.getText().trim();
                if (!txt.isEmpty()) System.out.println(tag.toUpperCase() + ": '" + txt + "'");
            }
        }
        for (String sel : new String[]{"div.page-title", "span.page-title", ".card-title", ".p-card-title"}) {
            for (WebElement e : getDriver().findElements(By.cssSelector(sel))) {
                String txt = e.getText().trim();
                if (!txt.isEmpty()) System.out.println("CSS[" + sel + "]: '" + txt + "'");
            }
        }
        // Title divs
        for (WebElement d : getDriver().findElements(By.xpath("//div[contains(@class,'title') or contains(@class,'header')]"))) {
            String txt = d.getText().trim();
            if (!txt.isEmpty() && txt.length() < 60) System.out.println("TITLE-DIV: '" + txt + "'");
        }

        // Tables
        System.out.println("p-table: " + getDriver().findElements(By.cssSelector("p-table")).size());
        System.out.println("HTML table: " + getDriver().findElements(By.tagName("table")).size());

        // Buttons
        for (WebElement btn : getDriver().findElements(By.tagName("button"))) {
            String txt = btn.getText().trim();
            if (!txt.isEmpty() && txt.length() < 40) System.out.println("BTN: '" + txt + "'");
        }
        System.out.println("========== END: " + label + " ==========");
    }

    @Test
    public void inspectAllFormularyPages() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
            PropertiesReader.readKey("username"),
            PropertiesReader.readKey("password")
        );
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        inspectPage("https://qc.care-connect.health/medications-order/generics", "Generics");
        inspectPage("https://qc.care-connect.health/medications-order/synonyms", "Medication Synonyms");
        inspectPage("https://qc.care-connect.health/medications-order/trades", "Trades");
        inspectPage("https://qc.care-connect.health/medications-order/careplans", "Care Plans Management");
        inspectPage("https://qc.care-connect.health/medications-order/release", "Releases");
    }
}
