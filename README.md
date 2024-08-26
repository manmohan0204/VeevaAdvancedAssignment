# Veeva Advance Assignment
This framework is a Multi Module Maven project with 4 modules.
■	Module 1: automation-framework
■	Module 2: core-product-tests
■	Module 3: derived-product1-tests
■	Module 4: derived-product2-tests


# Setup:
- Parent pom.xml contains all dependencies and pacling should be pom
- Module pom should inherit all dependency from partent pom and packing should be jar
- Framework used latest version of selenium (4.22), Tdd (java,Selenium, TestNG) and customized extent report.

# Architecture
- automation-framework module contains all the core component classes/methods for the project within page object model for the reusablity and this should be added to the other module as a dependency. 
- Global configuration JSON files are stored in automation-framework module .
- core-product-tests module contains testcases and Page classed related to Core Product features.
- derived-product2-tests module contains testcases and Page classes related to Core Product features.
- Test specific config JSON file are stored based on module wise.
- Specific TestNG.xml files are created for each test module.

# Run
-	Test cases are parametrized to support multiple browser (Chrome, Firefox etc..) and run in multiple threads using maven commands
# Report
- Once execution id completed customized extent report should be generated.
