package tmrs.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

        public static class StandAloneTest {

            public static void main(String[] args) {

        //		String productName = "ZARA COAT 3";
                String productName = "IPHONE 13 PRO";
                System.setProperty("webdriver.chrome.driver", "/Users/blanka/Documents/chromedriver.exe");

                WebDriver driver = new ChromeDriver();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                driver.manage().window().maximize();
                driver.get("https://rahulshettyacademy.com/client");
                LandingPage landingPage = new LandingPage(driver);
                driver.findElement(By.id("userEmail")).sendKeys("blanka.cruz@gmail.com");
                driver.findElement(By.id("userPassword")).sendKeys("Iambeloved@000");
                driver.findElement(By.id("login")).click();
                WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
                    List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));

            WebElement prod =	products.stream().filter(product->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
                prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();


                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
                //ng-animating
                wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
                driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

                List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
                Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
                Assert.assertTrue(match);
                driver.findElement(By.cssSelector(".totalRow button")).click();

                Actions a = new Actions(driver);
                a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));

        //		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
                driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
                driver.findElement(By.cssSelector(".action__submit")).click();

                String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
                Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
                System.out.println(confirmMessage);
                driver.close();
            }
        }
    }
