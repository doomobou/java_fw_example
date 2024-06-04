# Application evaluation report

### Notes

This is by no means a fully exhaustive report. Due to limited time, and scope of what was needed to be prepared,  
it is mostly focused on the parts of functionality that were chosen as part of the task.

## Chosen functionality and test cases

### Chosen functionality (UI):
- Search an owner
- Register - Add a new owner

### Chosen functionality (API):
- /owners endpoint

Coverage definitely isn't full:
UI - because of lack of clear requirements, having to interpret actual app behaviour
API - because of inconsistencies between provided specification and actual behaviour,  
Only several most basic API scenarios are included
(e.g. GET /owners in spec is actually GET /owners/list;  
DELETE /owners/{ownerId} does not seem to be present at all, and it would be crucial for proper test data handling)

### Reasoning

Very simple, "core" functionality that would be part of most basic test suite, e.g. Smoke.
The scenarios check the base app functions on which some other functionality is based on (e.g. Edit owner).
Some data validation scenarios are chosen specifically to highlight "unusual" app behaviour in the context of the task.

## Issues found

### General comment

UI - no documentation available, so all "intended" behaviour is my personal, "common sense" interpretation - definitely not a proper approach in a real-world scenario.  

API - a specification is available, but in practice it differs from the actual application API. For example:
- GET /owners in spec is actually GET /owners/list
- DELETE /owners/{ownerId} is present in spec and apparently not present in app  
  (as seen in source code [here](https://github.com/spring-projects/spring-petclinic/blob/main/src/main/java/org/springframework/samples/petclinic/owner/OwnerController.java))  

Having no option to delete data entries in UI, and no option to do so in API is quite crucial, and forces to adopt a less than optimal test data approach.

### Specific issues

1. UI/API - no authentication mechanism present.
2. API - specification mismatch. GET /owners in spec is actually GET /owners/list.
3. API - specification mismatch. No DELETE /owners/{ownerId} option available.
4. API - usability issue. POST /owners with valid data to create new owner only returns 201, no response body.  
   Thus, more steps and API calls are needed in order to actually get and verify the created data (see TC-API-1 as example).
5. UI/API - requirements (and possibly usability) issue. No option to delete entries.
6. UI/API - requirements/usability issue. No validation for duplicate entries, at least for Owner.
7. UI/API - requirements/usability issue. Non-transparent field validation rules - mandatory fields, validation rules (see TC-UI-4 comment).
8. UI - requirements/functional issue. Non-obvious filtering behaviour in owners list, filtering does not work by "Firstname lastname",  
but does work by hidden Pet, Pet Type and Visit data (see TC-UI-8 and TC-UI-9).
9. UI - requirements/usability issue. Inconsistent error display in negative scenarios (see TC-UI-4 comment)
   - Sometimes there is nothing in UI and error can be found only in browser Network tab,
   - Sometimes there is a default browser notification with an unclear error message,
   - Inconsistent behaviour in changing page or remaining on the current one after an error.

This is by no means an exhaustive list, again, mostly focusing on the chosen functionality.

## Approach

### General comment

In the specific circumstances, such as:
- No ability to delete test data entries,
- No persistence of test data (changes are not saved with app relaunch)

The approach is as follows:
- Some reliance on default test data present in the app (would avoid as much as possible in a real-world scenario),
- Some scenarios have inescapable side effects (e.g. create an Owner), increasing amount of test data after each test run.  
  This has somewhat less impact if the app is always relaunched, thus resetting test data.


For example, "UI - Search an existing owner" scenario,
"Ideal" approach:
- Create a new unique owner via API
- Search for its data by different fields in UI, verify that only this exact entry is found
- Delete entry created in first step

Instead, this scenario works with data for one of the entries present in the app by default, without usual before/after hooks.

### Test automation solution limitations

No interaction with Pet/Type/Visit objects apart from mapping them from an API JSON response.  
E.g. Owner1 equals Owner2 if every field matches, excluding Pet data.  
Considering this a concession for limited time/scope of the task.

Consequently, the data types for Pet/Type/Visit fields are also the most basic for the mapping to work. See recommendations for possible improvements in this area.

## Risks

1. Lack of UI requirements leads to subjective interpretation of intended application functionality,  
  having to spend more time on exploratory testing to "discover" actual behaviour.
2. Inconsistency between provided API specification and actual API increases testing time,  
  which is additionally spent on either more exploratory testing, or analyzing app source code.
3. Scope of testing the whole app is bigger than possible within the allotted time, especially considering 2 previous risks  
  This is somewhat lessened by allowing focus on just 2 functionalities, but does produce a less comprehensive coverage in this evaluation.

## Recommendations

### Test application

- Proper requirements. UI - detailing scenarios, field validation rules. API - at least matching the actual behaviour.
- Ability to properly handle test data, delete entries.
- UI - consistent behaviour in similar situations, e.g. negative scenarios (see TC-UI-4 comment in test cases)

### Test automation solution, possible improvements

- Ability to run UI tests in different browsers, if required. Would need some minor code alteration.
- Ability to run UI tests in parallel, if the scope significantly increases. Would need code alteration.
- Proper field typing and validation for Pet, Type, Visit classes, currently even datetime fields are mapped to strings (works fine in current limited scope).
- Update Owner object handling to include Pet data in equality comparisons and other scenarios.
- Proper logging setup, for example with log4j. Rules for intended log levels (e.g. UI = info, API = debug), proper reporting integration of logs with Allure,
  attaching screenshots for failed scenarios, etc.
- Generally more detailed and informative logging - hooks for starting/finishing a scenario, test results.
- Ability to run tests in different environments, correspondingly varying configuration and test data. 
- Proper approach to Git flow - currently everything is in a single commit and in develop branch, in the future at least a basic trunk-based flow could be used, with develop branch being locked from direct pushing.
