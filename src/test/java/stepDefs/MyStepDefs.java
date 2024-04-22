package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class MyStepDefs {
    private WebDriver driver;

    private String getComplexRandomMail() {
        String lowerCharacters = "abcdefghijklmnopqrstuvwxyz";
        String upperCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numberCharacters = "0123456789";
        return RandomStringUtils.random(3, lowerCharacters)
                + RandomStringUtils.random(3, numberCharacters)
                + RandomStringUtils.random(3, upperCharacters)
                + System.currentTimeMillis()
                + "@mail.com";
    }

    private WebElement waitForPresence(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }


    @Given("the user is on the registration page using browser {string}")
    public void theUserIsOnTheRegistrationPageUsingBrowser(String browser) {
        if (browser.equals("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equals("edge")) {
            driver = new EdgeDriver();
        }
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @And("the user enters the date of birth {string}")
    public void theUserEntersTheDateOfBirth(String date) {
        driver.findElement(By.cssSelector("input[id='dp']")).sendKeys(date);
    }

    @And("the user enters first {string} and last {string} name")
    public void theUserEntersFirstAndLastName(String firstName, String lastName) {
        driver.findElement(By.cssSelector("input[id='member_firstname']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input[id='member_lastname']")).sendKeys(lastName);
    }

    @And("the user enters an email and confirms it")
    public void theUserEntersAnEmailAndConfirmsIt() {
        String randomMail = getComplexRandomMail();
        driver.findElement(By.cssSelector("input[id='member_emailaddress']")).sendKeys(randomMail);
        driver.findElement(By.cssSelector("input[id='member_confirmemailaddress']")).sendKeys(randomMail);
    }

    @And("the user enters a password {string}")
    public void theUserEntersAPassword(String password) {
        driver.findElement(By.cssSelector("input[id='signupunlicenced_password']")).sendKeys(password);
    }

    @And("the user retypes the password {string}")
    public void theUserRetypesThePassword(String retypePwd) {
        driver.findElement(By.cssSelector("input[id='signupunlicenced_confirmpassword']")).sendKeys(retypePwd + Keys.TAB);
    }


    @And("the user marks the terms and conditions {string}")
    public void theUserMarksTheTermsAndConditions(String terms) {
        if (terms.equals("Checked")) {
            driver.findElement(By.cssSelector("label[for='sign_up_25']")).click();
        }
    }

    @And("the user marks the checkbox for age confirmation")
    public void theUserMarksTheCheckboxForAgeConfirmation() {
        driver.findElement(By.cssSelector("label[for='sign_up_26']")).click();
    }

    @And("the user marks the checkbox for the code of ethics and conduct")
    public void theUserMarksTheCheckboxForCodeOfEthicsAndConduct() {
        driver.findElement(By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']")).click();
    }

    @When("the user clicks the confirm and join button")
    public void theUserClicksTheConfirmAndJoinButton() {
        driver.findElement(By.cssSelector("input[name='join']")).click();
    }

    @Then("the user should see the message {string} {string}")
    public void theUserShouldSeeTheMessage(String expMessage, String status) {
        WebElement element;
        String actual;

        if (status.equals("succeed")) {

            element = waitForPresence(By.cssSelector("body > div > div.page-content-wrapper > div > h2"));
            actual = element.getText();
            assertEquals(expMessage, actual);

        } else if (status.equals("fail")) {
            String cssSelector;

            switch (expMessage) {
                case "Last Name is required":
                    cssSelector = "#signup_form > div:nth-child(6) > div:nth-child(2) > div > span > span";
                    break;
                case "Password did not match":
                    cssSelector = "#signup_form > div:nth-child(9) > div > div.row > div:nth-child(2) > div > span > span";
                    break;
                case "You must confirm that you have read and accepted our Terms and Conditions":
                    cssSelector = "#signup_form > div:nth-child(12) > div > div:nth-child(2) > div:nth-child(1) > span > span";
                    break;
                default:
                    cssSelector = "";
                    break;
            }

            element = waitForPresence(By.cssSelector(cssSelector));
            actual = element.getText();
            assertEquals(expMessage, actual);
        }
    }


    @After
    public void tearDown() {
        driver.quit();
    }


}









