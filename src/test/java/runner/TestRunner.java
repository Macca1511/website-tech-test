package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"utility","stepdefinitions"},
        plugin = {"pretty", "html:target/cucumber-report.html"},
        monochrome = true,
        tags = "@test"
)

public class TestRunner {
}

