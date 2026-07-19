# CareConnect Automation Framework

Welcome to the CareConnect Automation Framework! This repository contains the automated End-to-End (E2E) UI testing suite for the CareConnect application, built with a robust, scalable, and team-ready architecture.

## 🚀 Tech Stack
- **Language:** Java 11+
- **Build Tool:** Maven
- **Core Engine:** Selenium WebDriver (v4.31.0)
- **Test Runner:** TestNG
- **Reporting:** Allure Reports

## 📚 Documentation & Source of Truth

To ensure consistency and prevent code conflicts across the QA team, please read the following standard operating procedures (SOPs) before writing any code:

1. **[Architecture Overview](ARCHITECTURE.md)**: Understand the Advanced Page Object Model (POM) pattern, the role of Base classes, and the strict Separation of Concerns.
2. **[Workflow & SOP](WORKFLOW.md)**: Learn the exact steps to add a new Page Object and a new Test Class without breaking existing code.
3. **[Locators Strategy](LOCATORS_STRATEGY.md)**: Learn the naming conventions and hierarchy for selecting elements (prioritizing Angular bindings over generic CSS).
4. **[Environment & Execution](ENVIRONMENT.md)**: Learn how to configure test data, run tests via Maven, and serve the interactive Allure dashboard.

## ⚙️ Quick Start

**1. Clone the repository:**
```bash
git clone <repository_url>
cd framwork
```

**2. Configure your test data:**
Ensure the credentials and URLs are set correctly in `src/main/resources/data.properties`.

**3. Run the test suite:**
```bash
mvn clean test -f pom.xml
```

**4. View the Allure Report:**
```bash
mvn allure:serve
```

---
*Built for scale, stability, and maintainability.*
