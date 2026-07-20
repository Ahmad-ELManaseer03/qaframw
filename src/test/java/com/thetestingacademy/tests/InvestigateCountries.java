package com.thetestingacademy.tests;

import com.thetestingacademy.base.CommonToAllTest;
import com.thetestingacademy.pages.pageObjectModel.normal_POM.imporved_POM.careconnect.LoginPage;
import com.thetestingacademy.utils.PropertiesReader;
import com.thetestingacademy.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class InvestigateCountries extends CommonToAllTest {

    private String reportPath = "investigation/countries-add/report.txt";
    private FileWriter writer;

    @Test
    public void runInvestigation() throws Exception {
        new File("investigation/countries-add").mkdirs();
        writer = new FileWriter(reportPath);

        try {
            // 0. Login and navigate
            LoginPage loginPage = new LoginPage();
            loginPage.loginWithCredentials(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));
            WaitHelpers.checkVisibility(getDriver(), By.xpath("//*[normalize-space(text())='Patients']"), 15);
            getDriver().get("https://qc.care-connect.health/organization/country");
            Thread.sleep(5000); // let table load
            
            // 1. Add Form
            getDriver().findElement(By.xpath("//button[.//span[text()='Create'] or .//span[text()='Add'] or contains(@class, 'p-button-success')]")).click();
            Thread.sleep(2000);
            captureScreenshot("1_add_form.png");
            
            writeReport("=== 1. ADD FORM FIELDS ===");
            List<WebElement> labels = getDriver().findElements(By.cssSelector("label"));
            for (WebElement label : labels) {
                String text = label.getText();
                boolean isRequired = text.contains("*");
                // Find input next to or associated with label
                String forAttr = label.getAttribute("for");
                String inputType = "unknown";
                if (forAttr != null && !forAttr.isEmpty()) {
                    List<WebElement> inputs = getDriver().findElements(By.id(forAttr));
                    if (!inputs.isEmpty()) {
                        inputType = inputs.get(0).getTagName();
                        if (inputType.equals("input")) {
                            inputType += " type=" + inputs.get(0).getAttribute("type");
                        }
                    }
                }
                writeReport("Field: " + text + " | Required: " + isRequired + " | Type: " + inputType);
            }

            // 2. Submit Empty
            writeReport("\n=== 2. VALIDATION ERRORS ===");
            List<WebElement> footerBtns = getDriver().findElements(By.cssSelector(".p-dialog-footer button, .p-dialog .p-button"));
            for(WebElement b : footerBtns) {
                if (b.isDisplayed() && (b.getText().contains("Save") || b.getText().contains("Submit") || b.getText().contains("Create"))) {
                    b.click();
                    break;
                }
            }
            Thread.sleep(1000);
            captureScreenshot("2_validation_errors.png");
            List<WebElement> errors = getDriver().findElements(By.cssSelector(".p-error, .error-text, small.ng-invalid, .text-danger"));
            for (WebElement error : errors) {
                if (error.isDisplayed()) {
                    writeReport("Error text: " + error.getText());
                }
            }

            // 3. Fill required fields
            writeReport("\n=== 3. SUCCESS MESSAGE ===");
            List<WebElement> textInputs = getDriver().findElements(By.cssSelector(".p-dialog input[type='text']"));
            for(WebElement input : textInputs) {
                if(input.isDisplayed() && input.isEnabled()) {
                    input.sendKeys("ZZTEST_DoNotUse");
                }
            }
            footerBtns = getDriver().findElements(By.cssSelector(".p-dialog-footer button, .p-dialog .p-button"));
            for(WebElement b : footerBtns) {
                if (b.isDisplayed() && (b.getText().contains("Save") || b.getText().contains("Submit") || b.getText().contains("Create"))) {
                    b.click();
                    break;
                }
            }
            Thread.sleep(1500);
            captureScreenshot("3_success_toast.png");
            
            List<WebElement> toasts = getDriver().findElements(By.cssSelector(".p-toast-message-content"));
            for(WebElement t : toasts) {
                writeReport("Toast text: " + t.getText().replaceAll("\n", " | "));
            }
            
            Thread.sleep(3000); // wait for toast to fade and table to reload

            boolean cleanupNeeded = true;
            try {
                // Refresh page to clear any stuck modals before continuing
                getDriver().navigate().refresh();
                Thread.sleep(5000);

                // 4. Edit form
                writeReport("\n=== 4. EDIT FORM ===");
                List<WebElement> searchBoxes = getDriver().findElements(By.cssSelector("input[placeholder*='earch']"));
                if (!searchBoxes.isEmpty()) {
                    searchBoxes.get(0).clear();
                    searchBoxes.get(0).sendKeys("ZZTEST_DoNotUse");
                    Thread.sleep(2000);
                } else {
                    writeReport("NO SEARCH BOX FOUND");
                }
                
                List<WebElement> editButtons = getDriver().findElements(By.xpath("//button[.//span[contains(@class, 'fa-pencil')]]"));
                if (!editButtons.isEmpty()) {
                    editButtons.get(0).click();
                    Thread.sleep(2000);
                    captureScreenshot("4_edit_form.png");
                    writeReport("Opened Edit Form. Inspecting readonly fields...");
                    List<WebElement> editInputs = getDriver().findElements(By.cssSelector("input"));
                    for(WebElement input : editInputs) {
                        if (input.isDisplayed()) {
                            writeReport("Input tag: input | type=" + input.getAttribute("type") + " | readonly=" + input.getAttribute("readonly") + " | disabled=" + input.getAttribute("disabled"));
                        }
                    }
                    List<WebElement> cancelBtns = getDriver().findElements(By.xpath("//button[.//span[text()='Cancel'] or contains(@class, 'p-button-secondary')]"));
                    if (!cancelBtns.isEmpty()) cancelBtns.get(0).click();
                    Thread.sleep(1000);
                } else {
                    writeReport("Could not find Edit button for ZZTEST_DoNotUse");
                }
                
                // 6. Search empty state
                writeReport("\n=== 6. SEARCH EMPTY STATE ===");
                searchBoxes = getDriver().findElements(By.cssSelector("input[placeholder*='earch']"));
                if (!searchBoxes.isEmpty()) {
                    searchBoxes.get(0).clear();
                    searchBoxes.get(0).sendKeys("NO_MATCH_SEARCH_XYZ");
                    Thread.sleep(2000);
                    captureScreenshot("7_search_empty.png");
                    List<WebElement> emptyMessages = getDriver().findElements(By.cssSelector(".p-datatable-emptymessage, td[colspan]"));
                    if(!emptyMessages.isEmpty()) {
                        writeReport("Empty state text: " + emptyMessages.get(0).getText());
                    }
                    searchBoxes.get(0).clear();
                    Thread.sleep(2000);
                }

                // 7. Audit Log and Export
                writeReport("\n=== 7. AUDIT LOG & EXPORT ===");
                List<WebElement> auditBtns = getDriver().findElements(By.xpath("//button[.//span[contains(text(), 'Audit Log')]]"));
                if (!auditBtns.isEmpty()) {
                    writeReport("Found Audit Log button, clicking...");
                    auditBtns.get(0).click();
                    Thread.sleep(2000);
                    captureScreenshot("8_audit_log.png");
                    List<WebElement> closeBtns = getDriver().findElements(By.xpath("//button[.//span[contains(@class, 'pi-times')]]"));
                    if (!closeBtns.isEmpty()) closeBtns.get(0).click();
                    Thread.sleep(1000);
                } else {
                    writeReport("No Audit Log button found");
                }
                
                List<WebElement> exportBtns = getDriver().findElements(By.xpath("//button[contains(., 'Export') or contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'export')]"));
                if (!exportBtns.isEmpty()) {
                    writeReport("Found Export button.");
                } else {
                    writeReport("No Export button found");
                }
                
            } finally {
                if (cleanupNeeded) {
                    writeReport("\n=== 5. DELETE DIALOG & CLEANUP ===");
                    getDriver().navigate().refresh();
                    Thread.sleep(5000);

                    boolean found = false;
                    int maxPages = 10;
                    int pageCount = 1;
                    while(pageCount <= maxPages) {
                        List<WebElement> rows = getDriver().findElements(By.cssSelector("tr"));
                        for (WebElement row : rows) {
                            if (row.getText().contains("ZZTEST_DoNotUse")) {
                                writeReport("Found row with ZZTEST_DoNotUse!");
                                found = true;
                                List<WebElement> deleteBtn = row.findElements(By.xpath(".//button[.//span[contains(@class, 'pi-trash')]]"));
                                if (!deleteBtn.isEmpty()) {
                                    deleteBtn.get(0).click();
                                    Thread.sleep(1000);
                                    captureScreenshot("5_delete_dialog.png");
                                    
                                    List<WebElement> dialogMsgs = getDriver().findElements(By.cssSelector(".p-confirm-dialog-message"));
                                    if (!dialogMsgs.isEmpty()) {
                                        writeReport("Delete confirmation text: " + dialogMsgs.get(0).getText());
                                    }
                                    
                                    List<WebElement> confirmBtns = getDriver().findElements(By.xpath("//button[.//span[text()='Yes'] or .//span[text()='Confirm']]"));
                                    if (!confirmBtns.isEmpty()) confirmBtns.get(0).click();
                                    Thread.sleep(1500);
                                    captureScreenshot("6_delete_success.png");
                                    
                                    List<WebElement> delToasts = getDriver().findElements(By.cssSelector(".p-toast-message-content"));
                                    for(WebElement t : delToasts) {
                                        writeReport("Delete success toast: " + t.getText().replaceAll("\n", " | "));
                                    }
                                    writeReport("Cleanup successful.");
                                }
                                break;
                            }
                        }
                        if (found) break;
                        
                        // try to go to next page
                        List<WebElement> nextBtn = getDriver().findElements(By.cssSelector(".p-paginator-next"));
                        if (!nextBtn.isEmpty() && !nextBtn.get(0).getAttribute("class").contains("p-disabled")) {
                            nextBtn.get(0).click();
                            pageCount++;
                            Thread.sleep(2000);
                        } else {
                            break; // end of pages
                        }
                    }
                    if (!found) {
                        writeReport("Cleanup FAILED - Could not find ZZTEST_DoNotUse in table");
                    }
                }
            }

        } finally {
            writer.close();
        }
    }

    private void captureScreenshot(String fileName) throws IOException {
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        File dest = new File("investigation/countries-add/" + fileName);
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private void writeReport(String text) throws IOException {
        System.out.println(text);
        writer.write(text + "\n");
        writer.flush();
    }
}
