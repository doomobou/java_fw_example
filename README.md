# Documents
- `README.md` - current document
- `Test Cases.xlsx` - list of UI and API test cases
- `evaluation-report.md` - Overall Evaluation Report for the application

## Requirements
- Java 21
- Maven
- Chrome
- Docker (to actually run the app under test)

### Additional requirements to generate and view test result report:
- npm
- Allure Commandline npm package (https://www.npmjs.com/package/allure-commandline)

## Run the tests locally
With app already running (see original `instructions/general-instructions.md`):

`mvn clean verify -Dgroups="tags_to_run" -D"selenide.headless=true"`, where:   
`tags_to_run`: possible options - `API`, `UI`. Remove `-Dgroups` option from command to run everything.  
`selenide.headless` - `true` for headless, anything else or remove option for non-headless.

### Generate and view result report

In project `target` folder, after a finished test run:

`allure generate`
->
`allure open`

## CI setup with Github Actions

Script can be found in `.github/workflows`.  
Only manual trigger is currently available.  
To run in CI:
- Go to Actions section, https://github.com/BackbaseRecruitment/doomobou/actions,
- Click "Run Tests" action in left panel,
- Click "Run workflow"
  - Choose parameters: Branch - main; Test scenario tags - UI, API, or empty for all tests; headless or not
- Click "Run workflow"

Unfortunately in current state a proper Allure test result report is not published, as I don't have the required access in the repository.  
See https://github.com/ashikkumar23/github-action-with-allure-report, 3.2.  
(I do not have access to a Settings tab to set up Github Pages)

See `Run Tests #20` in Actions as an example of a successful run with expected results.

## Expected results

4 tests should fail (counting instances of parameterized tests as separate tests).  
More details and reasoning in code comments and in the evaluation report.
