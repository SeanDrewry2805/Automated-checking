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
    void addingSingleItemAndEmptyItem() {
        driver.get("https://todomvc.com/examples/angular/dist/browser/#/all");
        //here we are adding a single character (a) to the to-do list and then checking it is where we expect it to be
        toDoInputBar("a" + Keys.ENTER);
        WebElement findA = driver.findElement(By.xpath("//label[contains(text(), 'a')]"));
        Assertions.assertEquals("a", findA.getText());
        //here we are double-checking with the character (b)
        toDoInputBar("b" + Keys.ENTER);
        WebElement findB = driver.findElement(By.xpath("//label[contains(text(), 'b')]"));
        Assertions.assertEquals("b", findB.getText());
        //here we are checking that you can't add an empty item to the to-do list
        toDoInputBar("" + Keys.ENTER);
        toDoInputBar("c" + Keys.ENTER);
        //to check the empty item wasn't added, we added (c) to the list and made sure the 3rd item in the list wasn't an empty item
        WebElement findC = driver.findElement(By.xpath("//label[contains(text(), 'c')]"));
        Assertions.assertNotEquals("", findC.getText());
    }
    //The test below will only work if ran with the whole file in the current state.
    @Test
    void addingSpecialAndAccentedCharacters() {
        driver.get("https://todomvc.com/examples/angular/dist/browser/#/all");
        //Testing with accented character
        toDoInputBar("svetlâna" + Keys.ENTER);
        WebElement findAccents = driver.findElement(By.xpath("//label[contains(text(), 'svetlâna')]"));
        Assertions.assertEquals("svetlâna", findAccents.getText());
        //Checking different accented character
        toDoInputBar("Pay Grëta" + Keys.ENTER);
        WebElement findAccents2 = driver.findElement(By.xpath("//label[contains(text(), 'Pay Grëta')]"));
        Assertions.assertEquals("Pay Grëta", findAccents2.getText());
        //The tests below are all to check special characters
        toDoInputBar("Clean kitchen/bathroom" + Keys.ENTER);
        WebElement findSLash = driver.findElement(By.xpath("//label[contains(text(), 'Clean kitchen/bathroom')]"));
        Assertions.assertEquals("Clean kitchen/bathroom", findSLash.getText());
        toDoInputBar("\"Wash the towels\"" + Keys.ENTER);
        WebElement findQuotes = driver.findElement(By.xpath("//label[contains(text(), '\"Wash the towels\"')]"));
        Assertions.assertEquals("\"Wash the towels\"", findQuotes.getText());
        toDoInputBar("Look for where the soap is @" + Keys.ENTER);
        WebElement findWhereTheAtAt = driver.findElement(By.xpath("//label[contains(text(), 'Look for where the soap is @')]"));
        Assertions.assertEquals("Look for where the soap is @", findWhereTheAtAt.getText());
        toDoInputBar("Use 6% bleach" + Keys.ENTER);
        WebElement findPercent = driver.findElement(By.xpath("//label[contains(text(), 'Use 6% bleach')]"));
        Assertions.assertEquals("Use 6% bleach", findPercent.getText());
        toDoInputBar("Buy scratch cards £3" + Keys.ENTER);
        WebElement findTheMoney = driver.findElement(By.xpath("//label[contains(text(), 'Buy scratch cards £3')]"));
        Assertions.assertEquals("Buy scratch cards £3", findTheMoney.getText());
        toDoInputBar("Work out 8*3" + Keys.ENTER);
        WebElement findAstrix = driver.findElement(By.xpath("//label[contains(text(), 'Work out 8*3')]"));
        Assertions.assertEquals("Work out 8*3", findAstrix.getText());
        toDoInputBar("Pop into B&Q" + Keys.ENTER);
        WebElement findAnd = driver.findElement(By.xpath("//label[contains(text(), 'Pop into B&Q')]"));
        Assertions.assertEquals("Pop into B&Q", findAnd.getText());
        toDoInputBar("You're done!" + Keys.ENTER);
        WebElement findExclamation = driver.findElement(By.xpath("//label[contains(text(), \"You're done!\")]"));
        Assertions.assertEquals("You're done!", findExclamation.getText());
        //Testing symbol inputs
        toDoInputBar("Do science stuff ☠☠☠" + Keys.ENTER);
        WebElement findBrainiac = driver.findElement(By.xpath("//label[contains(text(), 'Do science stuff ☠☠☠')]"));
        Assertions.assertEquals("Do science stuff ☠☠☠", findBrainiac.getText());
        //Emojis are unable to be used in chromedriver code. Alternate browser testing only?
//        toDoInputBar("Be happy \uD83D\uDE01" + Keys.ENTER);
//        WebElement findHappyFace = driver.findElement(By.xpath("/html/body/app-root/section/app-todo-list/main/ul/app-todo-item[14]/li/div/label"));
//        Assertions.assertEquals("Be happy \uD83D\uDE01", findHappyFace.getText());
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
