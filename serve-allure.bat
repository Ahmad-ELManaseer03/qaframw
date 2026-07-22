@echo off
call mvn allure:report
call .\.allure\allure-3.4.1\node_modules\.bin\allure.cmd open target\site\allure-maven-plugin
