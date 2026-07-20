package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class InspectPrescribing5 extends CommonToAllTest {

    @Test
    public void testRedirectFlow() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
            PropertiesReader.readKey("username"),
            PropertiesReader.readKey("password")
        );
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        inspectPageWithRedirectFlow("https://qc.care-connect.health/prescribe-management/prescriptions", "Prescriptions");
        inspectPageWithRedirectFlow("https://qc.care-connect.health/prescribe-management/authorization-list", "Prescribe Auth");
        inspectPageWithRedirectFlow("https://qc.care-connect.health/prescribe-management/expired-prescriptions", "Expired Prescriptions");
    }
    
    private void inspectPageWithRedirectFlow(String url, String label) {
        System.out.println("\n\n========== INSPECTING: " + label + " ==========");
        getDriver().navigate().to(url);
        
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        
        String currentUrl = getDriver().getCurrentUrl();
        if (!currentUrl.contains(url)) {
            System.out.println(">>> Redirected to: " + currentUrl);
            try {
                By selectFacilityBtn = By.xpath("//button[normalize-space(text())='Select Facility']");
                if (WaitHelpers.waitForOptionalElement(getDriver(), selectFacilityBtn, 3)) {
                    System.out.println(">>> HANDLING MODAL AFTER REDIRECT...");
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
                    
                    System.out.println(">>> NAVIGATING AGAIN to: " + url);
                    getDriver().navigate().to(url);
                    try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
                }
            } catch (Exception e) {}
        }
        
        System.out.println("FINAL URL: " + getDriver().getCurrentUrl());
        
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
