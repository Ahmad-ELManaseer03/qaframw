# Standard Operating Procedure (SOP): Adding New Pages & Tests

Follow this manual when adding new automation coverage to the framework.

## Phase 1: Adding a New Page Object
1. **Create the File:** Under `src/main/java/com/thetestingacademy/pages/`, create a new Java class named after the page (e.g., `ProfilePage.java`).
2. **Inherit Base Class:** The class MUST extend `CommonToAllPage`.
3. **Define Locators:** Add private `By` locators at the top of the class.
4. **Create Action Methods:**
   - Write public methods representing user actions (e.g., `clickSave()`, `getProfileName()`).
   - Use `WaitHelpers` to handle dynamic Angular loading before interacting.
   - Annotate every action method with `@Step("Description of action")` for Allure reporting.

## Phase 2: Adding a New Test Class
1. **Create the File:** Under `src/test/java/com/thetestingacademy/tests/`, create a new test class (e.g., `TestProfile.java`).
2. **Inherit Base Class:** The class MUST extend `CommonToAllTest`.
3. **Define the Test:**
   - Add `@Test` annotation.
   - Add Allure annotations: `@Owner`, `@Description`, `@Severity`.
4. **Implement Workflow:**
   - Read necessary test data using `PropertiesReader`.
   - Instantiate required Page Objects.
   - Call the action methods on the Page Objects.
   - Use `Assert.assertTrue()` or `Assert.assertEquals()` to validate the outcome.

## Example Workflow
```java
// 1. Arrange
ProfilePage profilePage = new ProfilePage();
String expectedName = PropertiesReader.readKey("expected_profile_name");

// 2. Act
profilePage.navigateToProfile();
String actualName = profilePage.getProfileName();

// 3. Assert
Assert.assertEquals(actualName, expectedName, "Profile name mismatch!");
```
