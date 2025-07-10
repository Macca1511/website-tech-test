package stepdefinitions;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;

import driver.Driver;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Accessibility {

    WebDriver driver = Driver.getDriver();

    /**
     * Runs a lighthouse scan to check accessibility of a webpage.
     */
    @Then("I am able to run a Lighthouse accessibility scan")
    public void runLighthouseAccessibilityScan() throws IOException, InterruptedException {
        String url = driver.getCurrentUrl();

        // Edit username in path to point to local lighthouse
        String lighthousePath = "C:\\Users\\<username>\\AppData\\Roaming\\npm\\lighthouse.cmd";
        String outputPath = "lighthouse-report.json";

        ProcessBuilder builder = new ProcessBuilder(
                lighthousePath,
                url,
                "--only-categories=accessibility",
                "--quiet",
                "--output=json",
                "--output-path=" + outputPath,
                "--chrome-flags=--headless"
        );

        builder.redirectErrorStream(true);
        Process process = builder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("[Lighthouse] " + line);
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Lighthouse scan failed.");
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(new File(outputPath));
        double score = json.at("/categories/accessibility/score").asDouble() * 100;

        System.out.println("Lighthouse Accessibility Score: " + score);

        if (score < 90) {
            throw new AssertionError("Accessibility score is below threshold: " + score);
        }
    }
}
