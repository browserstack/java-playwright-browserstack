package runners;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.yaml.snakeyaml.Yaml;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONObject;
import java.util.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

public class BstackRunner {
    public static String userName, accessKey;
    public static Map<String, Object> browserStackYamlMap;
    public static final String USER_DIR = "user.dir";
    
    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    public Page page;

    public BstackRunner() {
        File file = new File(getUserDir() + "/browserstack.yml");
        browserStackYamlMap = convertYamlFileToMap(file, new HashMap<>());
    }

    @BeforeEach
    void launchBrowser() {
        playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        String caps = null;
        userName = System.getenv("BROWSERSTACK_USERNAME") != null ? System.getenv("BROWSERSTACK_USERNAME") : (String) browserStackYamlMap.get("userName");
        accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY") != null ? System.getenv("BROWSERSTACK_ACCESS_KEY") : (String) browserStackYamlMap.get("accessKey");

        HashMap<String, String> capabilitiesObject = new HashMap<>();
        capabilitiesObject.put("browserstack.user", userName);
        capabilitiesObject.put("browserstack.key", accessKey);
        capabilitiesObject.put("browserstack.source", "java-playwright-browserstack:sample-sdk:v1.0");
        capabilitiesObject.put("browser", "chrome");

        JSONObject jsonCaps = new JSONObject(capabilitiesObject);
        try {
            caps = URLEncoder.encode(jsonCaps.toString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String wsEndpoint = "wss://cdp.browserstack.com/playwright?caps=" + caps;
        browser = browserType.connect(wsEndpoint);
        page = browser.newPage();
    }

    @AfterEach
    void closeContext() {
        page.close();
        browser.close();
    }

    private String getUserDir() {
        return System.getProperty(USER_DIR);
    }

    private Map<String, Object> convertYamlFileToMap(File yamlFile, Map<String, Object> map) {
        try {
            InputStream inputStream = Files.newInputStream(yamlFile.toPath());
            Yaml yaml = new Yaml();
            Map<String, Object> config = yaml.load(inputStream);
            map.putAll(config);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Malformed browserstack.yml file - %s.", e));
        }
        return map;
    }
}
