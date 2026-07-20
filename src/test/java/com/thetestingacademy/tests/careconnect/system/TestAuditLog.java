package com.thetestingacademy.tests.careconnect.system;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.system.AuditLogPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestAuditLog extends CommonToAllTest {

    @Test
    public void testAuditLogPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        AuditLogPage page = new AuditLogPage();
        page.navigateToAuditLog();
        
        Assert.assertTrue(page.isPageLoaded(), "Audit Log page did not load correctly");
    }
}
