import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.Text;

import java.io.File;
public class StatusBar {
    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    // Your tests will go here!
    @Test
    void CheckingNumberOfItems() {
        driver.get("https://todomvc.com/examples/angular/dist/browser/#/all");
        //This test will check if the footer is not present when the to-do list is empty
        boolean NoItems = !driver.findElements(By.xpath("/html/body/app-root/section/app-todo-footer")).isEmpty();
        Assertions.assertTrue(NoItems);
        //We are adding an item to the list to see if the footer responds to the changes
        toDoInputBar("Clean my room" + Keys.ENTER);
        WebElement createItem = driver.findElement(By.xpath("/html/body/app-root/section/app-todo-list/main/ul/app-todo-item/li/div/label"));
        Assertions.assertEquals("Clean my room", createItem.getText());
        WebElement StatusLeft = driver.findElement(By.xpath("//span[@class='todo-count']"));
        //here we are asserting that the "item left" part matches with the number of items (if it was 2 it would be "items" as shown below)
        Assertions.assertEquals("1 item left", StatusLeft.getText());
        toDoInputBar("Wash the dishes" + Keys.ENTER);
        WebElement createItem2 = driver.findElement(By.xpath("/html/body/app-root/section/app-todo-list/main/ul/app-todo-item[2]/li/div/label"));
        Assertions.assertEquals("Wash the dishes", createItem2.getText());
        WebElement StatusLeftCheck= driver.findElement(By.xpath("//span[@class='todo-count']"));
        Assertions.assertEquals("2 items left", StatusLeftCheck.getText());

    }
    public void toDoInputBar(String toDoItem) {
        WebElement toDoInput = driver.findElement(By.cssSelector(".new-todo"));
        toDoInput.click();
        toDoInput.sendKeys(toDoItem);
    }
//    public WebElement toDoStatusBar() {
//        WebElement Filters = StatusBar.findElement(By.xpath("/html/body/app-root/section/app-todo-footer/footer/ul"));
//        return StatusBar;
//    }


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
