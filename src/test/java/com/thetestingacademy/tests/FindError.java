package com.thetestingacademy.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindError {
    @org.testng.annotations.Test
    public void findErrorTest() throws Exception {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 1);
        chromeOptions.setExperimentalOption("prefs", prefs);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        
        try {
            driver.get("https://qc.care-connect.health/login");
            Thread.sleep(3000);
            
            // Login with invalid
            driver.findElement(By.cssSelector("input[formControlName='username']")).sendKeys("invalid@example.com");
            driver.findElement(By.cssSelector("input[type='password']")).sendKeys("WrongPass123");
            driver.findElement(By.cssSelector("button[type='submit'].btn-login")).click();
            
            System.out.println("Clicked login. Waiting for error...");
            for (int i = 0; i < 20; i++) {
                Thread.sleep(1000);
                List<WebElement> elements = driver.findElements(By.xpath("//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'invalid')]"));
                if (!elements.isEmpty()) {
                    System.out.println("FOUND ERROR ELEMENT!");
                    for (WebElement e : elements) {
                        System.out.println("Tag: " + e.getTagName());
                        System.out.println("Class: " + e.getAttribute("class"));
                        System.out.println("Text: " + e.getText());
                        System.out.println("Outer HTML: " + e.getAttribute("outerHTML"));
                    }
                    break;
                }
            }
            
            // Also print any p-toast or p-message
            List<WebElement> toasts = driver.findElements(By.tagName("p-toast"));
            System.out.println("Toasts found: " + toasts.size());
            for(WebElement t : toasts) {
                System.out.println("Toast HTML: " + t.getAttribute("outerHTML"));
            }

            List<WebElement> messages = driver.findElements(By.tagName("p-messages"));
            System.out.println("Messages found: " + messages.size());
            for(WebElement m : messages) {
                System.out.println("Message HTML: " + m.getAttribute("outerHTML"));
            }

        } finally {
            driver.quit();
        }
    }
}
