# Framework Rules — CareConnect Automation

This document defines the non-negotiable rules for this framework. It
describes the framework **as it actually exists in this repository** — it
is not generic boilerplate, and every example below is real code from this
project. If you're an AI agent (Claude, Antigravity, Cursor, etc.) working
on this repo, follow these rules exactly; don't reintroduce a pattern this
document says to avoid.

> Previous versions of this file contained a generic third-party Selenium
> framework template (retry analyzers, Excel-driven data, multiple
> environment suite files, non-headless Chrome, a static `WebDriver` field)
> that did **not** match this project's real code. It has been replaced
> with this document, which is verified against the actual source.

---

## 1. Core Architecture (as it actually is)

- **Language / build:** Java 11, Maven (`pom.xml`, artifact
  `careconnect-automation`).
- **Test runner:** TestNG 7.11.0, single suite file `testng.xml`
  (no per-environment suite files — there is only one).
- **Reporting:** Allure Report 3.4.1 via `allure-maven` 3.0.2, using `allure-testng` 2.35.3 with AspectJ weaving enabled to support `@Step` nesting. Screenshots captured via `AllureScreenshotListener`.
- **One POM pattern, consistently:** Improved POM. Every page object lives
  under
  `src/main/java/com/thetestingacademy/pages/pageObjectModel/normal_POM/imporved_POM/...`
  and extends `CommonToAllPage`. There is no Page Factory pattern and no
  Basic POM pattern in this project — don't introduce one.
  (Note: `imporved_POM` is a pre-existing typo in the package path. It's
  part of the established structure — don't silently "fix" it, since that
  would require updating every import across ~60 files. Flag it to Ahmad
  if you think it's worth a deliberate rename.)
- **Driver lifecycle:** `DriverManager` — `ThreadLocal<WebDriver>`, not a
  static field. This is deliberate: it's what makes future parallel
  execution (Stage 5) safe. Never replace it with a plain static field.
- **Browser:** headless Chrome by default, with a mocked geolocation
  override (Amman, Jordan coordinates) set via CDP in `DriverManager.init()`.
  Firefox/Edge are supported as fallbacks based on the `browser` key in
  `data.properties`.
- **No retry mechanism exists.** There is no `RetryAnalyzer` or
  `IAnnotationTransformer` in this codebase. Don't assume one exists, and
  don't add one without discussing it first — flaky tests should be fixed
  at the root cause (usually a missing/wrong wait), not silently retried.
- **No Excel-driven data.** All config/test data comes from
  `src/main/resources/data.properties` via `PropertiesReader`. There is no
  Apache POI data-driven layer in use.

## 2. Absolute Rules

### Rule 1 — Never guess a locator
Always live-inspect the actual rendered DOM before writing a locator into a
Page Object. Pattern-matching a locator from a similar-looking page has
caused real bugs in this project (e.g. assuming a header would be
`h1.header` when the real markup used `div.panel-header`). If you need to
inspect the DOM, it's fine to write a temporary throwaway script — but see
Rule 5.

### Rule 2 — No `Thread.sleep()`, ever
Use `WaitHelpers` (backed by `WebDriverWait` / `FluentWait`) exclusively.
`WaitHelpers.waitJVM()` exists as a documented last resort but should
essentially never be used — if you reach for it, there's almost always a
missing explicit-wait condition instead. This codebase must never contain
`Thread.sleep` anywhere in `src/test`; ensure this standard is strictly upheld.

### Rule 3 — Never swallow the final visibility check in `isPageLoaded()`
The last assertion-relevant wait inside a Page Object's `isPageLoaded()`
must be allowed to throw if the content doesn't appear. Wrapping it in a
silent try/catch causes false positives — this exact bug was already found
and fixed in `ClaimsPage` and `PaperPrescriptionPage`. Don't reintroduce it
anywhere else.

### Rule 4 — Content locators must not collide with sidebar navigation text
Never assert on text (or a generic locator) that also appears in the
sidebar nav — e.g. "Settings" or "Audit Log" show up in both the nav and
the content header, so a locator like `//*[text()='Settings']` will always
pass, navigation success or not. Always scope to a specific content-area
element: a unique Angular component tag (e.g. `app-user-settings`) or a
data element that only exists in the loaded content, not the shell.

### Rule 5 — Delete scratch/inspection files once the real work is done
Temporary DOM-inspection scripts (anything like `InspectX.java`,
`DOMInspector.java`, `FindY.java`, `InvestigateZ.java`) are fine to write
while discovering real locators, but must be deleted once the real Page
Object + test are built and passing. Don't let them accumulate — a July
2026 cleanup removed 7 such files
(`InspectPageDOM`, `InspectFormulary`, `DOMInspector`,
`InspectTerminologies`, `FindNavigation`, `FindError`,
`InvestigateCountries`) that had been left in `src/test`.

### Rule 6 — Unique, descriptive class names across the whole project
Avoid name collisions across packages. This project currently has two
different `PrescriptionsPage` classes in different packages (`homeDelivery`
vs `prescribing`) — this works because the packages differ, but it's
confusing. Be more specific for new pages (e.g.
`HomeDeliveryPrescriptionsPage` instead of a bare `PrescriptionsPage`).

### Rule 7 — Always report real, actual command output
After any claimed fix or new test, actually run it and paste the real
surefire output line (`Tests run: X, Failures: Y, Errors: Z`) — never a
summary or an assumption that it worked. If it fails, report the failure
honestly along with exactly what you changed.

### Rule 8 — Keep `testng.xml` in sync, for real
Every new test class must be added to `testng.xml`, and that must be
confirmed by actually running the suite — not just by editing the XML.

### Rule 9 — Strict five-tag `groups` structure for all `@Test` methods
Every future `@Test` method must carry a `groups` attribute with exactly five tags in this specific order:
`{"<module>", "<page>", "<scenarioType>", "<page>.<scenarioType>", "regression"}`
Missing any of these tags will break selective suite execution silently. Do not omit them.

## 3. Known Environment Note

The team has previously hit a local environment issue where an AI agent's
own terminal execution tool fails with `opening NUL for ACL write: Access
is denied` on Windows, even for simple commands. This is not related to
project location (confirmed outside OneDrive) or admin privileges
(confirmed both ways). When this happens: the agent edits/writes code, and
Ahmad runs the actual `mvn`/`git` commands manually in his own terminal and
reports back the real output, per Rule 7.

## 4. Where Things Live (Required Standard)

```
src/main/java/com/thetestingacademy/
  base/CommonToAllPage.java        # shared page actions (click, type, getText, waits)
  driver/DriverManager.java        # ThreadLocal WebDriver lifecycle, headless Chrome + geolocation mock
  pages/pageObjectModel/normal_POM/imporved_POM/
    careconnect/                   # login, cdss, dispensing, prescribing, ruleEngine, system, useraccount
    formulary/, organization/, patients/, terminology/, users/
  utils/
    PropertiesReader.java          # reads src/main/resources/data.properties
    WaitHelpers.java                # explicit / fluent wait strategies

src/test/java/com/thetestingacademy/
  base/CommonToAllTest.java        # @BeforeMethod/@AfterMethod driver setup/teardown, logger
  listeners/AllureScreenshotListener.java   # attaches a screenshot to every test result
  tests/                            # one Test class per page, mirrors the pages/ package layout

testng.xml                          # the one and only suite file — 60 registered classes
pom.xml
```
