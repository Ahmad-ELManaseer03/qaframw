package com.thetestingacademy.tests.careconnect.cdss;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.cdss.CdssTransactionHistoryPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCdssTransactionHistory extends CommonToAllTest {

    @Test
    public void testCdssTransactionHistoryLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        CdssTransactionHistoryPage page = new CdssTransactionHistoryPage();
        page.navigateToCdssTransactionHistory();
        
        Assert.assertTrue(page.isPageLoaded(), "CDSS Transaction History page did not load correctly");
    }
}
