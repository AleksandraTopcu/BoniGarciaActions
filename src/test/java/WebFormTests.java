import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebDriverException;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebFormTests {
    static WebDriver driver;
    private static final String BASE_URL = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html";
    private static final String textExample = "Это пример текста";

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
    @DisplayName("Ввод текста в поле input")
    void inputTextEntertTest(){
        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys(textExample);
        Assertions.assertEquals(textExample, textInput.getAttribute("value"), "Текст не равен введенному");
    }

    @Test
    @DisplayName("Отправка текста в поле input по нажатию enter")
    void inputTextKeysEnterTest() {
        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys(textExample);
        actions.sendKeys(textInput, Keys.ENTER).perform();
        Assertions.assertEquals("Form submitted",driver.findElement(By.className("display-6")).getText(), "Нужного заголовка нет");
    }

    @Test
    @DisplayName("Очистка текста в поле input по нажатию backspace")
    void textInputClearTest() {
        WebElement textInput = driver.findElement(By.id("my-text-id"));
        textInput.sendKeys(textExample);
        textInput.clear();
        Assertions.assertEquals("", textInput.getAttribute("value"), "Поле не до конца очистилось");
    }

    @Test
    @DisplayName("Ввод текста в поле password")
    void passwordTextEnterTest() {
        WebElement passwordField = driver.findElement(By.name("my-password"));
        passwordField.sendKeys(textExample);
        Assertions.assertEquals(textExample,passwordField.getAttribute("value"), "Пароль отображается в неправильном виде");
    }

    @Test
    @DisplayName("Попытка ввода текста в disabled поле") //TO DO: добавить дополнительную проверку на задизейбленность, что поле не реагирует
        void disabledFieldInputTest() {
            WebElement disabledInputField = driver.findElement(By.name("my-disabled"));
            Throwable exception = assertThrows(
                    WebDriverException.class,
                    ()->disabledInputField.sendKeys(textExample)
            );

        Assertions.assertTrue(
                exception.getMessage().contains("element not interactable"),
                "Ожидалось сообщение о недоступности элемента"
        );
    }

    @Test
    @DisplayName("Попытка ввода текста в readonly поле")
    void readonlyInputTest() {
        WebElement readonlyInputField = driver.findElement(By.name("my-readonly"));
        readonlyInputField.sendKeys(textExample);
        Assertions.assertEquals("Readonly input", readonlyInputField.getAttribute("value"));
    }

    @Test
    @DisplayName("Переход по ссылке")
    void linkClickThroughTest() {
        WebElement linkLocator = driver.findElement(By.linkText("Return to index"));
        linkLocator.click();
        Assertions.assertEquals("https://bonigarcia.dev/selenium-webdriver-java/index.html", driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Отображение элементов выпадающего списка - select") // TO DO: добвить проверку, что по клику на элемент он встает в поле + enter на сабмит формы
    void dropdownSelectElementsCheckTest() {
        WebElement dropdownSelectField = driver.findElement(By.className("form-select"));
        dropdownSelectField.click();
        List<WebElement> optionsList = driver.findElements(By.xpath("//select[@name='my-select']/option"));
        Assertions.assertTrue(optionsList.size() == 4);
    }

    @Test
    @DisplayName("Отображение элементов выпадающего списка - datalist") //TO DO: добавить проверку, что по клику на элемент он встает в поле + добавить тест на enter + на удаление из поля
    void dropdownDatalistElementsCheckTest() {
        WebElement dropdownDatalistField = driver.findElement(By.name("my-datalist"));
        dropdownDatalistField.click();
        List<WebElement> optionsList = driver.findElements(By.xpath("//datalist[@id='my-options']/option"));
        Assertions.assertTrue(optionsList.size() == 5);
    }

    @Test
    @DisplayName("Загрузка файла с компьютера")
    void fileUploadTest() {
        File uploadFile = new File("src/test/resources/image.jpeg");
        WebElement uploadFileField = driver.findElement(By.name("my-file"));
        uploadFileField.sendKeys(uploadFile.getAbsolutePath());
        Assertions.assertTrue(uploadFileField.getAttribute("value").contains("image.jpeg"));
    }

    @Test
    @DisplayName("Снятие галочки из чекбокса")
    void checkedCheckboxTest() {
        WebElement checkedCheckboxField = driver.findElement(By.id("my-check-1"));
        checkedCheckboxField.click();
        Assertions.assertFalse(checkedCheckboxField.isSelected(), "Чекбокс заполнен");
    }

    @Test
    @DisplayName("Заполнение чекбокса")
    void defaultCheckboxTest() {
        WebElement defaultCheckboxField = driver.findElement(By.id("my-check-2"));
        defaultCheckboxField.click();
        Assertions.assertTrue(defaultCheckboxField.isSelected(), "Чекбокс не заполнен");
    }

    @Test
    @DisplayName("Попытка выбора выбранного радиобаттона")
    void selectedRadioButtonSelectTest() {
        WebElement selectedRadioButton = driver.findElement(By.id("my-radio-1"));
        selectedRadioButton.click();
        Assertions.assertTrue(selectedRadioButton.isSelected());

    }

    @Test
    @DisplayName("Выбор невыбранного радиобаттона")
    void notSelectedRadioButtonSelectTest() {
        WebElement unselectedRadioButton = driver.findElement(By.id("my-radio-2"));
        unselectedRadioButton.click();
        Assertions.assertTrue(unselectedRadioButton.isSelected());
    }

    @Test
    @DisplayName("Отправка формы")
    void submitFormTest() {
        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(), 'Submit')]"));
        submitButton.click();
        WebElement formSubmittedText = driver.findElement(By.xpath("//h1[text()='Form submitted']"));
        Assertions.assertEquals("https://bonigarcia.dev/selenium-webdriver-java/submitted-form.html?my-text=&my-password=&my-textarea=&my-readonly=Readonly+input&my-select=Open+this+select+menu&my-datalist=&my-file=&my-check=on&my-radio=on&my-colors=%23563d7c&my-date=&my-range=5&my-hidden=", driver.getCurrentUrl());
        Assertions.assertTrue(formSubmittedText.isDisplayed());
    }

    @Test
    @DisplayName("Выбор даты")
    void datePickerTest() {
        WebElement datePickerField = driver.findElement(By.name("my-date"));
        datePickerField.click();
        WebElement nextMonthArrow = driver.findElement(By.className("next"));
        nextMonthArrow.click();
        WebElement dateSquare = driver.findElement(By.xpath("//td[@data-date='1744156800000']"));
        dateSquare.click();
        Assertions.assertEquals("04/09/2025", datePickerField.getAttribute("value"));
    }

    @Test
    @DisplayName("Выбор точки в диапазоне")
    void exampleRangeTest() {
        WebElement range = driver.findElement(By.className("form-range"));
        range.click();
        actions.sendKeys(Keys.ARROW_RIGHT).sendKeys(Keys.ARROW_RIGHT).perform();
        Assertions.assertEquals("7", range.getAttribute("value"));

    }
}

