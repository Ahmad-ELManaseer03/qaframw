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

## Stage 2 — Preliminary Investigation Already Done (Countries)

Before Stage 1 was finished, some real DOM investigation was already run
against the Countries **Add** flow — raw findings are preserved in
`investigation/countries-add/report.txt`. Confirmed so far:

- **Add form fields:** `Country Arabic Name *` and `Country English Name *`
  — both required, field type not yet confirmed from the raw scan.
- **Edit form:** opens correctly; the two visible inputs are neither
  `readonly` nor `disabled`.
- **Not yet confirmed:** validation error text, the success message shown
  after a save, the search/empty-state behavior, and whether an Export
  button exists (none was found in this scan — an Audit Log button was
  found and worked).
- **Cleanup gap found:** the investigation script could **not** find a
  Delete button for its own test record (`ZZTEST_DoNotUse`), so that
  record may still be sitting in the QC environment. This should be
  checked and cleaned up manually before Stage 2 work resumes on
  Countries, and the real Delete locator needs to be found properly (per
  `FRAMEWORK_RULES.md`, Rule 1 — no guessing).

This is a head start, not finished coverage — Stage 2 on Countries still
needs proper Page Object methods and real `TestCountries` CRUD test cases
built from this, following the normal workflow in `WORKFLOW.md`.
