import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.Text;

import java.io.File;


class ClearCompleted {
    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    void findClearCompleted() throws Exception {
        driver.get("https://todomvc.com/examples/angular/dist/browser/#/all");
        //Creating "to do list" items so that we can complete them and find the clear all link
        toDoInputBar("Do work now!" + Keys.ENTER);
        WebElement CreateItem = driver.findElement(By.xpath("//label[contains(text(), 'Do work now!')]"));
        Assertions.assertEquals("Do work now!", CreateItem.getText());
        toDoInputBar("Do work later!" + Keys.ENTER);
        WebElement CreateItem2 = driver.findElement(By.xpath("//label[contains(text(), 'Do work later!')]"));
        Assertions.assertEquals("Do work later!", CreateItem2.getText());
        //Changing the items to completed to display the clear completed link
        WebElement ToggleAll = driver.findElement(By.cssSelector(".toggle-all"));
        ToggleAll.click();
        WebElement CompletedLink = driver.findElement(By.cssSelector(".clear-completed"));
        CompletedLink.click();
        //Using a "try catch" method to check that the items were removed after clicking the "clear completed" link
        boolean Displayed;
        try {
            driver.findElement(By.xpath("//label[contains(text(), 'Do work now!')]"));
            driver.findElement(By.xpath("//label[contains(text(), 'Do work later!')]"));
            Displayed = true;
        } catch (NoSuchElementException e) {
            Displayed = false;
        }
        //Asserting the test has run successfully
        Assertions.assertFalse(Displayed);
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

