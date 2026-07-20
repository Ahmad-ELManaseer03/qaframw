package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class InspectPrescribing6 extends CommonToAllTest {

    @Test
    public void testRedirectButtons() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
            PropertiesReader.readKey("username"),
            PropertiesReader.readKey("password")
        );
        WaitHelpers.checkVisibility(getDriver(), By.cssSelector("ul.layout-menu"), 15);

        System.out.println("\n\n========== INSPECTING Prescriptions ==========");
        getDriver().navigate().to("https://qc.care-connect.health/prescribe-management/prescriptions");
        
        try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
        
        System.out.println("URL: " + getDriver().getCurrentUrl());
        
        System.out.println(">>> BUTTONS:");
        for (WebElement btn : getDriver().findElements(By.tagName("button"))) {
            System.out.println("BUTTON: '" + btn.getText().replaceAll("\\s+", " ").trim() + "' [class=" + btn.getAttribute("class") + "]");
        }
        System.out.println(">>> INPUTS:");
        for (WebElement input : getDriver().findElements(By.tagName("input"))) {
            System.out.println("INPUT: [type=" + input.getAttribute("type") + "] [value=" + input.getAttribute("value") + "]");
        }
        System.out.println(">>> TEXT:");
        for (WebElement el : getDriver().findElements(By.xpath("//*[normalize-space(text())='Select Current Facility']"))) {
             System.out.println("FOUND Select Current Facility: " + el.getTagName() + " [class=" + el.getAttribute("class") + "]");
             // print parent tags
             WebElement p = el.findElement(By.xpath(".."));
             System.out.println("  PARENT: " + p.getTagName() + " [class=" + p.getAttribute("class") + "]");
        }
    }
}
