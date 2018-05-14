package net.naglestad.examples.browserstack;

import net.naglestad.examples.pages.SearchPage;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class SampleBrowserStackJUnitTest extends BrowserStackJUnitTest {

    private SearchPage searchPage;

    @Before
    public void initPageObjects() {
        searchPage = PageFactory.initElements(driver, SearchPage.class);
    }

    @Test
    public void testSearchAlmande() {
        searchPage.load();
        List<SearchPage.Product> searchResults = searchPage.search("Almande");
        boolean foundExpectedProduct = searchResults.stream().anyMatch(product -> product.name.equals("Baileys Almande"));
        assertTrue(foundExpectedProduct);
    }
}
