package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.AbstractPage;

import java.util.List;

public class BrandModal extends AbstractPage {

    public static final By BRAND_LABEL_LOCATOR =
        By.cssSelector(".dropdown-style__list.dropdown-style__list_brand .dropdown-style__checkbox-label");
    public static final By BRAND_INPUT_LOCATOR =
        By.xpath("//*[contains(@class, 'dropdown-style__top')]//input[@type='text'][contains(@class, 'input-style')]");
    public static final By FOUND_BRANDS_LOCATOR = By.xpath("//*[text()='Все марки']/following-sibling::" +
        "*[@class='dropdown-style__list']//*[@class='dropdown-style__checkbox-sign']");

    public BrandModal(WebDriver driver) {
        super(driver);
    }

    public BrandModal enterTextIntoFindBrandInput(String text) {
        driver.findElement(BRAND_INPUT_LOCATOR).sendKeys(text);
        return this;
    }

    public AutoFleaMarketPage clickOnFirstFoundBrand() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FOUND_BRANDS_LOCATOR));
        List<WebElement> elements = driver.findElements(FOUND_BRANDS_LOCATOR);
        WebElement webElement = elements.get(0);
        webElement.click();
        return new AutoFleaMarketPage(driver);
    }

    @Override
    public BrandModal isPageOpened() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(BRAND_LABEL_LOCATOR));
        return this;
    }
}
