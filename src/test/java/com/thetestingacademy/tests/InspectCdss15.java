package com.thetestingacademy.tests;

import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import com.thetestingacademy.base.CommonToAllTest;

public class InspectCdss15 extends CommonToAllTest {
    @Test
    public void inspect() throws InterruptedException {
        com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage loginPage = new com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage();
        loginPage.loginWithCredentials(
                com.thetestingacademy.utils.PropertiesReader.readKey("username"),
                com.thetestingacademy.utils.PropertiesReader.readKey("password")
        );
        Thread.sleep(8000);

        try {
            WebElement cdssMenu = getDriver().findElement(By.xpath("//a[.//span[normalize-space(text())='CDSS']]"));
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", cdssMenu);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", cdssMenu);
            Thread.sleep(1000);
        } catch (Exception e) {}

        WebElement link = getDriver().findElement(By.xpath("//a[@href='/cdss-management/simulator']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", link);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", link);

        Thread.sleep(4000);

        System.out.println("====== PAGE CONTENT ======");
        java.util.List<WebElement> headers = getDriver().findElements(By.xpath("//h1 | //h2 | //h3 | //h4 | //h5 | //th | //div[contains(@class, 'title') or contains(@class, 'header')]"));
        for (WebElement h : headers) {
            String text = h.getText().trim();
            if (!text.isEmpty()) {
                System.out.println("HEADER/TITLE: " + text);
            }
        }
        System.out.println("====== END PAGE CONTENT ======");
        System.out.println("PAGE URL: " + getDriver().getCurrentUrl());
    }
}
