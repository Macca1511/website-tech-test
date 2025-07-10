# Shady Meadows B&B Test Automation

## Tools

Java - programming language

Selenium - Browser automtion

Cucumber - BDD and gherkin syntax

Junit - Test Runner

Maven - Dependencies and build management

Itellij - IDE

## Running the tests

1. Open the project in Intellij
2. Right-click the 'TestRunner.java' class
3. Select **Run 'TestRunner'**
 
**From the command line (Maven)**

All tests tagged as @test will run when executing the following command:

mvn test

By default, this will run all the tests in Chrome. If you wish to run them using Edge or Firefox, use the following:

mvn test -DbrowserProfile=edge

or

mvn test -DbrowserProfile=firefox

If having issues you can also change the Config file to default to the browser you wish to use

```e.g. return props.getProperty("browser", "edge");```

## Tags

@test – will run by default

@defect - have been excluded from the test run as these either fail or have intermittent failures due to raised issues.

To run a specific tag use the following command:

'mvn test -Dcucumber.filter.tags="@tagname"'

## Reports

Reports are generated after each run and can be found in the following folder within the project:

target/cucumber-report.html

## Accessibility

The accessibility tests currently scan the webpages using Lighthouse. This test is currently tagged as @defect, therefore does not get run as part of the build. This is because the accessibility score is below 80.

**Pre-requisite**

Ensure you have node.js (https://nodejs.org/en) installed before installing Lighthouse. (check via Bash ```npm -v```)
In order to run this test ensure you have Lighthouse installed via the following method:

**Using Bash**

```npm install -g lighthouse```

**Confirm installation**

```Lighthouse - - version``` (version number should show if installed e.g. 12.7.1)

You should now be able to run the accessibility test.

### Important

Before running amend the ```Accessibility.java``` file to point to where Lighthouse was installed. 

e.g. ```String lighthousePath = "C:\\Users\\**<Username>**\\AppData\\Roaming\\npm\\lighthouse.cmd";```

**Not running**

If it doesn’t run (e.g. command not found)

Add npm global to your system path (only if Lighthouse doesn’t work)

**On Windows:**

a.	Copy path from npm bin -g (e.g. \Users\YourName\AppData\Roaming\npm)

b.	Open System Properties > Environment Variables

c.	Under System Variables, find and edit Path

d.	Click New, and paste the path

e.	Click OK, then restart your terminal or the IDE (e.g.Intellij)

On macOS/Linus:

Add this line to your

```~/ .bashrc, ~/ .zshrc, or ~/ .bash_profile```

**Bash**

```Export PATH=”$PATH: $(npm bin -g)”```

Then run:

**Bash**

```source ~/ .bashrc or ~/ .zshrc``` (depending on your shell) 

## Issues

Unfortunately I wasn't able to get the cross browser testing to run locally but would fix this issue with more time.

The tests currently add ```WebDriverWait``` in the step definition files. This was done to counteract the fact the website takes a while to load but through further iterations and better performance these wait can be removed.
