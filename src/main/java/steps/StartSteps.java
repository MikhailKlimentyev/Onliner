package steps;

import org.openqa.selenium.WebDriver;
import pages.StartPage;

public class StartSteps {

    private StartPage startPage;

    public StartSteps(WebDriver driver) {
        startPage = new StartPage(driver);
    }

    public AutoFleaMarketSteps openPage() {
        startPage.openPage().isPageOpened();
        WebDriver driver = startPage.getDriver();
        return new AutoFleaMarketSteps(driver);
    }
}
