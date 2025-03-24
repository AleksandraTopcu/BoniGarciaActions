import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropTests {
    static WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html";

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
}
