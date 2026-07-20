package com.thetestingacademy.tests.formulary;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.formulary.GenericsPage;
import com.thetestingacademy.utils.PropertiesReader;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestGenerics extends CommonToAllTest {

    @Test(priority = 1, groups = {"smoke"})
    @Owner("Ahmad")
    @Description("Verify that the Generics List page loads correctly with header 'Generics List' and exact URL")
    public void testGenericsPageLoads() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        GenericsPage genericsPage = new GenericsPage();
        Assert.assertTrue(genericsPage.isGenericsPageLoaded(),
                "Generics List page failed to load — header 'Generics List' not found or URL mismatch.");
    }
}
