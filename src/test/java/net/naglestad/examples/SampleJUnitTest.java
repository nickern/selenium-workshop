package net.naglestad.examples;

import net.naglestad.TestBase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class SampleJUnitTest extends TestBase {

    private String testBaseUrl = "http://www.vinmonopolet.no/";

    @Test
    public void testGetProductAlmande() {
        driver.get(testBaseUrl);
        WebElement searchFeild = driver.findElement(By.cssSelector("div.site-search input"));
        searchFeild.sendKeys("Almande");
        searchFeild.submit();

        // search results must be found - throws exception if missing
        driver.findElement(By.cssSelector("#search-results h1"));

        List<WebElement> products = driver.findElements(By.cssSelector("h2.product-item__name a"));
        Map<String, String> productLinks = new HashMap<>();
        for (WebElement productElement : products) {
            String linkText = productElement.getText();
            String linkUrl = productElement.getAttribute("href");
            System.out.println(linkText + ": " + linkUrl);
            productLinks.put(linkText, linkUrl);
        }

        assertTrue(productLinks.containsKey("Baileys Almande"));

        // dontCloseBrowerForDebug();
    }
}
