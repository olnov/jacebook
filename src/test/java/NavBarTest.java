

import com.makersacademy.acebook.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        driver = new ChromeDriver();
    }
    @After
    public void tearDown() {
//        driver.close();
    }
    @Test
    public void confirmNavBarLinksExist() {
        // Signup, login, then loads the homepage, then assert
        // Navigate to your web application
        driver.get("http://localhost:8080/");
        // Find and check if the "Home" link exists
        WebElement homeLink = driver.findElement(By.linkText("Home"));
        assertNotNull("Home link should exist on the page", homeLink);
        // Find and check if the "About" link exists
        WebElement aboutLink = driver.findElement(By.linkText("About"));
        assertNotNull("About link should exist on the page", aboutLink);
        // Find and check if the "Contact" link exists
        WebElement contactLink = driver.findElement(By.linkText("Contact"));
        assertNotNull("Contact link should exist on the page", contactLink);
    }
}