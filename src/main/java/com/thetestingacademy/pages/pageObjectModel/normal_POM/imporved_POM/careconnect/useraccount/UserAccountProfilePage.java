package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.useraccount;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;

public class UserAccountProfilePage extends CommonToAllPage {

    // Unique content locator for main area, avoids sidebar links
    private final By headerLoc = By.xpath("//div[contains(@class, 'card')]//*[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'profile')] | //form");

    public UserAccountProfilePage navigateToProfile() {
        // Concrete post-login check
        By dashboardSidebarItem = By.xpath("//*[normalize-space(text())='Patients']");
        WaitHelpers.checkVisibility(getDriver(), dashboardSidebarItem, 15);
        
        getDriver().get("https://qc.care-connect.health/profile");
        return this;
    }

    public boolean isPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), headerLoc, 15);
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/profile");
    }
}
