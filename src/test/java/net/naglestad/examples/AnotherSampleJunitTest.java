package net.naglestad.examples;

import net.naglestad.TestBase;
import net.naglestad.examples.pages.ProductPage;
import net.naglestad.examples.pages.SearchPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AnotherSampleJunitTest extends TestBase {

    private SearchPage searchPage;
    private ProductPage productPage;

    @Before
    public void initPageObjects() {
        searchPage = PageFactory.initElements(driver, SearchPage.class);
        productPage = PageFactory.initElements(driver, ProductPage.class);
    }

    @Test
    public void testSearchAlmande() {

        searchPage.load();
        List<SearchPage.Product> searchResults = searchPage.search("Almande");
        boolean foundExpectedProduct = searchResults.stream().anyMatch(product -> product.name.equals("Baileys Almande"));
        assertTrue(foundExpectedProduct);

//        dontCloseBrowerForDebug();
    }

    @Test
    public void testGetProductInfo() {
        productPage.load("https://www.vinmonopolet.no/vmp/Land/Skottland/BrewDog-Punk-IPA/p/1947102");

        System.out.println(productPage.getName() + " " + productPage.getPrice());
        System.out.println(productPage.getDescription());

//        dontCloseBrowerForDebug();
    }
}
