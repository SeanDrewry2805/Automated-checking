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
import java.util.function.BooleanSupplier;

public class CompleteTasks {
    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }


    @Test
    void completingAnItem() throws Exception {
        driver.get("https://todomvc.com/examples/angular/dist/browser/#/all");
        //Creating two items for the list to be marked as complete or incomplete
        toDoInputBar("Do work now!" + Keys.ENTER);
        WebElement CreateItem = driver.findElement(By.xpath("//label[contains(text(), 'Do work now!')]"));
        Assertions.assertEquals("Do work now!", CreateItem.getText());
        toDoInputBar("Do work later!" + Keys.ENTER);
        WebElement CreateItem2 = driver.findElement(By.xpath("//label[contains(text(), 'Do work later!')]"));
        Assertions.assertEquals("Do work later!", CreateItem2.getText());
        //Finding the toggle complete beside a "to do list" item
        WebElement CheckBox = driver.findElement(By.cssSelector(".toggle"));
        //Using a "try catch" method to check that the item is not completed before the toggle is clicked
        boolean Displayed;
        try {
            driver.findElement(By.cssSelector(".completed"));
            Displayed = true;
        } catch (NoSuchElementException e) {
            Displayed = false;
        }
        //Marking first item as complete
        CheckBox.click();
        //Getting the variable for the "completed" toggle
        WebElement Completed = driver.findElement(By.cssSelector(".completed"));
        //Screenshotting to provide visible evidence that it is marked as complete
        takeScreenshot(driver, "screenshots/CheckingCheckbox.png");
        //Asserting that what we have done gives the correct boolean response
        Assertions.assertFalse(Displayed);
        Assertions.assertTrue(Completed.isDisplayed());
    }
    @Test
    void filterItems() throws Exception {
        driver.get("https://todomvc.com/examples/angular/dist/browser/#/all");
        //Assigning filter links to variables so they can be accessed by code
        WebElement AllButton = driver.findElement(By.partialLinkText("All"));
        WebElement ActiveButton = driver.findElement(By.partialLinkText("Active"));
        WebElement CompletedButton = driver.findElement(By.partialLinkText("Completed"));
        //Clicking and screenshotting each filtering process and asserting that the browser responds
        ActiveButton.click();
        Assertions.assertEquals("https://todomvc.com/examples/angular/dist/browser/#/active", driver.getCurrentUrl());
        takeScreenshot(driver, "screenshots/Activefilter.png");
        CompletedButton.click();
        Assertions.assertEquals("https://todomvc.com/examples/angular/dist/browser/#/completed", driver.getCurrentUrl());
        takeScreenshot(driver, "screenshots/Compltedfilter.png");
        AllButton.click();
        takeScreenshot(driver, "screenshots/Allfilter.png");
        Assertions.assertEquals("https://todomvc.com/examples/angular/dist/browser/#/all", driver.getCurrentUrl());
    }
    @Test
    void massCompleteItems() throws Exception {
        driver.get("https://todomvc.com/examples/angular/dist/browser/#/all");
        //Assigning the toggle arrow to a variable so that we can check when items are mass completed
        WebElement ToggleAll = driver.findElement(By.cssSelector(".toggle-all"));
        ToggleAll.click();
        takeScreenshot(driver, "screenshots/TickedAll.png");
        ToggleAll.click();
        takeScreenshot(driver, "screenshots/UntickedAll.png");
        ToggleAll.click();
        //Checking that the status bar responds to the changes made by the toggle arrow
        WebElement StatusLeft = driver.findElement(By.xpath("//span[@class='todo-count']"));
        Assertions.assertEquals("0 items left", StatusLeft.getText());
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