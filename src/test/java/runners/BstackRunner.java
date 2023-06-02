package runners;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.extension.*;

import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Stream;

public class BstackRunner implements TestTemplateInvocationContextProvider {
    public Browser browser;
    public String wss;
    private JSONObject mainConfig;
    private JSONArray platformConfig;

    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext extensionContext) {
        List<TestTemplateInvocationContext> desiredCapsInvocationContexts = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            mainConfig = (JSONObject) parser
                        .parse(new FileReader("src/test/resources/conf/" + System.getProperty("config")));
            platformConfig = (JSONArray) mainConfig.get("environments");
            wss = (String) mainConfig.get("wss");

            for (int i = 0; i < platformConfig.size(); i++) {
                JSONObject platform = (JSONObject) platformConfig.get(i);
                desiredCapsInvocationContexts.add(invocationContext(platform));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desiredCapsInvocationContexts.stream();
    }

    private TestTemplateInvocationContext invocationContext(JSONObject capabilitiesObject) {
        return new TestTemplateInvocationContext() {

            @Override
            public List<Extension> getAdditionalExtensions() {

                return Collections.singletonList(new ParameterResolver() {
                    @Override
                    public boolean supportsParameter(ParameterContext parameterContext,
                                                     ExtensionContext extensionContext) {
                        return parameterContext.getParameter().getType().equals(Browser.class);
                    }

                    @Override
                    public Object resolveParameter(ParameterContext parameterContext,
                                                   ExtensionContext extensionContext) {
                        Playwright playwright = Playwright.create();
                        BrowserType browserType = playwright.chromium();
                        String caps = null;
                        try {
                            caps = URLEncoder.encode(capabilitiesObject.toString(), "utf-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        String ws_endpoint = wss + "?caps=" + caps;
                        browser = browserType.connect(ws_endpoint);
                        return browser;
                    }
                });
            }
        };
    }
}
