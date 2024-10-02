

import com.makersacademy.acebook.Application;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class NavBarTest {
    WebDriver driver;
    @Before
    public void setup() {
        System.out.println("TESTTTTTTING THE BEFORE EACH");
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        driver = new ChromeDriver();
    }
    @After
    public void tearDown() {
        driver.close();
    }
    @Test
    public void confirmNavBarLinksExist() {
        // Signup, login, then loads the homepage, then assert
        // Navigate to your web application
        driver.get("http://localhost:8080/");
//        driver.findElement(By.linkText("Sign up")).click();
        driver.findElement(By.name("username")).sendKeys("test@mail.com");
        driver.findElement(By.name("password")).sendKeys("Test123!");
        driver.findElement(By.name("action")).click();
        // Find and check if the "Feed" link exists
        WebElement feedLink = driver.findElement(By.linkText("Feed"));
        Assert.assertEquals("Feed", feedLink.getText());
        // Find and check if the "Feed" link exists
        WebElement friendsLink = driver.findElement(By.linkText("Friends"));
        Assert.assertEquals("Friends", friendsLink.getText());
        // Find and check if the "Feed" link exists
        WebElement myPostsLink = driver.findElement(By.linkText("My Posts"));
        Assert.assertEquals("My Posts", myPostsLink.getText());
        // Find and check if the "Feed" link exists
        WebElement profileLink = driver.findElement(By.linkText("Profile"));
        Assert.assertEquals("Profile", profileLink.getText());
    }

    @Test
    public void confirmNavBarLinksRedirectCorrectly() {

        driver.get("http://localhost:8080/");


        driver.findElement(By.name("username")).sendKeys("test@mail.com");
        driver.findElement(By.name("password")).sendKeys("Test123!");
        driver.findElement(By.name("action")).click();
        driver.findElement(By.id("friends")).click();

        WebElement h1 = driver.findElement(By.linkText("Friends"));
        Assert.assertEquals("Friends", h1.getText());

    }
}