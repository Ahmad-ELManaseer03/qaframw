# Standard Operating Procedure (SOP): Adding New Pages & Tests

Follow this manual when adding new automation coverage to the framework.

## Phase 1: Adding a New Page Object
1. **Inspect the real DOM first.** Never guess a locator from a similar
   page — see `FRAMEWORK_RULES.md`, Rule 1.
2. **Create the File:** Under
   `src/main/java/com/thetestingacademy/pages/pageObjectModel/normal_POM/imporved_POM/...`,
   in the appropriate module package, create a new Java class named after
   the page (e.g. `ProfilePage.java`). Use a specific, collision-free name
   — see `FRAMEWORK_RULES.md`, Rule 6.
3. **Inherit Base Class:** The class MUST extend `CommonToAllPage`.
4. **Define Locators:** Add private `By` locators at the top of the class,
   following `LOCATORS_STRATEGY.md`.
5. **Create Action Methods:**
   - Write public methods representing user actions (e.g., `clickSave()`,
     `getProfileName()`).
   - Use `WaitHelpers` to handle dynamic Angular loading before interacting
     — never `Thread.sleep()` (`FRAMEWORK_RULES.md`, Rule 2).
   - Annotate every action method with `@Step("Description of action")` for
     Allure reporting.
6. **`isPageLoaded()`:** its final content-visibility check must be allowed
   to throw, never wrapped in a silent try/catch (`FRAMEWORK_RULES.md`,
   Rule 3), and must not key off text duplicated in the sidebar nav
   (Rule 4).

## Phase 2: Adding a New Test Class
1. **Create the File:** Under
   `src/test/java/com/thetestingacademy/tests/...`, mirroring the page
   object's package, create a new test class (e.g. `TestProfile.java`).
2. **Inherit Base Class:** The class MUST extend `CommonToAllTest`.
3. **Define the Test:**
   - Add `@Test` annotation.
   - Add Allure annotations where useful: `@Description`, `@Severity`.
4. **Implement Workflow:**
   - Read necessary test data using `PropertiesReader`.
   - Instantiate required Page Objects.
   - Call the action methods on the Page Objects.
   - Use `Assert.assertTrue()` / `Assert.assertEquals()` to validate the
     outcome.
5. **Register it in `testng.xml`.** A test class that isn't registered
   doesn't run as part of the suite — add the `<class name="..."/>` entry
   under `testng.xml` (`FRAMEWORK_RULES.md`, Rule 8).
6. **Run it for real** and confirm the actual surefire output
   (`Tests run: X, Failures: Y`) — never claim a test passes without
   running it (`FRAMEWORK_RULES.md`, Rule 7).
7. **Clean up.** Delete any temporary DOM-inspection script you used while
   building the page object (`FRAMEWORK_RULES.md`, Rule 5).

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

## For Stage 2 (CRUD) Work Specifically

When adding depth to an existing module (see `ROADMAP.md`, Stage 2), each
screen typically needs separate test methods (or classes) for: **Add**
(with required-field validation), **Edit**, **Delete** (safe, disposable
test data only), **Search/Filter**, and relevant **edge cases** (empty
states, validation messages, duplicates). Confirm the target module with
Ahmad before starting — see `ROADMAP.md`, Stage 2.
