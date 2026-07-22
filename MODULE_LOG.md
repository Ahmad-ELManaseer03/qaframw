# Stage 1 Module Completion Log

This replaces the old `walkthrough.md`, which only documented the
Organization module and gave the false impression that Stage 1 covered
just that one module. Stage 1 actually delivered all 60 pages across 14
modules (see `ROADMAP.md`, Stage 1, for the full table) — this log records
what's specifically known about *how* each part was built, and is honest
about what detail wasn't preserved.

## Organization Module — Fully Documented

Countries, Areas, Cities, Districts, Streets (plus Facilities, Suppliers,
Groups, Divisions):

- **Live DOM verification caught a wrong assumption:** `Countries` was
  initially expected to use an `h1.header` element; live DOM inspection
  showed the real markup uses `div.panel-header` with text like
  `"Countries List"`, `"Area List"`, etc.
- **Robust post-login navigation check:** every Page Object explicitly
  waits for a known sidebar element (`WaitHelpers.checkVisibility(...,
  "Patients", 15)`) to confirm the SPA dashboard and session have fully
  initialized, before navigating to the specific route — instead of a
  brittle `Thread.sleep()` or a bare URL assertion.
- **Naming/grammar edge cases:** assertions were written as
  `.contains("Countries")`, `.contains("Area")`, `.contains("Cities")`,
  etc., to tolerate minor plural/singular differences in on-screen text
  (e.g. "Cities List" drops the "y" from "City").
- All 9 Organization tests, plus `TestLogin` and `TestPatients`, were
  confirmed passing together as part of this effort.

## User Account Module — Fully Documented (post-Stage-1 cleanup)

`TestUserAccountSettings` originally carried leftover debug code (a
`Thread.sleep(3000)` plus a raw DOM dump to `target/dom_dump.txt`) from
when the locator was first being worked out. This was removed on July 21,
2026 — the removal is safe because `UserAccountSettingsPage.isPageLoaded()`
already performs its own explicit wait
(`WaitHelpers.checkVisibility(..., headerLoc, 15)`) before checking the
URL, so the sleep was redundant. Verified passing after removal
(`Tests run: 1, Failures: 0`).

## CDSS, Dispensing, Prescribing, Rule Engine, Formulary, System, Users,
## Patients, Patient Management, Terminology, Home Delivery — Completed, Detail Not Preserved

These modules (35 pages total) were completed as part of Stage 1 and are
registered and passing in `testng.xml`, but the specific per-page
discoveries (locator surprises, edge cases hit, fixes applied) from when
they were originally built were not written down at the time. This log
intentionally doesn't invent that detail after the fact.

**Going forward:** when Stage 2 (CRUD depth) touches any of these modules,
record what's actually learned about each page here (or in a per-module
note) as it happens — the Organization and User Account entries above are
the pattern to follow: specific, verified facts only, written down close
to when the work happened.

## Stage 2 — Countries (Organization) — CRUD Complete ✅

Full CRUD lifecycle for Countries is built (`CountriesPage.java`), registered
in `testng.xml`, and verified passing on real surefire output:
`Tests run: 1, Failures: 0, Errors: 0, Skipped: 0` (July 22, 2026).
`TestCountries.testCountriesCRUD()` covers, in one Allure-stepped flow:
empty-form validation → create → search (found + not-found/empty-state) →
update → verify-update → self-cleaning delete. Zero `Thread.sleep` in either
file.

This supersedes the preliminary investigation below it replaced — corrected
findings:
- **No global search box exists.** The original "NO SEARCH BOX FOUND"
  result was correct as far as it went: Countries uses a **per-column
  filter** (PrimeNG filter-menu button → overlay input → Apply), not a
  standalone search input. `CountriesPage.searchCountry()` drives this.
- **Delete locator found and confirmed working** — scoped to the specific
  row by unique cell text, matching `.p-button-danger` / `.pi-trash`
  (`clickDeleteForCountry(uniqueName)`). This is what the original
  investigation's own cleanup step couldn't find.
- **Validation errors:** `.p-error, .text-danger, .invalid-feedback,
  mat-error`. **Success/update toast:** `p-toastitem .p-toast-detail,
  .p-toast-message-text`.
- A **Code** field exists on the Add/Edit form alongside Arabic/English
  name; it's optional (filled if present, skipped otherwise).

**Known open item — not yet resolved:** the original static
`ZZTEST_DoNotUse` record from the preliminary investigation was never
confirmed deleted (the investigation's own cleanup failed to find the
Delete button at the time). `TestCountries` only ever creates/cleans up its
own timestamp-suffixed records, so it will not touch this old row. Needs a
manual check in the QC environment, or a one-off cleanup run, now that the
real Delete locator works.

**Housekeeping done:** `InspectCountriesDelete.java` (the temporary DOM-dump
script that was hunting for this same Delete locator) is now dead code —
referenced nowhere else in the codebase — and should be deleted per
`FRAMEWORK_RULES.md` Rule 5, the same pattern as the July 21 Stage 1
cleanup.

## Infrastructure — Allure 3 Upgrade & Integration — Complete ✅

**Date:** July 22, 2026

The project's reporting infrastructure has been fully upgraded to resolve version mismatches and UI crash bugs.

- **Changes Applied:**
  - Upgraded `allure-testng` dependency from `2.26.0` to `2.35.3`.
  - Added `allure-bom` to `pom.xml` dependencyManagement for consistent versioning.
  - Upgraded `allure-maven` plugin to `3.0.2` and set `reportVersion` to `3.4.1` (Allure 3 frontend).
  - Cleaned up obsolete local installations of `.allure/` and manual `serve-allure.bat` scripts. The new `serve-allure.bat` now relies entirely on standard Maven commands (`mvn allure:report`, `mvn allure:serve`), preventing rogue manual CLI versions from overriding Maven.
- **AspectJ Weaving Added:** Previously, `@Step` annotations (e.g., in `LoginPage.java`) were appearing as flat text rather than nested steps because AspectJ Weaver was missing from the Surefire plugin configuration. AspectJ Weaver (`1.9.25`) was added to the `maven-surefire-plugin` `argLine`, resolving the nesting issue and linking `AllureEvidence` screenshots to the correct steps.
- **Verification:** Ran `mvn clean test -DsuiteXmlFile=testng.xml` to confirm the environment works without the old local binaries. Real surefire output:
  `Tests run: 5, Failures: 0, Errors: 0, Skipped: 0`
  The report generated flawlessly via `mvn allure:report` without crashing during Node.js/Allure 3.4.1 extraction.
