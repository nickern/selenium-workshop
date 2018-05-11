package net.naglestad.infra;

import net.naglestad.TestBase;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.service.DriverService;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class DriverServiceFactory {

    /**
     * @param driverName chrome or gecko (firefox)
     * @return
     */
    private static File getDriverExecutablePath(String driverName) {
        if (!driverNameIsKnown(driverName)) {
            throw new IllegalArgumentException("Unsupported driver!");
        }

        URL resource = TestBase.class.getResource("/webdrivers");
        try {
            File webdriverDirectory = Paths.get(resource.toURI()).toFile();
            File executable;

            if (SystemUtils.IS_OS_MAC) {
                executable = createFilePath(webdriverDirectory, driverName + "driver_mac64");
            } else if (SystemUtils.IS_OS_WINDOWS) {
                executable = createFilePath(webdriverDirectory, driverName + "driver.exe");
            } else if (SystemUtils.IS_OS_LINUX) {
                executable = createFilePath(webdriverDirectory, driverName + "driver_linux64");
            } else {
                throw new RuntimeException("Unsupported OS");
            }

            if (!executable.exists()) {
                throw new RuntimeException("Cannot find webdriver binary");
            }
            System.out.println("Using driver executable from " + executable.getAbsolutePath());

            executable.setExecutable(true);

            return executable;

        } catch (URISyntaxException e) {
            throw new RuntimeException("Cannot find webdriver binary", e);
        }

    }

    private static boolean driverNameIsKnown(String driverName) {
        return driverName.equalsIgnoreCase("chrome") || driverName.equalsIgnoreCase("gecko");
    }

    private static File createFilePath(File webdriverDirectory, String binary) {
        return new File(webdriverDirectory.getAbsolutePath(), binary);
    }

    public static DriverService createChromeDriverService() throws IOException {
        return new ChromeDriverService.Builder()
                .usingDriverExecutable(getDriverExecutablePath("chrome"))
                .usingAnyFreePort()
                .build();
    }

    public static DriverService createGeckoDriverService() throws IOException {
        return new GeckoDriverService.Builder()
                .usingDriverExecutable(getDriverExecutablePath("gecko"))
                .usingAnyFreePort()
                .build();
    }

}
