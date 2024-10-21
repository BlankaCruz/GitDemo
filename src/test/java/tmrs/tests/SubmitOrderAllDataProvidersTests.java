package tmrs.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebElement;

import org.testng.Assert;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tmrs.TestComponents.BaseTest;
import tmrs.pageobjects.*;

    public class SubmitOrderAllDataProvidersTests extends BaseTest {

        @Test(dataProvider = "getData")
        public void submitOrder(String uname, String pwd, String productName) throws IOException, InterruptedException {

            ProductCatalogue productCatalogue = landingPage.loginApplication(uname, pwd);
            List<WebElement> products = productCatalogue.getProductList();
            productCatalogue.addProductToCart(productName);
            CartPage cartPage = productCatalogue.goToCartPage();

            Boolean match = cartPage.VerifyProductDisplay(productName);
            Assert.assertTrue(match);
            CheckoutPage checkoutPage = cartPage.goToCheckout();
            checkoutPage.selectCountry("india");
            ConfirmationPage confirmationPage = checkoutPage.submitOrder();
            String confirmMessage = confirmationPage.getConfirmationMessage();
            Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        }

        @Test(dataProvider = "getData2")
        public void submitOrder2(HashMap<String, String> input) throws IOException, InterruptedException {

            ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
            List<WebElement> products = productCatalogue.getProductList();
            productCatalogue.addProductToCart(input.get("productName"));
            CartPage cartPage = productCatalogue.goToCartPage();

            Boolean match = cartPage.VerifyProductDisplay(input.get("productName"));
            Assert.assertTrue(match);
            CheckoutPage checkoutPage = cartPage.goToCheckout();
            checkoutPage.selectCountry("india");
            ConfirmationPage confirmationPage = checkoutPage.submitOrder();
            String confirmMessage = confirmationPage.getConfirmationMessage();
            Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        }

        @Test(dataProvider = "getData3")
        public void submitOrder3(HashMap<String, String> input) throws IOException, InterruptedException {

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

        @DataProvider
        public Object[][] getData() {
            return new Object[][]{{"blanka.cruz@gmail.com", "Iambeloved@000", "IPHONE 13 PRO"}, {"bcruz@gmail.com", "Iamqueen@000", "ZARA COAT 3"}};
        }

        @DataProvider
        public Object[][] getData2() {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("email", "blanka.cruz@gmail.com");
            map.put("password", "Iambeloved@000");
            map.put("productName", "IPHONE 13 PRO");

            HashMap<String, String> map2 = new HashMap<String, String>();
            map2.put("email", "bcruz@gmail.com");
            map2.put("password", "Iamqueen@000");
            map2.put("productName", "ZARA COAT 3");

            return new Object[][]{{map}, {map2}};
        }

        @DataProvider
        public Object[][] getData3() throws IOException {
            List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//tmrs//data//PurchaseOrder.json");
            return new Object[][]{{data.get(0)}, {data.get(1)}};
        }
    }
