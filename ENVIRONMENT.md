# Environment Setup and Execution

This document explains how to configure, run, and view reports for the
framework.

## 1. Test Data Configuration (`data.properties`)

All environment-specific variables, URLs, and credentials live in
`src/main/resources/data.properties`. The actual keys used across the
project are:

```properties
url=<CareConnect login URL>
username=<test account username>
password=<test account password>
invalid_username=<used for negative login tests>
invalid_password=<used for negative login tests>
browser=chrome
expected_page_title=<used by title-assertion tests>
expected_error_message=<used by negative login tests>
search_patient_name=<used by patient search tests>
```

*Never hardcode any of these directly inside Java classes or commit real
credentials. Always fetch via `PropertiesReader.readKey("username")`.*

## 2. Browser Behavior

By default, `DriverManager` launches **headless Chrome** (`1920x1080`) with
a mocked geolocation override (Amman, Jordan coordinates) applied via
Chrome DevTools Protocol — this keeps location-dependent parts of the UI
consistent regardless of where the suite actually runs. Set `browser` to
`firefox` or `edge` in `data.properties` to run against those instead (both
launch normally, not headless).

## 3. Running the Tests

Run from the project root (`qaframw/`):

**Run the full suite (via `testng.xml`):**
```bash
mvn clean test
```

**Run a specific test class:**
```bash
mvn test "-Dtest=TestLogin"
```
> On Windows PowerShell, wrap the `-Dtest=...` flag in quotes as shown
> above — PowerShell can otherwise mis-parse the `=` argument.

## 4. Logs

Log4j2 (`src/main/resources/log4j2.xml`) writes to both the console and
`logs/test.log`. Every `CommonToAllTest` subclass logs driver setup/teardown
automatically via the shared `logger` field.

## 5. Viewing the Allure Report

1. **Execute tests:** `mvn clean test` (as above). This writes raw result
   data into `allure-results/`.
2. **Serve the report:**
   ```bash
   mvn allure:serve
   ```
3. **View:** A local web server starts and opens the Allure dashboard in
   your default browser, including:
   - Pass/Fail charts
   - Step-by-step execution logs (from `@Step` annotations, e.g. in
     `LoginPage`)
   - A screenshot attached to **every** test result — pass, fail, or
     skipped — via `AllureScreenshotListener` (see `ARCHITECTURE.md`)

## 6. Known Environment Issue (Windows / Antigravity terminal)

If an AI coding agent's own terminal tool fails with
`opening NUL for ACL write: Access is denied` when trying to run `mvn` or
`git` commands, this is a known local environment issue — not related to
project location or admin privileges. Workaround: the agent edits/writes
code, and you run the actual `mvn`/`git` commands yourself in your own
terminal and report back the real output.
