package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.base.BasePage;

public class StartPage extends BasePage {

    public static final String START_PAGE_LINK = "https://www.onliner.by/";

    public static final By OPINIONS_PORTRAIT_LOCATOR = By.cssSelector(".b-opinions-main-2__portrait");

    public StartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public StartPage openPage() {
        driver.get(START_PAGE_LINK);
        return this;
    }

    @Override
    public StartPage isPageOpened() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(OPINIONS_PORTRAIT_LOCATOR));
        return this;
    }
}
