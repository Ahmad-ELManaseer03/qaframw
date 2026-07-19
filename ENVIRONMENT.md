# Environment Setup and Execution

This document explains how to configure, run, and view reports for the framework.

## 1. Test Data Configuration (`data.properties`)
All environment-specific variables, URLs, and credentials live in `src/main/resources/data.properties`.

**Example:**
```properties
url=https://qc.care-connect.health/login
username=careconnect@neorx.co
password=Admin@1234
browser=Chrome
```
*Never hardcode passwords or URLs directly inside Java classes. Always fetch them using `PropertiesReader.readKey("username")`.*

## 2. Running the Tests
To execute the automation suite, use Maven from the root directory (`framwork/`):

**Run all tests (via testng.xml):**
```bash
mvn clean test -f pom.xml
```

**Run a specific test class:**
```bash
mvn clean test -Dtest=TestLogin
```

## 3. Viewing the Allure Report
The framework generates rich, interactive reports.

1. **Execute Tests:** Run `mvn clean test` as shown above. This generates raw data in the `allure-results/` directory.
2. **Serve the Report:** Run the following command:
   ```bash
   mvn allure:serve
   ```
3. **View:** A lightweight web server will start, and the Allure dashboard will automatically open in your default browser. It includes:
   - Pass/Fail charts
   - Step-by-step execution logs (from `@Step` annotations)
   - Automatic screenshots on failure
