package com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.organization;

import com.thetestingacademy.base.CommonToAllPage;
import com.thetestingacademy.utils.WaitHelpers;
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

    // ── Page Actions ───────────────────────────────────────────

    public void navigateToCountriesPage() {
        By dashboardSidebarItem = By.xpath("//*[normalize-space(text())='Patients']");
        WaitHelpers.checkVisibility(getDriver(), dashboardSidebarItem, 15);
        getDriver().get("https://qc.care-connect.health/organization/country");
        // Ensure page has mounted
        WaitHelpers.checkVisibility(getDriver(), dataTable, 15);
    }

    public String getPageHeaderText() {
        WaitHelpers.checkVisibility(getDriver(), pageHeader, 15);
        return getText(pageHeader);
    }

    public boolean isDataTableDisplayed() {
        WaitHelpers.checkVisibility(getDriver(), dataTable, 10);
        return getDriver().findElement(dataTable).isDisplayed();
    }

    public void openCreateDialog() {
        // Ensure any previous toast messages (e.g. from login or previous actions) have cleared
        try { WaitHelpers.waitForElementToBeInvisible(getDriver(), toastMessage, 5); } catch(Exception ignored) {}
        
        WaitHelpers.checkVisibility(getDriver(), createButton, 10);
        
        // Attempt to click, fallback to JS click if intercepted
        try {
            clickElement(createButton);
        } catch (Exception e) {
            org.openqa.selenium.WebElement btn = getDriver().findElement(createButton);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", btn);
        }
        WaitHelpers.checkVisibility(getDriver(), saveButton, 10);
    }

    public void fillAndSaveCountry(String arabicName, String englishName, String code) {
        WaitHelpers.checkVisibility(getDriver(), arabicNameInput, 10);
        
        org.openqa.selenium.WebElement arInput = getDriver().findElement(arabicNameInput);
        arInput.clear();
        arInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
        arInput.sendKeys(arabicName);
        
        org.openqa.selenium.WebElement enInput = getDriver().findElement(englishNameInput);
        enInput.clear();
        enInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
        enInput.sendKeys(englishName);
        
        // Try to fill Code if it exists
        try {
            java.util.List<org.openqa.selenium.WebElement> codeElements = getDriver().findElements(codeInput);
            if (!codeElements.isEmpty()) {
                org.openqa.selenium.WebElement cdInput = codeElements.get(0);
                if (cdInput.isDisplayed() && cdInput.isEnabled()) {
                    cdInput.clear();
                    cdInput.sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"), org.openqa.selenium.Keys.BACK_SPACE);
                    cdInput.sendKeys(code);
                } else {
                    System.out.println("⚠️ Code field is present but hidden or disabled (likely Edit mode). Skipping code update.");
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Warning: Swallowed exception while attempting to fill Code field: " + e.getMessage());
        }

        // Ensure any previous toast messages (e.g. from login) have cleared
        try { WaitHelpers.waitForElementToBeInvisible(getDriver(), toastMessage, 5); } catch(Exception ignored) {}

        clickElement(saveButton);
        
        // Wait for the dialog to fully close so it doesn't intercept subsequent actions
        try { WaitHelpers.waitForElementToBeInvisible(getDriver(), By.tagName("p-dialog"), 5); } catch(Exception ignored) {}
    }

    public void searchCountry(String countryName) {
        System.out.println("Searching for country using column filter: " + countryName);
        try {
            // 1. Ensure overlay is closed from any previous actions
            try { WaitHelpers.waitForElementToBeInvisible(getDriver(), By.cssSelector(".p-column-filter-overlay"), 3); } catch(Exception ignored) {}

            // 2. Open the filter menu
            WaitHelpers.checkVisibility(getDriver(), filterMenuButton, 10);
            WaitHelpers.waitForClickable(getDriver(), filterMenuButton, 5).click();
            
            // 3. Wait for the overlay to render and enter the country name
            WaitHelpers.checkVisibility(getDriver(), filterInput, 5);
            
            // Robust clear for Angular/PrimeNG
            getDriver().findElement(filterInput).sendKeys(org.openqa.selenium.Keys.chord(org.openqa.selenium.Keys.CONTROL, "a"));
            getDriver().findElement(filterInput).sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
            getDriver().findElement(filterInput).sendKeys(countryName);
            
            // 4. Click the Apply button (PrimeNG standard) or fallback to Enter
            By applyBtnLocator = By.xpath("//div[contains(@class, 'p-column-filter-overlay')]//button[.//span[text()='Apply'] or @aria-label='Apply']");
            try {
                org.openqa.selenium.WebElement applyBtn = WaitHelpers.waitForClickable(getDriver(), applyBtnLocator, 5);
                applyBtn.click();
            } catch (Exception e) {
                System.out.println("⚠️ Apply button not found/clickable in overlay! Falling back to Enter.");
                getDriver().findElement(filterInput).sendKeys(org.openqa.selenium.Keys.ENTER);
            }
            
            // 5. Press Escape on the body to ensure overlay closes
            try { getDriver().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE); } catch(Exception e) {}
            
            // 6. Wait for the overlay and table loading mask to disappear
            try { WaitHelpers.waitForElementToBeInvisible(getDriver(), By.cssSelector(".p-column-filter-overlay"), 5); } catch(Exception e) {}
            try { WaitHelpers.waitForElementToBeInvisible(getDriver(), By.cssSelector(".p-datatable-loading-overlay"), 5); } catch(Exception e) {}
            
            // 7. Data table settling is handled by subsequent step's explicit waits (e.g. waiting for specific row)
        } catch (Exception e) {
            System.out.println("Failed to search using column filter: " + e.getMessage());
            throw new RuntimeException("Failed to search using column filter", e);
        }
    }

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

    public void clickEditForCountry(String uniqueName) {
        // Strict safety rule: ALWAYS edit the exact row that matches our unique string
        By editButtonForSpecificRow = By.xpath("//p-table//tbody//tr[td[contains(., '" + uniqueName + "')]]//button[contains(@class, 'p-button-primary') or .//*[contains(@class, 'fa-pencil')]]");
        WaitHelpers.checkVisibility(getDriver(), editButtonForSpecificRow, 10);
        clickElement(editButtonForSpecificRow);
        WaitHelpers.checkVisibility(getDriver(), saveButton, 10);
    }

    public void clickDeleteForCountry(String uniqueName) {
        // Strict safety rule: ALWAYS delete the exact row that matches our unique string
        By deleteButtonForSpecificRow = By.xpath("//p-table//tbody//tr[td[contains(., '" + uniqueName + "')]]//button[contains(@class, 'p-button-danger') or .//*[contains(@class, 'pi-trash')]]");
        WaitHelpers.checkVisibility(getDriver(), deleteButtonForSpecificRow, 10);
        clickElement(deleteButtonForSpecificRow);
    }

    public void confirmDelete() {
        WaitHelpers.checkVisibility(getDriver(), confirmYesButton, 10);
        clickElement(confirmYesButton);
    }

    public String getToastMessage() {
        WaitHelpers.checkVisibility(getDriver(), toastMessage, 10);
        return getText(toastMessage);
    }

    public void clickSave() {
        WaitHelpers.checkVisibility(getDriver(), saveButton, 5);
        clickElement(saveButton);
    }

    public boolean areValidationErrorsDisplayed() {
        By errorLocators = By.cssSelector(".p-error, .text-danger, .invalid-feedback, mat-error");
        try {
            WaitHelpers.checkVisibility(getDriver(), errorLocators, 5);
            return getDriver().findElements(errorLocators).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void closeDialog() {
        By closeIcon = By.cssSelector("p-dialog .p-dialog-header-close, button.p-dialog-header-close, .p-dialog-header-icon");
        try {
            if (getDriver().findElements(closeIcon).size() > 0) {
                clickElement(closeIcon);
            } else {
                getDriver().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
            }
        } catch (Exception e) {
            try { getDriver().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE); } catch (Exception ignored) {}
        }
        
        // Explicitly wait for dialog to vanish so it doesn't intercept future actions
        try { WaitHelpers.waitForElementToBeInvisible(getDriver(), By.tagName("p-dialog"), 5); } catch(Exception ignored) {}
    }

    public boolean isTableEmptyStateDisplayed() {
        try {
            org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(getDriver(), java.time.Duration.ofSeconds(5));
            return wait.until(driver -> {
                By dataRows = By.cssSelector("p-table tbody.p-datatable-tbody > tr");
                int rowCount = driver.findElements(dataRows).size();
                
                By paginatorCurrent = By.cssSelector(".p-paginator-current");
                boolean paginatorShowsEmpty = false;
                if (driver.findElements(paginatorCurrent).size() > 0) {
                    String text = driver.findElement(paginatorCurrent).getText();
                    paginatorShowsEmpty = text.contains("0 to 0");
                }
                return rowCount == 0 || paginatorShowsEmpty;
            });
        } catch(Exception e) {
            return false;
        }
    }
}
