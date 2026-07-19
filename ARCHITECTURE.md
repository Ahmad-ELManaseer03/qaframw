# Framework Architecture

This document defines the architectural structure of the CareConnect Automation Framework.

## Project Structure
The framework is built using Java, Maven, Selenium WebDriver, TestNG, and Allure Reporting. It strictly follows an Advanced Page Object Model (POM) pattern.

```text
framwork/
├── pom.xml                          # Maven build + dependencies
├── testng.xml                       # TestNG suite configuration
├── src/
│   ├── main/
│   │   ├── java/com/thetestingacademy/
│   │   │   ├── base/
│   │   │   │   └── CommonToAllPage.java      # Base page object
│   │   │   ├── driver/
│   │   │   │   └── DriverManager.java        # WebDriver lifecycle (Singleton)
│   │   │   ├── pages/                        # Page Objects (UI logic)
│   │   │   └── utils/
│   │   │       ├── PropertiesReader.java     # Config reader
│   │   │       └── WaitHelpers.java          # Wait strategies
│   │   └── resources/
│   │       ├── data.properties               # Test data & configuration
│   │       └── log4j2.xml                    # Logging config
│   └── test/java/com/thetestingacademy/
│       ├── base/
│       │   └── CommonToAllTest.java          # Base test class
│       └── tests/                            # Test Classes (Business logic & Assertions)
```

## Separation of Concerns

To ensure stability and scale, the framework enforces a strict separation of concerns:

1. **Page Objects (`src/main/.../pages/`) - The "How"**
   - **Responsibility:** They strictly define *how* to interact with the application UI. They store locators and wrapper methods (clicks, typing).
   - **Rule:** NEVER place TestNG assertions (`Assert.assertEquals`) inside Page Objects. They should only return state (e.g., Strings, booleans).

2. **Test Classes (`src/test/.../tests/`) - The "What"**
   - **Responsibility:** They define *what* we are testing. They contain the business logic, test data injection, and the actual **Assertions**.
   - **Rule:** NEVER place WebElements or WebDriver `findElement` commands here. 

3. **Base Classes (`CommonToAllPage` & `CommonToAllTest`)**
   - **`CommonToAllPage`:** Inherited by all Page Objects. It centralizes reusable UI actions (e.g., `clickElement`, `enterInput`) so no page calls `driver.findElement` directly.
   - **`CommonToAllTest`:** Inherited by all Test Classes. It manages the `@BeforeMethod` (setup driver) and `@AfterMethod` (teardown driver, take screenshot on failure).
