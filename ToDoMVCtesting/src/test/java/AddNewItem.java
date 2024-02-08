import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
public class AddNewItem {
    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    // Your tests will go here!
    @Test
    void addingSingleItem() {
        driver.get("https://todomvc.com/examples/angular/dist/browser/#/all");
        //here we are adding a single character (a) to the to-do list and then checking it is where we expect it to be
        toDoInputBar("a" + Keys.ENTER);
        WebElement findA = driver.findElement(By.xpath("/html/body/app-root/section/app-todo-list/main/ul/app-todo-item/li/div/label"));
        Assertions.assertEquals("a", findA.getText());
        //here we are double-checking with the character (b)
        toDoInputBar("b" + Keys.ENTER);
        WebElement findB = driver.findElement(By.xpath("/html/body/app-root/section/app-todo-list/main/ul/app-todo-item[2]/li/div/label"));
        Assertions.assertEquals("b", findB.getText());
        //here we are checking that you can't add an empty item to the to-do list
        toDoInputBar("" + Keys.ENTER);
        toDoInputBar("c" + Keys.ENTER);
        //to check the empty item wasn't added, we added (c) to the list and made sure the 3rd item in the list wasn't an empty item
        WebElement findC = driver.findElement(By.xpath("/html/body/app-root/section/app-todo-list/main/ul/app-todo-item[3]/li/div/label"));
        Assertions.assertNotEquals("", findC.getText());

    }

    public void toDoInputBar(String toDoItem) {
        WebElement toDoInput = driver.findElement(By.cssSelector(".new-todo"));
        toDoInput.click();
        toDoInput.sendKeys(toDoItem);
    }


    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }
    public static void takeScreenshot(WebDriver webdriver, String desiredPath) throws Exception{
        TakesScreenshot screenshot = ((TakesScreenshot)webdriver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }
}
