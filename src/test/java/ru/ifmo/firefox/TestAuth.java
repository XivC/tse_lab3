package ru.ifmo.firefox;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestAuth {


  WebDriver driver;
  @FindBy(xpath = "/html/body/div[2]/div[1]/div[2]/main/div/div/main/div[1]/div/div[1]/button")
  WebElement manButton;

  @FindBy(xpath = "/html/body/div[2]/div[1]/div[2]/main/div/div/main/div[1]/div/div[2]/button")
  WebElement womanButton;

  @BeforeEach
  public void setUp() {
    driver = Runner.forChrome();
    int a = 1;
  }

  @AfterEach
  public void exit() {
    driver.close();
    driver.quit();
  }

  @Test
  public void testManButtonText() {

    driver.get("https://mamba.ru/");
    PageFactory.initElements(driver, this);
    String text = manButton.getText();
    assert Objects.equals(text, "Мужчина");
  }

  @Test
  public void testWomanButtonText() {
    driver.get("https://mamba.ru/");
    PageFactory.initElements(driver, this);
    String text = womanButton.getText();
    assert Objects.equals(text, "Женщина");
  }



}
