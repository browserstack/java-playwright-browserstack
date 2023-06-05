##  java-playwright-browserstack

## Setup
* Clone the repo
* Install dependencies `mvn install`
* Update credentials in the `browserstack.yml` file with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings).

## Running tests:
* To run a sample tests, run `mvn test -P sample`.
* To run local tests, run `mvn test -P local`.

Understand how many parallel sessions you need by using our [Parallel Test Calculator](https://www.browserstack.com/automate/parallel-calculator?ref=github)

## Notes
* You can view your test results on the [BrowserStack Automate dashboard](https://www.browserstack.com/automate)
* You can export the environment variables for the Username and Access Key of your BrowserStack account.

  * For Unix-like or Mac machines:
  ```
  export BROWSERSTACK_USERNAME=<browserstack-username> &&
  export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
  
  * For Windows:
  ```
  set BROWSERSTACK_USERNAME=<browserstack-username>
  set BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
