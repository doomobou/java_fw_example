# Documents
- `README.md` - current document
- `Test Cases.xlsx` - list of UI and API test cases
- `evaluation-report.md` - Overall Evaluation Report for the application

## Requirements
- Java 21
- Maven
- Chrome (with chromedriver of matching version)
- Docker (to actually run the app under test)

### Additional requirements to generate and view test result report:
- npm
- Allure Commandline npm package (https://www.npmjs.com/package/allure-commandline)

## Run the tests
With app already running (see original `instructions/general-instructions.md`):

`mvn clean verify -D"webdriver.chrome.driver=%path_to_chromedriver%", -Dgroups="tags_to_run" -D"selenide.headless=true"`, where:  
`path_to_chromedriver` - full filepath to chromedriver of corresponding version to Chrome.  
`tags_to_run`: possible options - `API`, `UI`. Remove `-Dgroups` option from command to run everything.  
`selenide.headless` - `true` for headless, anything else or remove option for non-headless.

## Generate and view result report

In project `target` folder, after a finished test run:

`allure generate`
->
`allure open`

## Expected results

4 tests should fail (counting instances of parameterized tests as separate tests).  
More details and reasoning in code comments and in the evaluation report.
