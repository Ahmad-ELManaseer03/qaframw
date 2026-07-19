package com.thetestingacademy.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Standalone DOM inspector: logs in with invalid creds, then polls
 * the DOM every second for 15 seconds looking for any new visible
 * elements that could be the error message.
 *
 * Also logs in with VALID creds to inspect the Patients page header.
 */
public class FindError {
    public static void main(String[] args) throws Exception {
        ChromeOptions opts = new ChromeOptions();
        // run headed so we get the real DOM including geolocation prompts
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        opts.setExperimentalOption("prefs", prefs);

        // ──────── PHASE 1: Invalid login ────────────────────────
        System.out.println("═══════ PHASE 1: INVALID LOGIN ═══════");
        WebDriver driver = new ChromeDriver(opts);
        driver.manage().window().maximize();
        try {
            driver.get("https://qc.care-connect.health/login");
            Thread.sleep(4000);

            System.out.println("── Body text BEFORE login click ──");
            System.out.println(driver.findElement(By.tagName("body")).getText());

            driver.findElement(By.cssSelector("input[formControlName='username']")).sendKeys("invalid@example.com");
            driver.findElement(By.cssSelector("input[type='password']")).sendKeys("WrongPass123");
            driver.findElement(By.cssSelector("button[type='submit'].btn-login")).click();

            System.out.println("\n── Clicked login, polling for changes... ──");
            String prevText = driver.findElement(By.tagName("body")).getText();
            for (int i = 1; i <= 15; i++) {
                Thread.sleep(1000);
                String currText = driver.findElement(By.tagName("body")).getText();
                if (!currText.equals(prevText)) {
                    System.out.println("BODY TEXT CHANGED at second " + i + ":");
                    System.out.println(currText);
                    prevText = currText;
                }

                // Check for any new visible elements with error-like classes
                List<WebElement> toastDetails = driver.findElements(By.cssSelector(".p-toast-detail"));
                List<WebElement> toastMsgText = driver.findElements(By.cssSelector(".p-toast-message-text"));
                List<WebElement> toastMsgContent = driver.findElements(By.cssSelector(".p-toast-message-content"));
                List<WebElement> errorTexts = driver.findElements(By.cssSelector(".error-text"));
                List<WebElement> pMessages = driver.findElements(By.cssSelector("p-messages .p-message"));
                List<WebElement> alerts = driver.findElements(By.cssSelector("[role='alert']"));
                List<WebElement> ngbAlerts = driver.findElements(By.cssSelector("ngb-alert, .alert"));

                if (!toastDetails.isEmpty()) {
                    for (WebElement e : toastDetails) System.out.println("[p-toast-detail] outerHTML: " + e.getAttribute("outerHTML"));
                }
                if (!toastMsgText.isEmpty()) {
                    for (WebElement e : toastMsgText) System.out.println("[p-toast-message-text] outerHTML: " + e.getAttribute("outerHTML"));
                }
                if (!toastMsgContent.isEmpty()) {
                    for (WebElement e : toastMsgContent) System.out.println("[p-toast-message-content] outerHTML: " + e.getAttribute("outerHTML"));
                }
                if (!errorTexts.isEmpty()) {
                    for (WebElement e : errorTexts) System.out.println("[error-text] outerHTML: " + e.getAttribute("outerHTML"));
                }
                if (!pMessages.isEmpty()) {
                    for (WebElement e : pMessages) System.out.println("[p-message] outerHTML: " + e.getAttribute("outerHTML"));
                }
                if (!alerts.isEmpty()) {
                    for (WebElement e : alerts) System.out.println("[role=alert] outerHTML: " + e.getAttribute("outerHTML"));
                }
                if (!ngbAlerts.isEmpty()) {
                    for (WebElement e : ngbAlerts) System.out.println("[ngb-alert/.alert] outerHTML: " + e.getAttribute("outerHTML"));
                }
            }

            // Brute force: dump the entire body innerHTML
            System.out.println("\n── FULL BODY innerHTML (last 3000 chars) ──");
            String bodyHtml = (String) ((JavascriptExecutor) driver).executeScript(
                "return document.body.innerHTML;"
            );
            // Print last 3000 chars to capture the toast/error area
            int start = Math.max(0, bodyHtml.length() - 3000);
            System.out.println(bodyHtml.substring(start));

            // Also dump p-toast specifically
            System.out.println("\n── p-toast outerHTML ──");
            List<WebElement> pToasts = driver.findElements(By.tagName("p-toast"));
            for (WebElement t : pToasts) {
                System.out.println(t.getAttribute("outerHTML"));
            }

        } finally {
            driver.quit();
        }

        // ──────── PHASE 2: Valid login → Patients page ──────────
        System.out.println("\n═══════ PHASE 2: VALID LOGIN → PATIENTS PAGE ═══════");
        WebDriver driver2 = new ChromeDriver(opts);
        driver2.manage().window().maximize();
        try {
            driver2.get("https://qc.care-connect.health/login");
            Thread.sleep(4000);

            driver2.findElement(By.cssSelector("input[formControlName='username']")).sendKeys("careconnect@neorx.co");
            driver2.findElement(By.cssSelector("input[type='password']")).sendKeys("Admin@1234");
            driver2.findElement(By.cssSelector("button[type='submit'].btn-login")).click();

            System.out.println("── Logged in, waiting for dashboard/patients page... ──");
            Thread.sleep(8000);

            System.out.println("── Current URL: " + driver2.getCurrentUrl() + " ──");
            System.out.println("── Body text after valid login ──");
            System.out.println(driver2.findElement(By.tagName("body")).getText());

            // Look for any h1, h2, h3 tags
            System.out.println("\n── All header elements ──");
            for (String tag : new String[]{"h1", "h2", "h3"}) {
                List<WebElement> headers = driver2.findElements(By.tagName(tag));
                for (WebElement h : headers) {
                    System.out.println("<" + tag + " class=\"" + h.getAttribute("class") + "\">" + h.getText() + "</" + tag + ">");
                }
            }

            // Look for patient-related elements
            System.out.println("\n── Patient-related elements ──");
            List<WebElement> patientEls = driver2.findElements(By.cssSelector("[class*='patient'], [class*='Patient']"));
            for (WebElement e : patientEls) {
                System.out.println("Tag: " + e.getTagName() + " | Class: " + e.getAttribute("class") + " | Text: " + e.getText().substring(0, Math.min(100, e.getText().length())));
            }

            // Nav items / sidebar
            System.out.println("\n── Navigation/sidebar items ──");
            List<WebElement> navItems = driver2.findElements(By.cssSelector("nav a, .sidebar a, .nav-item, .menu-item, a[routerlink]"));
            for (WebElement n : navItems) {
                System.out.println("Tag: " + n.getTagName() + " | href/routerlink: " + n.getAttribute("routerlink") + " | Text: " + n.getText());
            }

            // Try navigating to patients page explicitly
            System.out.println("\n── Navigating to /patients... ──");
            driver2.get("https://qc.care-connect.health/patients");
            Thread.sleep(5000);
            System.out.println("Current URL: " + driver2.getCurrentUrl());
            System.out.println("Body text:");
            System.out.println(driver2.findElement(By.tagName("body")).getText());

            System.out.println("\n── All header elements on patients page ──");
            for (String tag : new String[]{"h1", "h2", "h3", "h4"}) {
                List<WebElement> headers = driver2.findElements(By.tagName(tag));
                for (WebElement h : headers) {
                    System.out.println("<" + tag + " class=\"" + h.getAttribute("class") + "\"> outerHTML: " + h.getAttribute("outerHTML"));
                }
            }

            // Search input
            System.out.println("\n── Input elements on patients page ──");
            List<WebElement> inputs = driver2.findElements(By.tagName("input"));
            for (WebElement inp : inputs) {
                System.out.println("input type=" + inp.getAttribute("type") + " formControlName=" + inp.getAttribute("formControlName") + " placeholder=" + inp.getAttribute("placeholder") + " class=" + inp.getAttribute("class"));
            }

            // Table rows
            System.out.println("\n── Table rows ──");
            List<WebElement> rows = driver2.findElements(By.cssSelector("tr, .p-datatable-row, [class*='row']"));
            int count = 0;
            for (WebElement r : rows) {
                if (count++ > 10) break;
                System.out.println("Tag: " + r.getTagName() + " | Class: " + r.getAttribute("class") + " | Text: " + r.getText().substring(0, Math.min(150, r.getText().length())));
            }

        } finally {
            driver2.quit();
        }
    }
}
