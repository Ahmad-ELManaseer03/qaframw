# CareConnect Automation - Usage Catalog / دليل الاستخدام

This document provides quick reference commands and guidelines for running tests and generating reports in the CareConnect Automation Framework.
يوفر هذا المستند مرجعاً سريعاً للأوامر والقواعد الخاصة بتشغيل الاختبارات وتوليد التقارير في إطار عمل CareConnect.

---

## 🇬🇧 English Catalog

### 1. Running Tests (Execution)

**Run the full suite (All Tests):**
```bash
mvn clean test
```

**Run specific tests using Groups (Selective Execution):**
Due to a Maven Surefire behavior with `testng.xml`, you **must** use the `-Dsurefire.suiteXmlFiles=` flag to bypass the static suite file when filtering by groups.
```bash
# Run all tests in the "organization" module
mvn clean test "-Dsurefire.suiteXmlFiles=" "-Dgroups=organization"

# Run only the "create" scenarios for "countries"
mvn clean test "-Dsurefire.suiteXmlFiles=" "-Dgroups=countries.create"

# Run all regression tests
mvn clean test "-Dsurefire.suiteXmlFiles=" "-Dgroups=regression"

# Exclude a specific group (e.g., skip delete tests)
mvn clean test "-Dsurefire.suiteXmlFiles=" "-Dgroups=organization" "-DexcludedGroups=delete"
```

### 2. Viewing Allure Reports
After running the tests, generate and open the interactive Allure report using:
```bash
mvn allure:serve
```
*(This command will automatically render the generated JSON results into a beautiful HTML UI and open it in your browser).*

### 3. Adding a New Test (Group Rules)
Every `@Test` method **must** include exactly 5 groups in this specific order:
`{"<module>", "<page>", "<scenarioType>", "<page>.<scenarioType>", "regression"}`

**Example:**
```java
@Test(priority = 1, groups = {"organization", "countries", "create", "countries.create", "regression"})
public void testCountriesCreate_ValidDataSucceeds() { ... }
```

---

## 🇸🇦 الدليل باللغة العربية

### 1. تشغيل الاختبارات (Execution)

**تشغيل جميع الاختبارات (الـ Suite كاملة):**
```bash
mvn clean test
```

**تشغيل اختبارات محددة (باستخدام ميزة الـ Groups):**
بسبب سلوك إضافة Maven Surefire عند الارتباط بملف `testng.xml`، **يجب** استخدام الباراميتر `-Dsurefire.suiteXmlFiles=` لتخطي الملف الثابت وإجبار النظام على فلترة الاختبارات المطلوبة فقط.
```bash
# تشغيل كل اختبارات موديول المنظمة (organization)
mvn clean test "-Dsurefire.suiteXmlFiles=" "-Dgroups=organization"

# تشغيل سيناريوهات الإنشاء (create) الخاصة بالدول فقط
mvn clean test "-Dsurefire.suiteXmlFiles=" "-Dgroups=countries.create"

# تشغيل جميع اختبارات الـ Regression
mvn clean test "-Dsurefire.suiteXmlFiles=" "-Dgroups=regression"

# استثناء مجموعة معينة (مثلاً: تشغيل الكل ما عدا اختبارات الحذف)
mvn clean test "-Dsurefire.suiteXmlFiles=" "-Dgroups=organization" "-DexcludedGroups=delete"
```

### 2. عرض تقارير Allure
بعد الانتهاء من تشغيل الاختبارات، يمكنك توليد وفتح التقرير التفاعلي الخاص بـ Allure مباشرة عبر الأمر التالي:
```bash
mvn allure:serve
```
*(هذا الأمر سيقوم بتحويل النتائج الخام إلى واجهة HTML رائعة ويفتحها تلقائياً في متصفحك).*

### 3. إضافة اختبار جديد (قواعد التكويد)
كل دالة اختبار `@Test` تقوم بكتابتها مستقبلاً **يجب** أن تحتوي على 5 مجموعات (tags) بالضبط، وبهذا الترتيب الموحد:
`{"<الموديول>", "<الصفحة>", "<نوع_السيناريو>", "<الصفحة>.<نوع_السيناريو>", "regression"}`

**مثال تطبيقي:**
```java
@Test(priority = 1, groups = {"organization", "countries", "create", "countries.create", "regression"})
public void testCountriesCreate_ValidDataSucceeds() { ... }
```
