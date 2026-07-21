# CareConnect Automation Framework

Automated End-to-End (E2E) UI testing suite for **CareConnect**
(`https://qc.care-connect.health`), a healthcare/pharmacy SPA built with
Angular + PrimeNG. Built with Selenium WebDriver, TestNG, and Java, using
an Improved Page Object Model.

## 🚀 Tech Stack
- **Language:** Java 11
- **Build Tool:** Maven
- **Core Engine:** Selenium WebDriver 4.31.0
- **Test Runner:** TestNG 7.11.0
- **Reporting:** Allure 2.26.0 (screenshot on every test result — pass, fail, or skip)

## 📍 Project Status

**Stage 1 (full page coverage) is complete: 60 pages across 14 modules,**
all passing on real, DOM-verified locators. Stage 2 (CRUD depth per module)
is next. See **[ROADMAP.md](ROADMAP.md)** for the complete 5-stage plan and
current status — read this first to understand where the project stands.

## 📚 Documentation & Source of Truth

Read these before writing any code:

1. **[ROADMAP.md](ROADMAP.md)**: What this project is, the 5-stage plan,
   and current status. Start here.
2. **[Framework Rules](FRAMEWORK_RULES.md)**: The non-negotiable rules for
   this specific codebase — locator strategy, wait strategy, and the bugs
   these rules exist to prevent.
3. **[Architecture Overview](ARCHITECTURE.md)**: The Improved POM pattern,
   base classes, and separation of concerns.
4. **[Workflow & SOP](WORKFLOW.md)**: The exact steps to add a new Page
   Object and a new Test Class without breaking existing code.
5. **[Locators Strategy](LOCATORS_STRATEGY.md)**: Naming conventions and
   selector priority (Angular bindings over generic CSS).
6. **[Environment & Execution](ENVIRONMENT.md)**: How to configure test
   data, run tests via Maven, and view the Allure dashboard.

## ⚙️ Quick Start

**1. Clone the repository:**
```bash
git clone https://github.com/Ahmad-ELManaseer03/qaframw.git
cd qaframw
```

**2. Configure your test data:**
Set credentials and URLs in `src/main/resources/data.properties`. Never
commit real credentials — this file should hold test/QA environment values
only.

**3. Run the full suite:**
```bash
mvn clean test
```

**4. Run a single test class:**
```bash
mvn test "-Dtest=TestUserAccountSettings"
```

**5. View the Allure Report:**
```bash
mvn allure:serve
```

---
*Built for scale, stability, and maintainability.*
