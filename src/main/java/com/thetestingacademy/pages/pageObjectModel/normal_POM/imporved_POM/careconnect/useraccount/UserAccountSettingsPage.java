package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.useraccount;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;

public class UserAccountSettingsPage extends CommonToAllPage {

    // Unique content locator for main area, avoids sidebar links
    private final By headerLoc = By.cssSelector("app-user-settings");

    public UserAccountSettingsPage navigateToUserSettings() {
        // Concrete post-login check
        By dashboardSidebarItem = By.xpath("//*[normalize-space(text())='Patients']");
        WaitHelpers.checkVisibility(getDriver(), dashboardSidebarItem, 15);
        
        getDriver().get("https://qc.care-connect.health/user-settings");
        return this;
    }

    public boolean isPageLoaded() {
        WaitHelpers.checkVisibility(getDriver(), headerLoc, 15);
        String currentUrl = getDriver().getCurrentUrl();
        return currentUrl.contains("/user-settings");
    }
}
