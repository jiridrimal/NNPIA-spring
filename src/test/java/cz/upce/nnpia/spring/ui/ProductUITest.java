package cz.upce.nnpia.spring.ui;

import cz.upce.nnpia.spring.datafactory.Creator;
import cz.upce.nnpia.spring.entity.Product;
import cz.upce.nnpia.spring.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(Creator.class)
public class ProductUITest {

    private WebDriver driver;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    Creator creator;

    @BeforeAll
    public static void setupWebdriverChromeDriver() {
        String chromedriverPath = ProductUITest.class.getResource("/chromedriver.exe").getFile();
        System.setProperty("webdriver.chrome.driver", chromedriverPath);
    }

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        productRepository.deleteAll();
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void addProductTest() {
        driver.get("http://localhost:8080/product-form");
        driver.findElement(By.id("name")).sendKeys("Test Product");
        driver.findElement(By.id("file")).sendKeys("C:\\Users\\Jirka\\docasnyGit\\NNPIA-spring\\images\\sample.jpg");
        driver.findElement(By.id("rating")).sendKeys("5");
        driver.findElement(By.id("price")).sendKeys("100000000");
        driver.findElement(By.id("displayed")).sendKeys("1");
        driver.findElement(By.id("inStock")).sendKeys("1");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        Assert.assertEquals(1,driver.findElements(By.xpath("//h2[text()='Product list']")).size());

        Assert.assertEquals(1,driver.findElements(By.xpath("//h3[text()='Test Product']")).size());

    }
}