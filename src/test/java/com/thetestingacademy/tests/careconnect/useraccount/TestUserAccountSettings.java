package com.thetestingacademy.tests.careconnect.useraccount;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.useraccount.UserAccountSettingsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestUserAccountSettings extends CommonToAllTest {

    @Test
    public void testUserAccountSettingsPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        UserAccountSettingsPage page = new UserAccountSettingsPage();
        page.navigateToUserSettings();
        
        try {
            Thread.sleep(3000);
            java.nio.file.Files.writeString(java.nio.file.Paths.get("target/dom_dump.txt"), page.getDriver().findElement(org.openqa.selenium.By.tagName("body")).getAttribute("innerHTML"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(page.isPageLoaded(), "User Account Settings page did not load correctly");
    }
}
