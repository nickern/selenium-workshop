package net.naglestad.examples.browserstack;

import com.browserstack.local.Local;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileReader;
import java.net.URL;
import java.util.*;

/**
 * Forked from: https://github.com/browserstack/junit-browserstack
 */

@RunWith(Parallelized.class)
public class BrowserStackJUnitTest {
    public WebDriver driver;
    private Local local;

    private static JSONObject config;

    @Parameterized.Parameter(value = 0)
    public int taskID;

    @Parameterized.Parameters
    public static Iterable<? extends Object> data() throws Exception {
        JSONParser parser = new JSONParser();
        config = (JSONObject) parser.parse(new FileReader("src/test/resources/browserstack-config.json"));
        int envs = ((JSONArray) config.get("environments")).size();

        List<Integer> taskIDs = new ArrayList<>();
        for (int i = 0; i < envs; i++) {
            taskIDs.add(i);
        }

        return taskIDs;
    }

    @Before
    public void setUp() throws Exception {
        JSONArray envs = (JSONArray) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(taskID);
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("key");
        }

        if (capabilities.getCapability("browserstack.local") != null && capabilities.getCapability("browserstack.local") == "true") {
            local = new Local();
            Map<String, String> options = new HashMap<>();
            options.put("key", accessKey);
            options.put("localIdentifier", "Runner-" + taskID);
            local.start(options);
        }

        driver = new RemoteWebDriver(new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities);
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) driver.quit();
        if (local != null) local.stop();
    }
}