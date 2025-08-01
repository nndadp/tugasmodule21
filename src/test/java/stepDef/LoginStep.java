package stepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginStep {

    WebDriver driver;
    LoginPage loginPage;

    @Before
    public void beforeTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
    }


    @After
    public void afterTest() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("user is on login page")
    public void userIsOnLoginPage() {
        driver.get("https://www.saucedemo.com/v1/");
    }

    @When("user input username with {string}")
    public void userInputUsernameWith(String username) {
        loginPage.inputUsername(username);
    }

    @And("user input password with {string}")
    public void userInputPasswordWith(String password) {
        loginPage.inputPassword(password);
    }

    @And("user click login button")
    public void userClickLoginButton() {
        loginPage.clickLoginButton();
    }

    @Then("user will redirect to homepage")
    public void userWillRedirectToHomepage() {
        By productTitle = By.xpath("//*[@id=\"inventory_filter_container\"]/div");
        WebElement productElement = driver.findElement(productTitle);
        assertTrue(productElement.isDisplayed());
        assertEquals("Products", productElement.getText());
    }

    @Then("A message appears {string}")
    public void aMessageAppears(String errorMessage) {
        assertTrue(driver.getPageSource().contains(errorMessage));

    }

}
