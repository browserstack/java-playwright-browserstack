##  java-playwright-browserstack

## Setup
* Clone the repo
* Install dependencies `mvn install`
* Update credentials in the `/src/test/resources/*.conf.json` file with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings).
* For parallel testing, control the concurrency by setting the value for `parallel.count`. Junit 5 uses the following properties for parallelism:
  ```
  junit.jupiter.execution.parallel.enabled = true
  junit.jupiter.execution.parallel.mode.default = concurrent
  junit.jupiter.execution.parallel.config.strategy=fixed
  junit.jupiter.execution.parallel.config.fixed.parallelism=${parallel.count}
  ```
## Running your tests
* To run a sample tests, run `mvn test -P sample-test`
* Update `<parallel.count>` in `pom.xml` to set the number of parallel threads 

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
