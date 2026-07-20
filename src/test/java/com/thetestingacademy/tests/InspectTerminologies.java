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
 * One-off discovery tool to inspect the Terminologies page DOM.
 * NOT part of testng.xml — run manually with -Dtest=InspectTerminologies.
 */
public class InspectTerminologies extends CommonToAllTest {
    @Test
    public void inspectTerminologiesPage() {
        System.out.println("====== INSPECTING TERMINOLOGIES PAGE ======");
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
            PropertiesReader.readKey("username"),
            PropertiesReader.readKey("password")
        );

        // Wait for sidebar to load after login
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        // Navigate directly to the terminologies page
        getDriver().navigate().to("https://qc.care-connect.health/terminology-management/terminology/list");

        // Wait for URL to settle
        try { Thread.sleep(5000); } catch (InterruptedException ignored) {}

        System.out.println("=== CURRENT URL: " + getDriver().getCurrentUrl() + " ===");

        // Handle facility modal if it appears
        try {
            By facilityDropdownTrigger = By.cssSelector("p-dropdown .p-dropdown-trigger");
            By facilityDropdownFirstItem = By.cssSelector("p-dropdownitem li");
            if (WaitHelpers.waitForOptionalElement(getDriver(), facilityDropdownTrigger, 3)) {
                System.out.println(">>> FACILITY MODAL DETECTED — selecting first option");
                WebElement dropdownTrigger = getDriver().findElement(facilityDropdownTrigger);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", dropdownTrigger);
                WaitHelpers.checkVisibility(getDriver(), facilityDropdownFirstItem, 5);
                WebElement item = getDriver().findElement(facilityDropdownFirstItem);
                ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", item);
                try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            } else {
                System.out.println(">>> No facility modal detected");
            }
        } catch (Exception e) {
            System.out.println(">>> Facility modal handling: " + e.getMessage());
        }

        System.out.println("=== URL AFTER MODAL HANDLING: " + getDriver().getCurrentUrl() + " ===");

        // Dump all headings (h1-h6, .page-title, spans with text)
        System.out.println("\n=== HEADINGS ===");
        for (String tag : new String[]{"h1", "h2", "h3", "h4", "h5", "h6"}) {
            List<WebElement> headings = getDriver().findElements(By.tagName(tag));
            for (WebElement h : headings) {
                String txt = h.getText().trim();
                if (!txt.isEmpty()) {
                    System.out.println(tag.toUpperCase() + ": '" + txt + "'");
                }
            }
        }

        // Check for .page-title or similar
        for (String selector : new String[]{"div.page-title", "span.page-title", ".card-title", ".p-card-title", ".page-header"}) {
            List<WebElement> els = getDriver().findElements(By.cssSelector(selector));
            for (WebElement e : els) {
                String txt = e.getText().trim();
                if (!txt.isEmpty()) {
                    System.out.println("CSS[" + selector + "]: '" + txt + "'");
                }
            }
        }

        // Check for any prominent text at the top
        List<WebElement> allSpans = getDriver().findElements(By.xpath("//div[contains(@class,'title') or contains(@class,'header') or contains(@class,'heading')]"));
        for (WebElement s : allSpans) {
            String txt = s.getText().trim();
            if (!txt.isEmpty() && txt.length() < 100) {
                System.out.println("TITLE-DIV: '" + txt + "'");
            }
        }

        // Check for tables
        System.out.println("\n=== TABLES ===");
        List<WebElement> pTables = getDriver().findElements(By.cssSelector("p-table"));
        System.out.println("p-table count: " + pTables.size());
        List<WebElement> htmlTables = getDriver().findElements(By.tagName("table"));
        System.out.println("HTML table count: " + htmlTables.size());

        // Check for buttons
        System.out.println("\n=== BUTTONS ===");
        List<WebElement> buttons = getDriver().findElements(By.tagName("button"));
        for (WebElement btn : buttons) {
            String txt = btn.getText().trim();
            String ariaLabel = btn.getAttribute("aria-label");
            if (!txt.isEmpty() || (ariaLabel != null && !ariaLabel.isEmpty())) {
                System.out.println("BUTTON: text='" + txt + "', aria-label='" + ariaLabel + "'");
            }
        }

        // Check for breadcrumbs or toolbar
        System.out.println("\n=== TOOLBAR / BREADCRUMBS ===");
        List<WebElement> toolbars = getDriver().findElements(By.cssSelector("p-toolbar, .p-toolbar, nav[aria-label='breadcrumb']"));
        System.out.println("Toolbar elements found: " + toolbars.size());

        System.out.println("\n====== TERMINOLOGIES INSPECTION COMPLETE ======");
    }
}
