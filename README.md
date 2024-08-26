# Veeva
This is a TDD with PageObject framework

# Setup:
- pom.xml contains all dependencies and it used latest version of selenium (4.22), Tdd (java,Selenium, TestNG) and customized extent report.

# Architecture
- src/main/java contains all the core component classes/methods for the project within page object model for the reusablity. 
- src/main/resources contains Global configuration and editable test data in JSON file.
- src/test/java contains testcases in tdd architecture.
- src/test/resources contains test configuration and editable test data in JSON file.

# Run
-	Test cases are parametrized to support multiple browser (Chrome, Firefox etc..) and run parallely from RunnerFile.xml file
# Report
- After execution customized extent report should be generated. 
