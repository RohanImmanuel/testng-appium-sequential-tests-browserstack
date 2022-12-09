package com.browserstack.run_sequential_tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.MobileBy;
import io.appium.java_client.ios.IOSElement;


public class FirstTest extends BrowserStackTestNGTest {

  @Test
  public void test() throws Exception {
    System.out.println("1st Test");
    jse.executeScript("browserstack_executor: {\"action\": \"annotate\", \"arguments\": {\"data\": \"First Test\", \"level\": \"info\"}}");
  }
}
