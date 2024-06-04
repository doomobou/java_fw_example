package org.backbase.task;

import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.slf4j.Slf4j;
import org.backbase.task.config.Config;
import org.backbase.task.pages.HomePage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.Objects;

@Slf4j
public class BaseUiTest extends BaseTest {

    private SelenideDriver driver;

    protected HomePage homePage;

    void openBrowser() {
        var config = Config.getInstance();

        var chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notifications", "--window-size=1920,1080");

        var headless = System.getProperty("selenide.headless");
        if(Objects.nonNull(headless) && "true".equals(headless)) {
            chromeOptions.addArguments("--headless");
        }

        var webDriver = new ChromeDriver(chromeOptions);
        driver = new SelenideDriver(config, webDriver, null);
        WebDriverRunner.setWebDriver(driver.getWebDriver());

        driver.getWebDriver().manage().timeouts()
                .implicitlyWait(Duration.ofMillis(config.timeout()))
                .pageLoadTimeout(Duration.ofMillis(2 * config.timeout()));

        homePage = driver.open("", HomePage.class);
    }

    @BeforeEach
    public void setUp() {
        openBrowser();
    }

    @AfterEach
    public void tearDown() {
        driver.getWebDriver().quit();
    }
}
