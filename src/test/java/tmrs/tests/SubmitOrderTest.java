package tmrs.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import org.testng.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tmrs.TestComponents.BaseTest;
import tmrs.pageobjects.*;

public class SubmitOrderTest extends BaseTest {

    @Test (dataProvider = "getData", groups = {"Purchase"} )
    public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {

        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("product"));
        CartPage cartPage = productCatalogue.goToCartPage();

        Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
        Assert.assertTrue(match);
        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }
    // Verify that "IPHONE 13 PRO" is on the OrderPage

    @Test(dependsOnMethods = "submitOrder")
    public void orderHistoryTest() {
        String productName = "IPHONE 13 PRO";

        // "IPHONE 13 PRO"
        ProductCatalogue productCatalogue = landingPage.loginApplication("blanka.cruz@gmail.com", "Iambeloved@000");
        OrderPage ordersPage = productCatalogue.goToOrdersPage();
        Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
    }

    // Extent Reports...

    
    @DataProvider
    public Object[][] getData() throws IOException {
        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//tmrs//data//PurchaseOrder.json");
        return new Object[][] {{data.get(0)}, {data.get(1)}};
    }
}
