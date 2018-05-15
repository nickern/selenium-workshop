package net.naglestad.examples.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends Page {

    private String url = "http://www.vinmonopolet.no/";

    @FindBy(how = How.CSS, using = "div.site-search input")
    @CacheLookup
    public WebElement searchField;


    public SearchPage(WebDriver driver) {
        super(driver);
    }

    public void load() {
        driver.get(url);
    }

    public static final class Product {
        public String name;
        public String url;

        public Product(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public List<Product> search(String query) {
        WebElement searchResults = getSearchResults(query);

        List<WebElement> productElements = searchResults.findElements(By.cssSelector("h2.product-item__name a"));

        List<Product> products = new ArrayList<>();
        productElements.forEach(e -> products.add(new Product(e.getText(), e.getAttribute("href"))));
        return products;
    }

    private WebElement getSearchResults(String query) {
        searchField.sendKeys(query);
        searchField.submit();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#search-results")));

        // search results must be found - throws exception if missing
        return driver.findElement(By.cssSelector("#search-results"));
    }
}
