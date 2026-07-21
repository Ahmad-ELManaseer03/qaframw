# Framework Architecture

This document defines the architectural structure of the CareConnect
Automation Framework, as it actually exists in this repository.

## Project Structure

```text
qaframw/
├── pom.xml                          # Maven build + dependencies
├── testng.xml                       # The single TestNG suite (60 registered classes)
├── src/
│   ├── main/
│   │   ├── java/com/thetestingacademy/
│   │   │   ├── base/
│   │   │   │   └── CommonToAllPage.java      # Base page object — shared UI actions
│   │   │   ├── driver/
│   │   │   │   └── DriverManager.java        # ThreadLocal WebDriver lifecycle
│   │   │   ├── pages/pageObjectModel/normal_POM/imporved_POM/
│   │   │   │   ├── careconnect/              # LoginPage, cdss/, dispensing/, prescribing/,
│   │   │   │   │                              #   ruleEngine/, system/, useraccount/, homeDelivery/,
│   │   │   │   │                              #   patient_management/
│   │   │   │   ├── formulary/
│   │   │   │   ├── organization/
│   │   │   │   ├── patients/
│   │   │   │   ├── terminology/
│   │   │   │   └── users/
│   │   │   └── utils/
│   │   │       ├── PropertiesReader.java     # Reads src/main/resources/data.properties
│   │   │       └── WaitHelpers.java          # Explicit / fluent / implicit waits
│   │   └── resources/
│   │       ├── data.properties               # Test data & configuration (never commit real creds)
│   │       └── log4j2.xml                    # Logging config → logs/test.log
│   └── test/java/com/thetestingacademy/
│       ├── base/
│       │   └── CommonToAllTest.java          # @BeforeMethod/@AfterMethod driver setup/teardown
│       ├── listeners/
│       │   └── AllureScreenshotListener.java # Attaches a screenshot to every test result
│       └── tests/                            # One Test class per page, mirrors pages/ layout
```

> The `imporved_POM` folder name is a pre-existing typo baked into the
> package path across ~60 files. It's intentionally left as-is rather than
> mass-renamed — see `FRAMEWORK_RULES.md`.

## Separation of Concerns

1. **Page Objects (`src/main/.../pages/`) — The "How"**
   - **Responsibility:** Define *how* to interact with the application UI —
     locators and wrapper methods (clicks, typing, visibility checks).
   - **Rule:** NEVER place TestNG assertions (`Assert.assertEquals`) inside
     Page Objects. They should only return state (Strings, booleans) via
     methods like `isPageLoaded()`.

2. **Test Classes (`src/test/.../tests/`) — The "What"**
   - **Responsibility:** Define *what* we are testing — business logic,
     test data injection, and the actual **assertions**.
   - **Rule:** NEVER place `WebElement`s or `driver.findElement` calls here.

3. **Base Classes**
   - **`CommonToAllPage`** — inherited by every Page Object. Centralizes
     reusable UI actions (`clickElement`, `enterInput`, `clearAndEnterInput`,
     `getText`, `isElementDisplayed`, `openCareConnectUrl`) so no page ever
     calls `driver.findElement` directly. All click/input actions go through
     `WaitHelpers` first.
   - **`CommonToAllTest`** — inherited by every Test class. Handles
     `@BeforeMethod` (launch driver via `DriverManager.init()`) and
     `@AfterMethod` (`DriverManager.down()`), and carries the
     `@Listeners({AllureScreenshotListener.class})` annotation so every test
     automatically gets a screenshot attached to its Allure result.

4. **`DriverManager` (`src/main/.../driver/`)**
   - Holds the `WebDriver` in a `ThreadLocal<WebDriver>` — not a plain
     static field. This is the prerequisite for safe parallel execution
     (Stage 5 of the roadmap) and must not be changed to a static field.
   - Launches Chrome headless by default, with a mocked geolocation
     (Amman, Jordan) set via Chrome DevTools Protocol so location-dependent
     UI behaves consistently in CI-like conditions. Firefox/Edge are
     supported as fallbacks via the `browser` key in `data.properties`.

5. **`AllureScreenshotListener` (`src/test/.../listeners/`)**
   - A TestNG `IInvokedMethodListener`. After every test method, it grabs a
     screenshot from the current `WebDriver` and attaches it to the Allure
     report, labeled by outcome (Success / Failure / Skipped). This is how
     Stage 4 of the roadmap (visual evidence capture) is implemented — it's
     a permanent framework feature, not something enabled per-test.

See `ROADMAP.md` for how this architecture supports the project's 5-stage
plan, and `FRAMEWORK_RULES.md` for the rules that keep it from regressing.
