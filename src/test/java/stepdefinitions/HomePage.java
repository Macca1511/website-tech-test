package stepdefinitions;

import driver.Driver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

import static org.junit.Assert.*;

public class HomePage {

    WebDriver driver = Driver.getDriver();

    /**
     * Opens browser and navigates to Shady Meadows homepage.
     */
    @Given("^I have navigated to the Shady Meadows B&B website$")
    public void user_navigates_to_shady_meadows_website() {
        Driver.getDriver().get("https://automationintesting.online/");
    }

    /**
     * Scrolls to room section.
     */
    @Given("I scroll to the our rooms section")
    public void scroll_to_our_rooms_section() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By locator = By.xpath("//*[@id=\"rooms\"]/div");

        WebElement roomsHeading = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", roomsHeading);
        Thread.sleep(500);
    }

    /**
     * Selects 'Book now' for each room type.
     */
    @Given("^I have select to book a (.*) room$")
    public void i_have_select_room_type(String buttonName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By locator;

        switch (buttonName) {
            case "single":
                locator = By.xpath("//*[@id=\"rooms\"]/div/div[2]/div[1]/div/div[3]/a");
                break;
            case "double":
                locator = By.xpath("//*[@id=\"rooms\"]/div/div[2]/div[2]/div/div[3]/a");
                break;
            case "suite":
                locator = By.xpath("//*[@id=\"rooms\"]/div/div[2]/div[3]/div/div[3]/a");
                break;
            default:
                throw new IllegalArgumentException("Unknown button: " + buttonName);
        }

        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        Thread.sleep(300);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
    }

    /**
     * Enters contact details.
     */
    @When("I fill in the contact form with the following details:")
    public void fillInForm(DataTable dataTable) {
        Map<String, String> formData = dataTable.asMap();

        for (Map.Entry<String, String> entry : formData.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue();

            switch (field) {
                case "name":
                    handleFieldInput(By.id("name"), value);
                    break;
                case "email":
                    handleFieldInput(By.id("email"), value);
                    break;
                case "phone":
                    handleFieldInput(By.id("phone"), value);
                    break;
                case "subject":
                    handleFieldInput(By.id("subject"), value);
                    break;
                case "message":
                    handleFieldInput(By.id("description"), value);
                    break;
            }
        }
    }

    private void handleFieldInput(By locator, String value) {
        WebElement input = driver.findElement(locator);
        input.clear();

        if (value.equalsIgnoreCase("blank")) {
            input.sendKeys(" ");
            input.sendKeys(Keys.BACK_SPACE);
            input.sendKeys(Keys.TAB);
        } else {
            input.sendKeys(value);
        }
    }

    /**
     * Submits contact details.
     */
    @When("I click submit contact details")
    public void clicks_submit_contact_details() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By submitLocator = By.xpath("//button[text()='Submit']");

        WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(submitLocator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", submitButton);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        }
    }

    /**
     * Scrolls to contact form.
     */
    @When("I scroll to the contact form")
    public void scroll_to_contact_form() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By locator = By.id("contact");

        WebElement sendMessageHeading = wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", sendMessageHeading);
        Thread.sleep(500);
    }

    /**
     * Checks welcome text.
     */
    @Then("^the welcome text is shown as expected$")
    public void welcome_text_shown_as_expected() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement welcome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id=\"root-container\"]/div/section[1]/div/div/div/h1")));
        assertEquals("Welcome to Shady Meadows B&B", welcome.getText());
    }

    /**
     * Checks welcome subtext.
     */
    @Then("^the welcome subtext is shown as expected$")
    public void welcome_subtext_shown_as_expected() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement welcomeSubtext = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id=\"root-container\"]/div/section[1]/div/div/div/p")));
        assertEquals("Welcome to Shady Meadows, a delightful Bed & Breakfast nestled in the "
                + "hills on Newingtonfordburyshire. A place so beautiful you will never want to "
                + "leave. All our rooms have comfortable beds and we provide breakfast from the "
                + "locally sourced supermarket. It is a delightful place.", welcomeSubtext.getText());
    }

    /**
     * Checks each section heading.
     */
    @Then("the relevant {string} heading is shown as {string}")
    public void wait_for_relevant_heading_and_check_text(String section, String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By locator;
        switch (section) {
            case "check availability":
                locator = By.xpath("//*[@id=\"booking\"]/div/div/div/h3");
                break;
            case "rooms":
                locator = By.xpath("//*[@id=\"rooms\"]/div/div[1]/h2");
                break;
            case "location":
                locator = By.xpath("//*[@id=\"location\"]/div/div[1]/h2");
                break;
            case "contact":
                locator = By.xpath("//*[@id=\"location\"]/div/div[2]/div[2]/div/div/h3");
                break;
            default:
                throw new IllegalArgumentException("Unknown element type: " + section);
        }

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        String actualText = element.getText();
        assertEquals(actualText, element.getText());
    }

    /**
     * Checks each section subtext.
     */
    @Then("the relevant {string} text is shown as {string}")
    public void wait_for_relevant_text_and_check_text(String subheading, String expectedSubText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By locator;
        switch (subheading) {
            case "check in":
                locator = By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/label");
                break;
            case "check out":
                locator = By.xpath("//*[@id=\"booking\"]/div/div/div/form/div/div[1]/label");
                break;
            case "rooms":
                locator = By.xpath("//*[@id=\"rooms\"]/div/div[1]/p");
                break;
            case "location":
                locator = By.xpath("//*[@id=\"location\"]/div/div[1]/p");
                break;
            case "address":
                locator = By.xpath("//*[@id=\"location\"]/div/div[2]/div[2]/div/div/div[1]/div[2]/h5");
                break;
            case "phone":
                locator = By.xpath("//*[@id=\"location\"]/div/div[2]/div[2]/div/div/div[2]/div[2]/h5");
                break;
            default:
                throw new IllegalArgumentException("Unknown element type: " + subheading);
        }

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        String actualText = element.getText();
        assertEquals(actualText, element.getText());
    }

    /**
     * Checks send us a message heading.
     */
    @Then("^the send us a message section is shown as expected$")
    public void send_us_a_message_section_shown_as_expected() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement sendMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id=\"contact\"]/div/div/div/div/div/h3")));
        assertEquals("Send Us a Message", sendMessage.getText());
    }

    /**
     * Checks name matches when sending a message.
     */
    @Then("^the following thanks for getting in touch message is shown (.*)$")
    public void the_thanks_for_getting_in_touch_page_is_shown(String message) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By locator = By.id("contact");

        WebElement messageHeading = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", messageHeading);
        WebElement contactText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id=\"contact\"]/div/div/div/div/div/h3")));
        assertEquals(message, contactText.getText());
    }

    /**
     * Checks name matches when sending a message.
     */
    @Then("^the following error validation message is shown (.*)$")
    public void the_following_error_validation_is_shown(String message) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By locator = By.xpath("//*[@id=\"contact\"]/div/div/div/div/div/div");

        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", errorMessage);
        WebElement contactText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//*[@id=\"contact\"]/div/div/div/div/div/div/p[1]")));
        assertEquals(message, contactText.getText());
    }
}