import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DropdownTests {
    static WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html";

    Actions actions = new Actions(driver);

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
    @DisplayName("Открытие дропдауна левой кнопкой мыши")
    void leftClickDropdownTest() {
        WebElement leftClickDropdownMenu = driver.findElement(By.id("my-dropdown-1"));
        actions.click(leftClickDropdownMenu).perform();
        List<WebElement> dropdownOptions = driver.findElements(By.xpath("//ul[@class='dropdown-menu show']/li/a[@class='dropdown-item']"));
        Assertions.assertEquals(4, dropdownOptions.size());
        assertAll(
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Action")))),
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Another action")))),
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Something else here")))),
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Separated link"))))
        );
    }

    @Test
    @DisplayName("Открытие дропдауна правой кнопкой мыши")
    void rightClickDropdownTest() {
        WebElement rightClickDropdownMenu = driver.findElement(By.id("my-dropdown-2"));
        actions.contextClick(rightClickDropdownMenu).perform();
        List<WebElement> dropdownOptions = driver.findElements(By.xpath("//ul[@id='context-menu-2']/li/a[@class='dropdown-item']"));
        Assertions.assertEquals(4, dropdownOptions.size());
        assertAll(
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Action")))),
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Another action")))),
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Something else here")))),
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Separated link"))))
        );
    }

    @Test
    @DisplayName("Открытие дропдауна даблкликом мыши")
    void doubleClickDropdownTest() {
        WebElement doubleClickDropdownMenu = driver.findElement(By.id("my-dropdown-3"));
        actions.doubleClick(doubleClickDropdownMenu).perform();
        List<WebElement> dropdownOptions = driver.findElements(By.xpath("//ul[@id='context-menu-3']/li/a[@class='dropdown-item']"));
        Assertions.assertEquals(4, dropdownOptions.size());
        assertAll(
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Action")))),
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Another action")))),
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Something else here")))),
                ()-> assertTrue(dropdownOptions.contains(driver.findElement(By.linkText("Separated link"))))
        );
    }



}
