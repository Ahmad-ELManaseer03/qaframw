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
        
        System.out.println("====== LINKS START ======");
        List<WebElement> links = getDriver().findElements(By.cssSelector("a, .p-menuitem-link"));
        for (WebElement link : links) {
            try {
                String text = link.getText().trim();
                String href = link.getAttribute("href");
                if (text.isEmpty()) {
                    text = link.getAttribute("aria-label");
                }
                if (text != null && !text.isEmpty()) {
                    System.out.println("LINK|" + text.replace("\n", " ") + "|" + href);
                }
            } catch (Exception e) {}
        }
        System.out.println("====== LINKS END ======");
    }
}
