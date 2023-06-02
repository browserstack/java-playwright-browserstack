package tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import runners.PlaywrightTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTest {

    @PlaywrightTest
    void sampleTest(Browser browser) {
        Page page = browser.newPage();
        try {
            page.navigate("https://bstackdemo.com/");
            String product_name = page.locator("//*[@id='1']/p").textContent();
            page.locator("//*[@id='1']/div[4]").click();
            page.locator(".float\\-cart__content");
            String product_in_cart = page.locator("//*[@id='__next']/div/div/div[2]/div[2]/div[2]/div/div[3]/p[1]")
                    .textContent();
            assertEquals(product_in_cart, product_name);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        browser.close();
    }
}
