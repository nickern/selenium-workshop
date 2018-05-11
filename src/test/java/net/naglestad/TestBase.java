package net.naglestad;

import net.naglestad.infra.DriverServiceFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.service.DriverService;

import java.util.concurrent.TimeUnit;

public class TestBase {

    protected WebDriver driver;
    protected static DriverService service;

    private final boolean headless = Boolean.valueOf(System.getProperty("selenium.use_headless", "false"));

    @BeforeClass
    public static void setUp() throws Exception {
        service = DriverServiceFactory.createChromeDriverService();
        //service = DriverServiceFactory.createGeckoDriverService();

        service.start();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        service.stop();
    }

    @Before
    public void createChromeDriver() {
        // Add options to Google Chrome. The window-size is important for responsive sites
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            System.out.println("Using headless mode");
            options.addArguments("headless");
        }
        options.addArguments("window-size=1920x1200");

        driver = new RemoteWebDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // wait 5 seconds for page to update
    }

    @After
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void dontCloseBrowerForDebug() {
        while (true) {
            try {
                Thread.sleep(125L);
            } catch (InterruptedException e) {
                // pass
            }
        }
    }
}
