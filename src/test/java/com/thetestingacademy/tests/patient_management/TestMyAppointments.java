package com.thetestingacademy.tests.patient_management;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.patient_management.MyAppointmentsPage;
import com.thetestingacademy.utils.PropertiesReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestMyAppointments extends CommonToAllTest {

    @Test(description = "Verify that the My Appointments page loads correctly")
    public void testMyAppointmentsPageLoads() {
        // 1. Login
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithCredentials(
                PropertiesReader.readKey("username"),
                PropertiesReader.readKey("password")
        );

        // 2. Navigate to My Appointments
        MyAppointmentsPage myAppointmentsPage = new MyAppointmentsPage();
        myAppointmentsPage.navigateToMyAppointments();

        // 3. Assert page loaded
        boolean isLoaded = myAppointmentsPage.isMyAppointmentsPageLoaded();
        Assert.assertTrue(isLoaded, "My Appointments page header should be visible");
        Assert.assertEquals(myAppointmentsPage.getHeaderText(), "Appointments List", "Header text should match 'Appointments List'");
    }
}
