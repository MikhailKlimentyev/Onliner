package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.BasePage;

import java.util.List;
import java.util.stream.Collectors;

public class AutoFleaMarketPage extends BasePage {

    public static final String AUTO_FLEA_MARKET_PAGE_LINK = "https://ab.onliner.by/";

    public static final By CAR_PICTURE_LOCATOR = By.cssSelector(".vehicle-form__panorama-item .vehicle-form__image");
    public static final By BRAND_FIELD_LOCATOR = By.xpath("//*[text()='Марка']/following-sibling::*[@class='input-style__real']");
    public static final By BRAND_LABEL_LOCATOR = By.cssSelector(".vehicle-form__button_tag");
    public static final By MODEL_LOCATOR =
        By.cssSelector(".vehicle-form__link.vehicle-form__link_primary-alter.vehicle-form__link_middle.vehicle-form__link_noreflex");
    public static final By USD_EUR_PRICE_LOCATOR =
        By.cssSelector(".vehicle-form__offers-part.vehicle-form__offers-part_price" +
            " .vehicle-form__description.vehicle-form__description_tiny.vehicle-form__description_other");

    public static String currencyLocatorPattern = "//*[text()='%s']";
    public static String priceInputPattern = "//*[contains(text(), 'Цена')]/ancestor::" +
        "*[contains(@class, 'vehicle-form__row')]//*[@placeholder='%s']";

    public AutoFleaMarketPage(WebDriver driver) {
        super(driver);
    }

    public BrandModal clickOnBrandField() {
        WebElement element = driver.findElement(BRAND_FIELD_LOCATOR);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
        return new BrandModal(driver);
    }

    public AutoFleaMarketPage selectCurrency(String currency) {
        By currencyLocator = By.xpath(String.format(currencyLocatorPattern, currency));
        driver.findElement(currencyLocator).click();
        return this;
    }

    public AutoFleaMarketPage enterFromPrice(String price) {
        enterPrice(price, "от");
        return this;
    }

    public AutoFleaMarketPage enterToPrice(String price) {
        enterPrice(price, "до");
        return this;
    }

    public List<String> getBrandAndPriceInterval() {
        List<WebElement> elements = getSearchResultsOnPage(BRAND_LABEL_LOCATOR);
        return getSearchResultsNames(elements);
    }

    public List<String> getUsdEurPrices() {
        List<WebElement> elements = getSearchResultsOnPage(USD_EUR_PRICE_LOCATOR);
        return getSearchResultsNames(elements);
    }

    public List<String> getModels() {
        List<WebElement> elements = getSearchResultsOnPage(MODEL_LOCATOR);
        return getSearchResultsNames(elements);
    }

    @Override
    public AutoFleaMarketPage openPage() {
        driver.get(AUTO_FLEA_MARKET_PAGE_LINK);
        return this;
    }

    @Override
    public AutoFleaMarketPage isPageOpened() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(CAR_PICTURE_LOCATOR));
        return this;
    }

    private void enterPrice(String price, String priceIndex) {
        By priceLocator = By.xpath(String.format(priceInputPattern, priceIndex));
        WebElement element = driver.findElement(priceLocator);
        new Actions(driver).sendKeys(element, price).perform();
        element.sendKeys(Keys.ENTER);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<String> getSearchResultsNames(List<WebElement> elements) {
        return elements.stream().map(element -> element.getText()).collect(Collectors.toList());
    }

    private List<WebElement> getSearchResultsOnPage(By locator) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver.findElements(locator);
    }
}
