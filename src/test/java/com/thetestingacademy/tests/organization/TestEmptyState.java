package com.thetestingacademy.tests.organization;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization.CountriesPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class TestEmptyState extends CommonToAllTest {
    @Test
    public void testEmptyState() throws Exception {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );
        CountriesPage countriesPage = new CountriesPage();
        countriesPage.navigateToCountriesPage();
        
        // Search for a random string
        String randomName = "ZZZNOTHING_" + java.util.UUID.randomUUID().toString();
        countriesPage.searchCountry(randomName);
        
        // Wait a bit to ensure empty state renders
        Thread.sleep(3000);
        
        // Dump the DOM of the table
        String tableHtml = getDriver().findElement(By.tagName("p-table")).getAttribute("outerHTML");
        System.out.println("----- EMPTY TABLE DOM START -----");
        System.out.println(tableHtml);
        System.out.println("----- EMPTY TABLE DOM END -----");
    }
}
