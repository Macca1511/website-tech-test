package stepdefinitions;

import driver.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BookThisRoomPage {

    WebDriver driver = Driver.getDriver();

    /**
     * Click reserve now button.
     */
    @When("I click reserve now")
    public void clicks_reserve_now() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By submitLocator = By.id("doReservation");

        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(submitLocator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", submitButton);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        }
    }

    /**
     * Enters booking details.
     */
    @When("I complete the booking with the following details:")
    public void complete_booking_details(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> data = dataTable.asMap();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (Map.Entry<String, String> entry : data.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();
            By locator;

            switch (field) {
                case "forename":
                    locator = By.xpath(
                            "//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/div[1]/input");
                    break;
                case "lastname":
                    locator = By.xpath(
                            "//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/div[2]/input");
                    break;
                case "email":
                    locator = By.xpath(
                            "//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/div[3]/input");
                    break;
                case "phone":
                    locator = By.xpath(
                            "//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/div[4]/input");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field: " + field);
            }

            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            input.clear();
            input.sendKeys(value);

        }
    }

    /**
     * Click reserve now button.
     */
    @When("I click to confirm reservation")
    public void clicks_to_confirm_reservation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By submitLocator = By.xpath("//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/form/button[1]");

        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(submitLocator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", submitButton);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        }
    }

    /**
     * Checks the present of two possible outcomes when making a reservation.
     */
    @Then("I should see either booking confirmation or an error message")
    public void the_reservation_has_been_made() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    By successLocator = By.xpath("//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/h2");
    By errorLocator = By.xpath("//*[@id=\"__next_error__\"]/body/div/div/h2");

    boolean success = false;
    boolean error = false;

    try {
        WebElement confirmationText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id=\"root-container\"]/div/div[2]/div/div[2]/div/div/h2")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successLocator));
        assertEquals("Booking Confirmed", confirmationText.getText());
        success = true;
        System.out.println("Success message detected.");
    } catch (TimeoutException e) {
    }

    if (!success) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));
            error = true;
            System.out.println("Error message detected.");
        } catch (TimeoutException e) {
        }
    }

    if (!success && !error) {
        throw new AssertionError("Neither success nor error message appeared.");
    }
}

}
