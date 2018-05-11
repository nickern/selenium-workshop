package net.naglestad.examples.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ProductPage extends Page {

    @FindBy(how = How.CSS, using = "div.product__hgroup h1")
    @CacheLookup
    private WebElement name;

    @FindBy(how = How.CSS, using = "div.product__hgroup p")
    @CacheLookup
    private WebElement description;

    @FindBy(how = How.CLASS_NAME, using = "product__price")
    @CacheLookup
    private WebElement price;


    public String getName() {
        return name.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public String getPrice() {
        return price.getText();
    }

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void load(String url) {
        driver.get(url);
    }


}
