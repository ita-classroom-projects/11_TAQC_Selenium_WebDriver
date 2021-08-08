package com.softserve.edu;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FirstSeleniumTest {
    private final String BASE_URL = "http://taqc-opencart.epizy.com/";
    private final Long IMPLICITLY_WAIT_SECONDS = 10L;
    private final Long ONE_SECOND_DELAY = 1000L;
    private WebDriver driver;

    private void presentationSleep() {
        presentationSleep(1);
    }

    private void presentationSleep(int seconds) {
        try {
            Thread.sleep(seconds * ONE_SECOND_DELAY); // For Presentation ONLY
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        // System.setProperty("webdriver.chrome.driver", "./lib/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        // WebDriverManager.firefoxdriver().setup();
    }

    @BeforeClass
    public void beforeClass() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(IMPLICITLY_WAIT_SECONDS, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        presentationSleep(); // For Presentation ONLY
        // driver.close();
        driver.quit();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(BASE_URL);
        presentationSleep(); // For Presentation ONLY
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        presentationSleep(); // For Presentation ONLY
        if (!result.isSuccess()) {
            String testName = result.getName();
            System.out.println("***TC error, name = " + testName + " ERROR");
            // Take Screenshot, Save sourceCode, Save to log, Prepare report, Return to previous state, logout, etc.
            // driver.manage().deleteAllCookies(); // clear cache; delete cookie; delete session;
        }
        //driver.findElement(By.cssSelector("#logo .img-responsive")).click();
        //driver.findElement(By.cssSelector("#logo > a")).click();
        driver.findElement(By.xpath("//img[contains(@src, '/logo.png')]/..")).click();
        presentationSleep(); // For Presentation ONLY
    }

    @Test
    public void checkCanonSearch() {
        //
        // Choose Currency
        driver.findElement(By.cssSelector("button.btn.btn-link.dropdown-toggle")).click();
        presentationSleep(); // For Presentation ONLY
        driver.findElement(By.name("EUR")).click();
        //driver.findElement(By.name("GBP")).click();
        presentationSleep(); // For Presentation ONLY
        //
        // Type Canon to Search Field
        driver.findElement(By.name("search")).click();
        driver.findElement(By.name("search")).clear();
        driver.findElement(By.name("search")).sendKeys("Canon");
        presentationSleep(); // For Presentation ONLY
        //
        // Click Search Button
        driver.findElement(By.cssSelector("div#search button")).click();
        presentationSleep(); // For Presentation ONLY
        //
        // Click Add to Cart Button
        driver.findElement(By.cssSelector("div.product-layout button[onclick*='cart']")).click();
        Select options = new Select(driver.findElement(By.id("input-option226")));
        //options.selectByValue("16");
        options.selectByVisibleText("Blue");
        driver.findElement(By.id("button-cart")).click();
        presentationSleep(); // For Presentation ONLY
        //
        // Check if add to cart
        WebElement breadcrumb = driver.findElement(By.cssSelector("div.alert.alert-success"));
        String actual = breadcrumb.getText();
        Assert.assertTrue(actual.contains("Canon"));
        //
        // Check if cart contains item
        WebElement cart = driver.findElement(By.id("cart")).findElement(By.cssSelector("span"));
        actual = cart.getText();
        System.out.println("actual = " + actual);
        Assert.assertTrue(actual.contains("76.89"));
        //
        System.out.println("Euro symbol = \u20ac");
        Assert.assertTrue(actual.contains("\u20ac"));
        System.out.println("Pound Sterling symbol = " + "\u00A3");
        //Assert.assertTrue(actual.contains("\u00A3"));
        //
    }

    @DataProvider
    public Object[][] dataCurrencyChange() {
        return new Object[][] {
            { "EUR", "\u20ac" },
            { "GBP", "\u00A3" }, 
            { "USD", "$" },
        };
    }

    @Test(dataProvider = "dataCurrencyChange")
    public void checkCurrencyChange(String currencyName, String currencySymbol) {
        System.out.println("currencyName = " + currencyName + "\tcurrencySymbol = " + currencySymbol);
        //
        // TODO
        //
    }

    @DataProvider
    public Object[][] dataCheckMacSearch() {
        return new Object[][] {
            { "iMac" },
            { "MacBook" },
            { "MacBook Air" },
            { "MacBook Pro" },
        };
    }
    
    @Test(dataProvider = "dataCheckMacSearch")
    public void checkMacSearch(String expectedName) {
        // Expected search: iMac; MacBook; MacBook Air; MacBook Pro
        System.out.println("expectedName = " + expectedName);
        //
        // TODO
        //
    }

    @DataProvider
    public Object[][] dataCheckAddToCart() {
        return new Object[][] {
            { "Xiaomi Mi 8" },
            { "MacBook" },
        };
    }
    
    @Test(dataProvider = "dataCheckAddToCart")
    public void checkAddToCart(String searchName) {
        System.out.println("searchName = " + searchName);
        //
        // TODO
        //
    }

}
