package tmrs.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import tmrs.TestComponents.BaseTest;
import tmrs.TestComponents.Retry;
import tmrs.pageobjects.CartPage;
import tmrs.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

    @Test(groups = {"ErrorHandling"})//,retryAnalyzer=Retry.class)
    public void LoginErrorValidation() throws IOException, InterruptedException {
        ExtentReports extent;
        // ExtentReport, ExtentSparkReporter
//        String path = System.getProperty("user.dir") + "\\src\\test\\java\\tmrs\\reports\\initialDemo.html";
        String path = System.getProperty("user.dir") +  "\\reports\\index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Blanka Cruz");

        landingPage.loginApplication("blanka.cruz@gmail.com", "Iamloved@000");
        Assert.assertEquals("Incorrect email password.", landingPage.getErrorMessage());
    }

    @Test
    public void ProductErrorValidation() throws IOException, InterruptedException {
        String productName = "IPHONE 13 PRO";
        ProductCatalogue productCatalogue = landingPage.loginApplication("bcruz@gmail.com", "Iamqueen@000");
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage = productCatalogue.goToCartPage();
        Boolean match = cartPage.VerifyProductDisplay("IPHONE 133 PRO");
        Assert.assertFalse(match);
    }
}
