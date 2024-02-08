import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

public class BleedOverTest {
    private static ChromeDriver driver;

    @BeforeAll
    static void launchBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    void CheckingNumberOfItems() throws Exception {
        driver.get("https://todomvc.com/");
        WebElement findAngular = driver.findElement(By.cssSelector(".js-app-list:nth-child(1) li:nth-child(6) .link"));
        findAngular.click();
        Assertions.assertEquals("https://todomvc.com/examples/angular/dist/browser/#/all" , driver.getCurrentUrl());
        WebElement toDoInput = driver.findElement(By.cssSelector(".new-todo"));
        toDoInput.click();
        toDoInput.sendKeys("Make a cup of tea" + Keys.ENTER);
        takeScreenshot(driver, "checkToDoForTea.png");
        WebElement findCupOfTea = driver.findElement(By.xpath("/html/body/app-root/section/app-todo-list/main/ul/app-todo-item/li/div/label"));
        Assertions.assertEquals("Make a cup of tea", findCupOfTea.getText());
        driver.navigate().back();
        WebElement findVuejs = driver.findElement(By.cssSelector(".js-app-list:nth-child(1) li:nth-child(3) .link"));
        findVuejs.click();
        Assertions.assertEquals("https://todomvc.com/examples/vue/dist/#/" , driver.getCurrentUrl());
        takeScreenshot(driver, "checkToDoForNoTea.png");
        boolean NoItems = !driver.findElements(By.xpath("/html/body/section/main/ul")).isEmpty();
        Assertions.assertTrue(NoItems);
        //this could be easy to break but currently as there are no id's on here this is the safest way we know how to test (there may be better ways)
        driver.navigate().back();
        WebElement findAngularAgain = driver.findElement(By.cssSelector(".js-app-list:nth-child(1) li:nth-child(6) .link"));
        findAngularAgain.click();
        Assertions.assertEquals("https://todomvc.com/examples/angular/dist/browser/#/all" , driver.getCurrentUrl());
        //there is currently a bug on the site that stops it from saving the to-do lists, we are testing this expecting it to fail because hopefully this bug will get fixed at some point
//        WebElement findCupOfTeaAgain = driver.findElement(By.xpath("/html/body/app-root/section/app-todo-list/main/ul/app-todo-item/li/div/label"));
//        Assertions.assertEquals("Make a cup of tea", findCupOfTeaAgain.getText());
        boolean NoCupOfTea = !driver.findElements(By.xpath("/html/body/app-root/section/app-todo-list")).isEmpty();
        Assertions.assertTrue(NoCupOfTea);

    }

    public static void takeScreenshot(WebDriver webdriver, String desiredPath) throws Exception {
        TakesScreenshot screenshot = ((TakesScreenshot) webdriver);
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
        File targetFile = new File(desiredPath);
        FileUtils.copyFile(screenshotFile, targetFile);
    }
    @AfterAll
    static void closeBrowser() {
        driver.quit();
    }
}
