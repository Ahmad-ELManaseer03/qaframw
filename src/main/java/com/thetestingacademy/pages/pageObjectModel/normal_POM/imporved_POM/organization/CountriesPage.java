package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * CountriesPage — Page Object for the Organization > Countries page.
 *
 * DOM Inspection Findings (2026-07-19):
 * - Page Header: <div class="panel-header ..."> Countries List </div>
 *   Locator: By.cssSelector("div.panel-header") (Stable class for panel titles)
 * - Search Input: <input type="text" placeholder="Search...">
 *   Locator: By.cssSelector("input[placeholder='Search...']") (Stable property)
 * - Create Button: <button ...><span class="p-button-label">Create</span></button>
 *   Locator: By.xpath("//button[.//span[text()='Create']]") (Matches button text reliably)
 * - Data Table: <p-table ... data-pc-name="table">
 *   Locator: By.tagName("p-table") (PrimeNG specific tag)
 */
public class CountriesPage extends CommonToAllPage {

    // ── Locators ───────────────────────────────────────────────
    private By pageHeader = By.xpath("//div[contains(@class, 'panel-header') or contains(@class, 'page-header')] | //h1 | //h2[contains(., 'Countr')]");
    
    // Column filter locators
    private By filterMenuButton = By.xpath("//th[@psortablecolumn='englishName']//button[contains(@class, 'p-column-filter-menu-button')]");
    private By filterInput = By.cssSelector(".p-column-filter-overlay input.p-inputtext");
    private By filterApplyButton = By.xpath("//div[contains(@class, 'p-column-filter-overlay')]//button[.//span[text()='Apply']]");
    private By createButton = By.xpath("//button[.//span[contains(translate(text(), 'CREATE', 'create'), 'create') or contains(translate(text(), 'ADD', 'add'), 'add')]] | //button[.//i[contains(@class, 'pi-plus')]] | //p-toolbar//button[contains(@class, 'p-button-success') or contains(@class, 'p-button-primary')]");
    private By dataTable = By.tagName("p-table");

    private By countriesNavLink = By.xpath("//*[normalize-space(text())='Countries']");

    // ── CRUD Locators ──────────────────────────────────────────
    private By arabicNameInput = By.xpath("//label[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'arabic')]/following-sibling::input | //input[@id='arabicName' or @formcontrolname='arabicName' or contains(@placeholder, 'Arabic')]");
    private By englishNameInput = By.xpath("//label[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'english')]/following-sibling::input | //input[@id='englishName' or @formcontrolname='englishName' or contains(@placeholder, 'English')]");
    private By codeInput = By.xpath("//label[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'code')]/following-sibling::input | //input[@id='code' or @formcontrolname='code' or contains(translate(@placeholder, 'CODE', 'code'), 'code')]");
    private By saveButton = By.xpath("//p-dialog//button[.//span[contains(translate(text(), 'SAVE', 'save'), 'save') or contains(translate(text(), 'SUBMIT', 'submit'), 'submit')]] | //button[@type='submit'] | //p-dialog//button[contains(@class, 'p-button-primary')]");
    private By confirmYesButton = By.xpath("//p-confirmdialog//button[.//span[contains(translate(text(), 'YES', 'yes'), 'yes') or contains(translate(text(), 'ACCEPT', 'accept'), 'accept')]] | //button[contains(@class, 'p-confirm-dialog-accept')]");
    private By toastMessage = By.cssSelector("p-toastitem .p-toast-detail, .p-toast-message-text");
    
    // Modal & Validation Locators
    private By validationError = By.cssSelector(".p-error, .invalid-feedback, small.p-error, div.p-error");
    private By modalOverlay = By.cssSelector("p-dialog, .p-dialog-mask");
    private By cancelButton = By.xpath("//p-dialog//button[.//span[contains(translate(text(), 'CANCEL', 'cancel'), 'cancel')]] | //button[contains(@class, 'p-button-secondary')]");

    // ── Page Actions ───────────────────────────────────────────

    @Step("Navigate directly to the Countries page")
    public void navigateToCountriesPage() {
        By dashboardSidebarItem = By.xpath("//*[normalize-space(text())='Patients']");
        WaitHelpers.checkVisibility(getDriver(), dashboardSidebarItem, 15);
        getDriver().get("https://qc.care-connect.health/organization/country");
        // Ensure page has mounted
        WaitHelpers.checkVisibility(getDriver(), dataTable, 15);
    }

    @Step("Get the text of the page header to verify page load")
    public String getPageHeaderText() {
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);
        return getText(pageHeader);
    }

    @Step("Check if the data table is displayed")
    public boolean isDataTableDisplayed() {
        WaitHelpers.checkVisibility(getDriver(), dataTable, 10);
        return getDriver().findElement(dataTable).isDisplayed();
    }

    @Step("Open the Create dialog")
    public void openCreateDialog() {
        WaitHelpers.checkVisibility(getDriver(), createButton, 10);
        clickElement(createButton);
        WaitHelpers.checkVisibility(getDriver(), saveButton, 10);
    }

    @Step("Fill and save the country form")
    public void fillAndSaveCountry(String arabicName, String englishName, String code) {
        WaitHelpers.checkVisibility(getDriver(), arabicNameInput, 10);
        enterInput(arabicNameInput, arabicName);
        enterInput(englishNameInput, englishName);
        
        // Try to fill Code if it exists
        try {
            if (getDriver().findElements(codeInput).size() > 0) {
                enterInput(codeInput, code);
            }
        } catch (Exception e) {
            // Ignore if code is not visible
        }

        // Ensure any previous toast messages (e.g. from login) have cleared
        try { WaitHelpers.waitForElementToBeInvisible(getDriver(), toastMessage, 5); } catch(Exception ignored) {}

        clickElement(saveButton);
    }

    @Step("Search for a country in the data table using column filter")
    public void searchCountry(String countryName) {
        System.out.println("Searching for country using column filter: " + countryName);
        try {
            // 1. Open the filter menu for the English Name column
            WaitHelpers.checkVisibility(getDriver(), filterMenuButton, 10);
            WaitHelpers.waitForClickable(getDriver(), filterMenuButton, 5).click();
            
            // 2. Wait for the overlay to render and enter the country name
            WaitHelpers.checkVisibility(getDriver(), filterInput, 5);
            org.openqa.selenium.WebElement input = getDriver().findElement(filterInput);
            input.clear();
            input.sendKeys(countryName);
            
            // 3. Press Enter to apply the filter
            input.sendKeys(org.openqa.selenium.Keys.ENTER);
            
            // 4. Press Escape to close the overlay and avoid intercepting future clicks
            input.sendKeys(org.openqa.selenium.Keys.ESCAPE);
            
            // 5. Explicitly wait for the overlay to disappear instead of Thread.sleep
            WaitHelpers.waitForElementToBeInvisible(getDriver(), By.cssSelector(".p-column-filter-overlay"), 5);
        } catch (Exception e) {
            System.out.println("Failed to search using column filter: " + e.getMessage());
            // Fallback: The newly created country appears at the top of the table anyway
            // so the test can safely proceed without filtering if it's on page 1.
        }
    }

    @Step("Check if a specific country row exists")
    public boolean isCountryInTable(String uniqueName) {
        By specificRow = By.xpath("//p-table//tbody//tr[td[contains(., '" + uniqueName + "')]]");
        try {
            WaitHelpers.checkVisibility(getDriver(), specificRow, 10);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Country not found: " + uniqueName + ". Dumping DOM to target/failed_isCountryInTable_dom.txt");
            try {
                java.nio.file.Files.write(
                    java.nio.file.Paths.get("target/failed_isCountryInTable_dom.txt"), 
                    getDriver().getPageSource().getBytes()
                );
            } catch (Exception writeEx) {
                System.out.println("Failed to write DOM: " + writeEx.getMessage());
            }
            return false;
        }
    }

    @Step("Click Edit for a specific country row")
    public void clickEditForCountry(String uniqueName) {
        // Strict safety rule: ALWAYS edit the exact row that matches our unique string
        By editButtonForSpecificRow = By.xpath("//p-table//tbody//tr[td[contains(., '" + uniqueName + "')]]//button[contains(@class, 'p-button-primary') or .//*[contains(@class, 'fa-pencil')]]");
        WaitHelpers.checkVisibility(getDriver(), editButtonForSpecificRow, 10);
        clickElement(editButtonForSpecificRow);
        WaitHelpers.checkVisibility(getDriver(), saveButton, 10);
    }

    @Step("Click Delete for a specific country row")
    public void clickDeleteForCountry(String uniqueName) {
        // Strict safety rule: ALWAYS delete the exact row that matches our unique string
        By deleteButtonForSpecificRow = By.xpath("//p-table//tbody//tr[td[contains(., '" + uniqueName + "')]]//button[contains(@class, 'p-button-danger') or .//*[contains(@class, 'pi-trash')]]");
        WaitHelpers.checkVisibility(getDriver(), deleteButtonForSpecificRow, 10);
        clickElement(deleteButtonForSpecificRow);
    }

    @Step("Confirm the delete action")
    public void confirmDelete() {
        WaitHelpers.checkVisibility(getDriver(), confirmYesButton, 10);
        clickElement(confirmYesButton);
    }

    @Step("Get Toast Message Text")
    public String getToastMessage() {
        WaitHelpers.checkVisibility(getDriver(), toastMessage, 10);
        return getText(toastMessage);
    }

    // ── Modal & Validation Actions ─────────────────────────────
    
    @Step("Check if a modal dialog is currently open")
    public boolean isModalOpen() {
        return WaitHelpers.waitForOptionalElement(getDriver(), modalOverlay, 2);
    }

    @Step("Click the Cancel button in the modal dialog")
    public void clickCancelButton() {
        WaitHelpers.checkVisibility(getDriver(), cancelButton, 5);
        clickElement(cancelButton);
        WaitHelpers.waitForElementToBeInvisible(getDriver(), modalOverlay, 5);
    }

    @Step("Close the modal dialog if it is open")
    public void closeModalIfOpen() {
        if (isModalOpen()) {
            clickCancelButton();
        }
    }

    @Step("Get validation error texts from the form")
    public java.util.List<String> getValidationErrors() {
        java.util.List<String> errors = new java.util.ArrayList<>();
        if (WaitHelpers.waitForOptionalElement(getDriver(), validationError, 2)) {
            java.util.List<org.openqa.selenium.WebElement> errorElements = getDriver().findElements(validationError);
            for (org.openqa.selenium.WebElement el : errorElements) {
                String text = el.getText().trim();
                if (!text.isEmpty()) {
                    errors.add(text);
                }
            }
        }
        return errors;
    }
}
