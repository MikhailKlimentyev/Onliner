package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import pages.BrandModal;

public class BrandSteps {

    private BrandModal brandModal;

    public BrandSteps(WebDriver driver) {
        brandModal = new BrandModal(driver);
    }

    @Step("Select brand {brand} brand")
    public AutoFleaMarketSteps selectBrand(String brand) {
        brandModal
            .enterTextIntoFindBrandInput(brand)
            .clickOnFirstFoundBrand();
        WebDriver driver = brandModal.getDriver();
        return new AutoFleaMarketSteps(driver);
    }
}
