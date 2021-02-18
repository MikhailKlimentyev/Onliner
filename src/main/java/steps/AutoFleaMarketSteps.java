package steps;

import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.AutoFleaMarketPage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AutoFleaMarketSteps {

    private AutoFleaMarketPage autoFleaMarketPage;

    public AutoFleaMarketSteps(WebDriver driver) {
        autoFleaMarketPage = new AutoFleaMarketPage(driver);
    }

    @Step("Open auto flea market link")
    public AutoFleaMarketSteps openAutoFleaMarketLink() {
        autoFleaMarketPage.clickOnAutoFleaMarketLink().isPageOpened();
        return this;
    }

    @Step("Select price {currency} currency, {fromPrice} fromPrice, {toPrice} toPrice")
    public AutoFleaMarketSteps selectPrice(String currency, String fromPrice, String toPrice) {
        autoFleaMarketPage.selectCurrency(currency)
            .enterFromPrice(fromPrice)
            .enterToPrice(toPrice);
        return this;
    }

    @Step("Open brand modal")
    public BrandSteps openBrandModal() {
        autoFleaMarketPage.clickOnBrandField()
            .isPageOpened();
        WebDriver driver = autoFleaMarketPage.getDriver();
        return new BrandSteps(driver);
    }

    @Step("Validate search labels {expectedBrand} expectedBrand, {expectedFromPrice} expectedFromPrice," +
        " {expectedToPrice} expectedToPrice")
    public AutoFleaMarketSteps validateSearchLabels(String expectedBrand, String expectedFromPrice, String expectedToPrice) {
        List<String> brandAndPriceInterval = autoFleaMarketPage.getBrandAndPriceInterval();
        Assert.assertEquals(brandAndPriceInterval.size(), 3,
            "Something from Brand or FromPrice or ToPrice is not displayed");
        Assert.assertEquals(brandAndPriceInterval.get(0), expectedBrand, "Brand does not match expected");
        Assert.assertEquals(brandAndPriceInterval.get(1), expectedFromPrice, "FromPrice does not match expected");
        Assert.assertEquals(brandAndPriceInterval.get(2), expectedToPrice, "ToPrice does not match expected");
        return this;
    }

    @Step("Validate founded brands on first page {expectedSize} expectedSize, {expectedBrand} expectedBrand")
    public AutoFleaMarketSteps validateFoundedBrandsOnFirstPage(int expectedSize, String expectedBrand) {
        List<String> models = autoFleaMarketPage.getModels();
        List<String> brands = models.stream().map(model -> StringUtils.left(model, 13))
            .collect(Collectors.toList());
        Assert.assertEquals(brands.size(), expectedSize, "Brands number does not match expected");
        brands.forEach(brand ->
            Assert.assertEquals(brand, expectedBrand, "Brand name does not match expected"));
        return this;
    }

    @Step("Validate USD price {expectedMinPrice} expectedMinPrice, {expectedMaxPrice} expectedMaxPrice")
    public AutoFleaMarketSteps validateUsdPrice(String expectedMinPrice, String expectedMaxPrice) {
        List<String> stringPrices = autoFleaMarketPage.getUsdEurPrices();
        List<Integer> prices = normalizePrices(stringPrices);
        Collections.sort(prices);
        Assert.assertTrue(prices.get(0) >= Integer.parseInt(expectedMinPrice),
            "Min price does not match expected");
        Assert.assertTrue(prices.get(prices.size() - 1) <= Integer.parseInt(expectedMaxPrice),
            "Max price does not match expected");
        return this;
    }

    private List<Integer> normalizePrices(List<String> stringPrices) {
        return stringPrices.stream()
            .map(price -> StringUtils.substringBefore(price, "$").trim())
            .map(price -> StringUtils.replace(price, " ", ""))
            .map(price -> Integer.valueOf(price))
            .collect(Collectors.toList());
    }
}
