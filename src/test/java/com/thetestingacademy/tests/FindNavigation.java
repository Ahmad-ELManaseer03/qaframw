package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.util.List;

public class FindNavigation extends CommonToAllTest {

    @Test
    public void testFindNavigationLinks() throws InterruptedException {
        System.out.println("Logging in via LoginPage POM...");
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );
        
        System.out.println("Waiting for dashboard...");
        Thread.sleep(8000); // Wait for SPA to fully load
        
        System.out.println("====== SIDEBAR EXTRACTION START ======");
        String js = 
            "let results = [];" +
            "let allLinks = document.querySelectorAll('ul.layout-menu a[href]');" +
            "allLinks.forEach(a => {" +
            "   let href = a.getAttribute('href');" +
            "   if (href === '#' || href.startsWith('javascript')) return;" +
            "   " +
            "   let text = a.querySelector('.layout-menuitem-text')?.textContent.trim() || a.textContent.trim();" +
            "   " +
            "   let moduleName = 'Standalone';" +
            "   let rootLi = a.closest('li.layout-root-menuitem');" +
            "   if (rootLi) {" +
            "       let rootTextEl = rootLi.querySelector('.layout-menuitem-root-text');" +
            "       if (rootTextEl) {" +
            "           moduleName = rootTextEl.textContent.trim();" +
            "       } else {" +
            "           moduleName = rootLi.querySelector('.layout-menuitem-text')?.textContent.trim() || 'Standalone';" +
            "       }" +
            "   }" +
            "   " +
            "   results.push({module: moduleName, page: text, url: href});" +
            "});" +
            "return JSON.stringify(results);";

        try {
            org.openqa.selenium.JavascriptExecutor jsExec = (org.openqa.selenium.JavascriptExecutor) getDriver();
            String jsonResult = (String) jsExec.executeScript(js);
            System.out.println(jsonResult);
        } catch (Exception e) {
            System.err.println("Failed to extract via JS: " + e.getMessage());
        }
        System.out.println("====== SIDEBAR EXTRACTION END ======");
    }
}
