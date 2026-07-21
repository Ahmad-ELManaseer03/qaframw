package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.CountriesPage;
import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Temporary inspection test to find the Delete action locator for "ZZTEST_DoNotUse".
 * NOT part of testng.xml — run manually with:
 * mvn test -Dtest=InspectCountriesDelete
 */
public class InspectCountriesDelete extends CommonToAllTest {

    @Test
    public void inspectDeleteAction() {
        // 1. Log in
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        System.out.println("Formatting failed_search_dom.txt...");
        try {
            String dom = new String(Files.readAllBytes(Paths.get("target/failed_search_dom.txt")));
            // Add newlines before every tag for easier reading
            dom = dom.replace("><", ">\n<");
            Files.write(Paths.get("target/formatted_failed_dom.txt"), dom.getBytes());
            System.out.println("DOM successfully formatted and saved to target/formatted_failed_dom.txt");
        } catch (Exception e) {
            System.out.println("Error formatting DOM: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
