import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class NavigationTests {
    static WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/navigation1.html";

    @BeforeAll
    static void setUp(){
        System.setProperty("webdriver.chrome.driver", "/Users/aineumestova/Downloads/chromedriver-mac-arm64/chromedriver");
        driver= new ChromeDriver();
        driver.get(BASE_URL);
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Открытие нужной страницы")
    void pageOpenTest() {
        WebElement title = driver.findElement(By.className("display-6"));
        Assertions.assertTrue(title.isDisplayed());
    }

    @Test
    @DisplayName("Переход на вторую страницу")
    void secondPageOpenTest() {
        WebElement secondPageIndex = driver.findElement(By.xpath("//a[@href='navigation2.html']"));
        secondPageIndex.click();
        WebElement secondPageText = driver.findElement(By.className("lead"));
        assertAll(
                ()-> assertEquals("https://bonigarcia.dev/selenium-webdriver-java/navigation2.html", driver.getCurrentUrl()),
                ()->assertTrue(secondPageText.getText().contains("Ut enim"))
        );
    }

    @Test
    @DisplayName("Переход на предыдущую страницу со второй страницы")
    void previousPageOpenFromSecondPageTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/navigation2.html");
        WebElement firstPageIndex = driver.findElement(By.xpath("//a[@href='navigation1.html' and text()='Previous']"));
        firstPageIndex.click();
        WebElement firstPageText = driver.findElement(By.className("lead"));
        assertAll(
                ()-> assertEquals("https://bonigarcia.dev/selenium-webdriver-java/navigation1.html", driver.getCurrentUrl()),
                ()->assertTrue(firstPageText.getText().contains("Lorem ipsum "))
        );
    }

    @Test
    @DisplayName("Переход на следующую страницу со второй страницы")
    void nextPageOpenFromSecondPageTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/navigation2.html");
        WebElement thirdPageIndex = driver.findElement(By.xpath("//a[@href='navigation3.html' and text()='Next']"));
        thirdPageIndex.click();
        WebElement thirdPageText = driver.findElement(By.className("lead"));
        assertAll(
                ()-> assertEquals("https://bonigarcia.dev/selenium-webdriver-java/navigation3.html", driver.getCurrentUrl()),
                ()->assertTrue(thirdPageText.getText().contains("Excepteur sint "))
        );
    }


    @Test
    @DisplayName("Переход по ссылке")
    void linkClickThroughTest() {
        WebElement linkLocator = driver.findElement(By.linkText("Back to index"));
        linkLocator.click();
        Assertions.assertEquals("https://bonigarcia.dev/selenium-webdriver-java/index.html", driver.getCurrentUrl());
    }


}
