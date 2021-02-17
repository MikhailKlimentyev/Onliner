package steps;

import org.openqa.selenium.WebDriver;
import pages.BrandModal;

public class BrandSteps {

    private BrandModal brandModal;

    public BrandSteps(WebDriver driver) {
        brandModal = new BrandModal(driver);
    }

    public AutoFleaMarketSteps selectBrand(String brand) {
        brandModal
            .enterTextIntoFindBrandInput(brand)
            .clickOnFirstFoundBrand();
        WebDriver driver = brandModal.getDriver();
        return new AutoFleaMarketSteps(driver);
    }
}
