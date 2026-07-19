# Locators Strategy

This document defines how we find and interact with UI elements in CareConnect.

## Naming Convention
- Locators MUST be declared as `private By` at the top of the Page Object.
- We use the `By` class instead of `@FindBy` annotations.
- **Why?** `@FindBy` initializes elements early using the PageFactory. In dynamic Single Page Applications (SPAs) like Angular, the DOM changes frequently, leading to `StaleElementReferenceException`. `By` locators resolve the element at the exact moment of interaction.

**Example:**
```java
private By usernameInput = By.cssSelector("input[formControlName='username']");
private By submitBtn     = By.id("login-button");
```

## Strategy Hierarchy
When locating elements, prioritize selectors in this order:
1. **Custom Angular Attributes:** `formControlName`, `ng-reflect-name` (e.g., `input[formControlName='email']`).
2. **Custom Data Attributes:** `data-test-id`, `data-cy`.
3. **ID:** `id="submit"` (If guaranteed unique and static).
4. **CSS Selectors:** Prefer combining classes or hierarchy (e.g., `button.btn-primary`).
5. **XPath:** Use as a last resort, especially for text-based matching (e.g., `//button[text()='Login']`).

## Strict Rule
**NEVER put locators in Test Classes.**
Test classes should have zero knowledge of HTML structure, CSS, or WebDriver commands. If the UI changes, you should only have to update the locator in ONE place: the Page Object.
