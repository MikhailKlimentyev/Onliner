package pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.AutoFleaMarketPage;

public abstract class BasePage extends AbstractPage {

    public static final By AUTO_FLEA_MARKET_LINK_LOCATOR = By.xpath("(//*[@class='b-main-navigation__text'])[3]");

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public AutoFleaMarketPage clickOnAutoFleaMarketLink() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(AUTO_FLEA_MARKET_LINK_LOCATOR).click();
        return new AutoFleaMarketPage(driver);
    }

    public abstract AbstractPage openPage();
}
