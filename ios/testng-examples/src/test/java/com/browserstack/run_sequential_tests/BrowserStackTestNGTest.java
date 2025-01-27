package com.browserstack.run_sequential_tests;
import com.browserstack.local.Local;

import java.net.URL;
import java.util.Map;
import java.util.Iterator;
import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;

import org.openqa.selenium.remote.DesiredCapabilities;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.io.*;


public class BrowserStackTestNGTest {
  private static IOSDriver<IOSElement> driver;
  public static JavascriptExecutor jse;

  @BeforeSuite
  public void setUp() throws Exception {
    System.out.println("Setting Up Driver");
    JSONParser parser = new JSONParser();
    JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/com/browserstack/run_sequential_tests/sequential.conf.json"));
    JSONArray envs = (JSONArray) config.get("environments");

    DesiredCapabilities capabilities = new DesiredCapabilities();

    Map<String, String> envCapabilities = (Map<String, String>) envs.get(0);
    Iterator it = envCapabilities.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry)it.next();
      capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
    }

    Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
    it = commonCapabilities.entrySet().iterator();
    while (it.hasNext()) {
      Map.Entry pair = (Map.Entry)it.next();
      if(capabilities.getCapability(pair.getKey().toString()) == null){
          capabilities.setCapability(pair.getKey().toString(), pair.getValue());
      }
    }

    String username = System.getenv("BROWSERSTACK_USERNAME");
    if(username == null) {
      username = (String) config.get("username");
    }

    String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
    if(accessKey == null) {
      accessKey = (String) config.get("access_key");
    }

    String app = System.getenv("BROWSERSTACK_APP_ID");
    if(app != null && !app.isEmpty()) {
      capabilities.setCapability("app", app);
    }

    driver = new IOSDriver<IOSElement>(new URL("http://"+username+":"+accessKey+"@"+config.get("server")+"/wd/hub"), capabilities);
    jse = (JavascriptExecutor)driver;
  }

  @AfterSuite
  public void tearDown() throws Exception {
    System.out.println("Driver Quit");
    // Invoke driver.quit() to indicate that the test is completed.
    // Otherwise, it will appear as timed out on BrowserStack.
    driver.quit();
  }
}
