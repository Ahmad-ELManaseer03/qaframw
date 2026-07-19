package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.util.List;

public class InspectPageDOM extends CommonToAllTest {

    @Test
    public void dumpPageDOM() throws InterruptedException {
        System.out.println("Logging in...");
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );
        
        System.out.println("Waiting for dashboard...");
        Thread.sleep(5000); 
        
        String targetUrl = "https://qc.care-connect.health/organization/street";
        System.out.println("Navigating to " + targetUrl);
        getDriver().get(targetUrl);
        
        System.out.println("Waiting for page to load...");
        Thread.sleep(8000); 
        
        System.out.println("====== DOM DUMP START ======");
        List<WebElement> headers = getDriver().findElements(By.xpath("//*[contains(text(), 'Street')]"));
        for(WebElement h : headers) {
            try {
                System.out.println("FOUND HEADER: " + h.getAttribute("outerHTML"));
            } catch (Exception e) {}
        }
        
        // Print tables and buttons for completeness
        List<WebElement> tables = getDriver().findElements(By.cssSelector("p-table, table, .p-datatable"));
        for(WebElement t : tables) {
            System.out.println("TABLE: " + t.getAttribute("outerHTML"));
        }
        List<WebElement> buttons = getDriver().findElements(By.cssSelector("button"));
        for(WebElement b : buttons) {
            try {
                System.out.println("BUTTON: " + b.getAttribute("outerHTML"));
            } catch(Exception e){}
        }
        List<WebElement> inputs = getDriver().findElements(By.cssSelector("input"));
        for(WebElement i : inputs) {
            System.out.println("INPUT: " + i.getAttribute("outerHTML"));
        }
        System.out.println("====== DOM DUMP END ======");
    }
}
