# CareConnect Automation — Project Roadmap

This document is the single source of truth for **what this project is, why it
exists, and where it's going.** Any contributor (human or AI agent) should
read this before touching the codebase.

---

## 1. What This Project Is

**qaframw** (Maven artifact: `careconnect-automation`) is a Selenium +
TestNG + Java UI test automation framework for **CareConnect**, a healthcare
platform (Angular / PrimeNG single-page application) used in the Saudi
market, built by NeoRx/Neomatix. The target environment under test is:

```
https://qc.care-connect.health
```

The framework covers CareConnect's core clinical/pharmacy workflow areas:
Organization management, Users & Roles, Patients & Visits, Terminology,
Formulary (Generics/Synonyms/Trades/Care Plans/Releases), Prescribing,
Dispensing, Home Delivery, Rule Engine (business rules, overrides,
simulator), CDSS (Clinical Decision Support — drug interaction checks), and
System administration (audit log, lookups, settings, tutorials).

**Owner / primary contributor:** Ahmad (Senior QA Specialist, NeoRx).

## 2. Why It Exists

Manual regression testing of a ~60-page enterprise healthcare SPA before
every release is slow and error-prone. This framework's job is to give the
QA team fast, reliable, repeatable confidence that:
1. Every page in the system is reachable and renders correctly (Stage 1).
2. Every module's core data operations (Add/Edit/Delete/Search) work
   correctly (Stage 2).
3. Real multi-step clinical workflows that span several pages work
   end-to-end (Stage 3).
4. Every test run leaves behind visual proof of what happened, for fast
   triage (Stage 4).
5. All of the above runs automatically and in parallel as part of the team's
   normal delivery pipeline (Stage 5).

## 3. Architecture At a Glance

Improved Page Object Model (POM) — page objects extend a shared
`CommonToAllPage` base class, test classes extend `CommonToAllTest`. See
`ARCHITECTURE.md` for the full breakdown, `LOCATORS_STRATEGY.md` for how
elements are located, and `FRAMEWORK_RULES.md` for the hard rules that must
never be violated (no `Thread.sleep`, no guessed locators, no swallowed
exceptions, etc.).

---

## 4. The Five Stages

### Stage 1 — Full Page Coverage ✅ COMPLETE

**Goal:** Prove that every page in CareConnect is reachable and loads
correctly, with zero broken navigation anywhere in the system.

**What each test does:** login → navigate to the page → assert on a unique
content-area locator (never sidebar/header text that also appears in
navigation) → assert the URL is correct.

**Scope delivered — 60 pages across 14 modules**, all registered in
`testng.xml` and passing on real, DOM-verified locators (no guessed
selectors):

| Module | Pages | Example classes |
|---|---|---|
| CDSS | 15 | `TestDrugAllergyInteractions`, `TestDrugDoseRange`, `TestSimulator` |
| Organization | 9 | `TestCountries`, `TestCities`, `TestFacilities`, `TestSuppliers` |
| Rule Engine | 7 | `TestBusinessRulesList`, `TestOverrideRulesList`, `TestSimulator` |
| Formulary | 5 | `TestGenerics`, `TestTrades`, `TestMedicationSynonyms`, `TestCarePlans`, `TestReleases` |
| Dispensing | 5 | `TestClaims`, `TestDispensingAuth`, `TestPaperPrescription` |
| System | 4 | `TestAuditLog`, `TestSettings`, `TestLookupsManagement` |
| Users | 3 | `TestUsers`, `TestRoles`, `TestInvitations` |
| Prescribing | 3 | `TestPrescriptions`, `TestPrescribeAuth`, `TestExpiredPrescriptions` |
| User Account | 3 | `TestUserAccountSettings`, `TestUserAccountProfile`, `TestUserAccountChangePassword` |
| Patient Management | 2 | `TestVisits`, `TestMyAppointments` |
| Home Delivery | 1 | `TestPrescriptions` (homeDelivery package) |
| Patients | 1 | `TestPatients` |
| Terminology | 1 | `TestTerminologies` |
| Login | 1 | `TestLogin` |

**Hard-won lessons from this stage (now codified in `FRAMEWORK_RULES.md`):**
- Never guess a locator by pattern-matching another page — always
  live-inspect the real DOM first.
- Never let the final content-visibility check in `isPageLoaded()` be
  swallowed by a try/catch — it caused false positives on `ClaimsPage` and
  `PaperPrescriptionPage`.
- Never key a content check on text that also appears in the sidebar
  (e.g. "Settings", "Audit Log") — it always passes regardless of whether
  navigation actually worked.
- Scope every content assertion to a specific Angular component tag (e.g.
  `app-user-settings`) or unique data element.
- Scratch/inspection scripts used to discover the real DOM must be deleted
  once the real Page Object is built — see the July 2026 cleanup that
  removed 7 leftover investigation files and a stray `Thread.sleep`.

**Target State:** `main` branch is green and all broken tests are cleaned up.
**Current Progress:** Cleanup is complete as of July 21, 2026.
(`Tests run: 1, Failures: 0` on last verification), no `Thread.sleep` or
scratch files remain in `src/test`.

---

### Stage 2 — Depth: Full CRUD Coverage 🔜 IN PROGRESS (next up)

**Goal:** For every module, go beyond "the page loads" into real functional
coverage of the operations a user actually performs.

**Per-module scope, for each screen that supports it:**
- **Add** a new record, including required-field validation (what happens
  if you submit with a required field empty, or with invalid input).
- **Edit** an existing record and confirm the change is saved and reflected.
- **Delete** a record — safely, without touching real production-like data
  (use dedicated/disposable test data only).
- **Search / Filter** — confirm results match the query, and confirm the
  empty-results state renders correctly.
- **Edge cases** — empty states, validation error messages, duplicate-entry
  handling, and any other non-happy-path behavior specific to that screen.

**Starting point:** work began partially on the **Countries** page
(Organization module) before the team paused to finish Stage 1 completely
first. This is the natural first module to resume with, but the next
target module is decided by Ahmad before each new batch of work starts —
Stage 2 must never be started on a module without his explicit
direction (per established working rules).

**Definition of done for a module:** Each CRUD test class must map to a specific Organization entity, be fully
implemented, use only real DOM-verified locators, strictly contain zero `Thread.sleep`, be registered in `testng.xml`, and has been run with real (not summarized)
surefire output confirming pass/fail.

---

### Stage 3 — Multi-Page Flows (Cross-Module Scenarios) 🔮 PLANNED

**Goal:** Automate realistic end-to-end clinical/pharmacy workflows that
chain several modules together in one scenario, e.g.:

> Login → search for a patient → open their file → add a visit →
> prescribe a medication → verify it appears in Dispensing.

**Why it comes after Stage 2:** these scenarios only work reliably once
every individual page/module they touch already has solid, independently
verified CRUD coverage. Building multi-page flows on top of untested
individual pages means failures are ambiguous — you can't tell whether the
flow broke or a single page did.

**Not yet started.** Concrete scenarios to automate will be defined once
Stage 2 covers enough modules to compose real workflows from.

---

### Stage 4 — Visual Evidence Capture ✅ IMPLEMENTED (ongoing, permanent feature)

**Goal:** Every test result — pass, fail, or skip — should carry a
screenshot attached to the Allure report, so triage never requires
re-running a test just to see what the screen looked like.

**Delivered:** `AllureScreenshotListener` (a TestNG `IInvokedMethodListener`)
attaches a screenshot to the Allure report after every test method,
labeled by outcome ("Screenshot on Success" / "Screenshot on Failure" /
"Screenshot on Skipped"). This is wired in permanently via
`CommonToAllTest`'s `@Listeners` annotation — every test class inherits it
automatically, no per-test setup needed. Reports are generated with
`mvn allure:report` / viewed live with `mvn allure:serve`.

This stage isn't "done once" — it's a standing framework feature that
Stage 2 and Stage 3 tests get for free.

---

### Stage 5 — CI/CD & Parallel Execution 🔮 FUTURE

**Goal:** Move from "someone runs `mvn test` manually" to "tests run
automatically on every push, in parallel, as part of the team's actual
delivery pipeline."

**Two parts:**
1. **Parallel execution** — `testng.xml` currently runs as a single
   sequential `<test>` block (`preserve-order="true"`, no `parallel`
   attribute). Turning on `parallel="methods"` (or `"classes"`) with a real
   thread-count needs to be validated deliberately: `DriverManager` already
   uses `ThreadLocal<WebDriver>`, which is the prerequisite for safe
   parallel runs, but this hasn't been exercised under real parallel load
   yet.
2. **CI integration** — wire the suite into Azure DevOps (or whatever CI
   the team standardizes on) so it runs on a schedule and/or on every push,
   with pass/fail visible to the team without anyone running it by hand.

**Not yet started.** This is explicitly the last stage — it only makes
sense once there's enough real test coverage (Stage 2 + Stage 3) that
running it automatically is actually valuable.

---

## 5. Project Tracking & Progress

| Stage | Status | Notes |
|---|---|---|
| 1 — Full page coverage | ✅ Complete | 60 pages, cleanup verified July 21, 2026 |
| 2 — CRUD depth | 🔜 Next | Countries (Organization) partially started; module TBD by Ahmad |
| 3 — Multi-page flows | 🔮 Planned | Blocked on enough Stage 2 coverage |
| 4 — Visual evidence | ✅ Live | `AllureScreenshotListener`, permanent framework feature |
| 5 — CI/CD & parallel | 🔮 Future | `ThreadLocal` driver already in place; nothing else started |

---

## 6. Established Working Rules (summary — full detail in `FRAMEWORK_RULES.md`)

1. Never guess a locator — always live-inspect the real DOM first.
2. Never use `Thread.sleep()` — explicit waits (`WaitHelpers`) only.
3. Never swallow the final content-visibility check inside `isPageLoaded()`.
4. Never key content assertions on text duplicated in sidebar navigation.
5. Delete scratch/inspection files once the real Page Object is built.
6. Use unique, descriptive class names across the whole project.
7. Always run the actual test and report the real surefire output — never
   summarize or assume a fix worked.
8. Keep `testng.xml` in sync with every new test class, verified by
   actually running the suite.
