# Locators Strategy

This document defines how we find and interact with UI elements in
CareConnect.

## Naming Convention
- Locators MUST be declared as `private By` at the top of the Page Object.
- We use the `By` class instead of `@FindBy` annotations.
- **Why?** `@FindBy` initializes elements early using the PageFactory. In
  dynamic Single Page Applications (SPAs) like Angular, the DOM changes
  frequently, leading to `StaleElementReferenceException`. `By` locators
  resolve the element at the exact moment of interaction.

**Example:**
```java
private By usernameInput = By.cssSelector("input[formControlName='username']");
private By submitBtn     = By.id("login-button");
```

## Strategy Hierarchy
When locating elements, prioritize selectors in this order:
1. **Custom Angular Attributes:** `formControlName`, `ng-reflect-name` (e.g.,
   `input[formControlName='email']`).
2. **Custom Data Attributes:** `data-test-id`, `data-cy`.
3. **ID:** `id="submit"` (if guaranteed unique and static).
4. **CSS Selectors:** Prefer combining classes or hierarchy (e.g.,
   `button.btn-primary`), or a unique Angular component tag
   (e.g. `app-user-settings`) for content-area checks.
5. **XPath:** Use as a last resort, especially for text-based matching
   (e.g., `//button[text()='Login']`).

## Never Verify a Page With Text That Also Lives in the Sidebar

This was a real bug found during Stage 1: locators/assertions built on text
like `"Settings"` or `"Audit Log"` matched **both** the sidebar navigation
item and the content header — meaning the check passed even when navigation
actually failed. Always scope content-verification locators to something
that only exists in the loaded content area: a specific Angular component
tag, or a data element unique to that page. See `FRAMEWORK_RULES.md`,
Rule 4, for the full explanation and the pages this was fixed on
(`ClaimsPage`, `PaperPrescriptionPage`, and others).

## Strict Rule
**NEVER put locators in Test Classes.**
Test classes should have zero knowledge of HTML structure, CSS, or
WebDriver commands. If the UI changes, you should only have to update the
locator in ONE place: the Page Object.

## Always Verify Live, Never Guess
Before writing any locator, inspect the real rendered DOM (DevTools, or a
temporary throwaway inspection script — deleted once the real Page Object
is done, per `FRAMEWORK_RULES.md` Rule 5). Never assume a locator based on
how a similar-looking page was built; CareConnect's markup is not always
consistent between similar screens.
