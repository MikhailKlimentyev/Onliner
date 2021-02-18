package tests;

import io.qameta.allure.Feature;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.StartSteps;

import java.util.concurrent.TimeUnit;

@Feature("OnlinerTest")
public class OnlinerTest {

    private WebDriver driver;
    private StartSteps startSteps;

    @BeforeMethod
    public void setUp() {
        System.setProperty("console.encoding", "UTF-8");
        System.setProperty("file.encoding", "UTF-8");
        String browser = System.getProperty("browser", "chrome");
        String driverPath = "src/main/resources/webdrivers";
        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", String.format("%s/chromedriver.exe", driverPath));
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            options.addArguments("--no-sandbox");
        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", String.format("%s/geckodriver.exe", driverPath));
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        startSteps = new StartSteps(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Test car search by brand and USD prices range")
    public void testCarSearchByBrandAndUsdPricesRange() {
        String brand = System.getProperty("brand", "Mercedes-Benz");
        String fromPrice = System.getProperty("fromPrice", "5000");
        String toPrice = System.getProperty("toPrice", "12000");
        String normalizedFromPrice = getNormalizedPrice(fromPrice);
        String expectedFromPrice = "От " + normalizedFromPrice + " $";
        String normalizedToPrice = getNormalizedPrice(toPrice);
        String expectedToPrice = "До " + normalizedToPrice + " $";
        startSteps
            .openPage()
            .openAutoFleaMarketLink()
            .openBrandModal()
            .selectBrand(brand)
            .selectPrice("USD", fromPrice, toPrice)
            .validateSearchLabels(brand, expectedFromPrice, expectedToPrice)
            .validateFoundedBrandsOnFirstPage(50, brand)
            .validateUsdPrice(fromPrice, toPrice);
    }

    private String getNormalizedPrice(String price) {
        String normalizedPrice = price;
        if (price.length() >= 5 && price.length() <= 6) {
            String firstPartSubstring = "";
            String secondPartSubstring = StringUtils.substring(price, price.length() - 3);
            if (price.length() == 5) {
                firstPartSubstring = StringUtils.substring(price, 0, 2);
            } else {
                firstPartSubstring = StringUtils.substring(price, 0, 3);
            }
            normalizedPrice = firstPartSubstring + " " + secondPartSubstring;
        }
        return normalizedPrice;
    }
}
