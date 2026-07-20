package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class DOMInspector extends CommonToAllTest {
    @Test
    public void inspect() throws InterruptedException {
        String url = System.getProperty("inspectUrl", "/organization/facility");
        System.out.println("Logging in...");
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );
        Thread.sleep(8000);
        
        System.out.println("Navigating to: " + url);
        getDriver().get("https://qc.care-connect.health" + url);
        Thread.sleep(8000);
        
        try {
            WebElement header = getDriver().findElement(By.cssSelector("div.panel-header"));
            System.out.println("====== HEADER START ======");
            System.out.println(header.getText());
            System.out.println("====== HEADER END ======");
        } catch (Exception e) {
            System.err.println("Error extracting DOM: " + e.getMessage());
        }
    }
}
