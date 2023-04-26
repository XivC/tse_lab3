package ru.ifmo.firefox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestAuthFirefox {


  WebDriver driver;
  @FindBy(xpath = "/html/body/div[2]/div[1]/div[2]/main/div/div/main/div[1]/div/div[1]/button")
  WebElement manButton;

  @FindBy(xpath = "/html/body/div[2]/div[1]/div[2]/main/div/div/main/div[1]/div/div[2]/button")
  WebElement womanButton;

  @BeforeEach
  public void setUp() {
    driver = Runner.forFirefox();
    int a = 1;
  }

  @AfterEach
  public void exit() {

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

  @ParameterizedTest()
  @ValueSource(strings = {"С мужчинами", "С женщинами", "Со всеми"})
  public void testSelectGender(String text) throws InterruptedException {

    toGenderPage();
    WebElement textBlock = driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main"));
    assert textBlock.getText().contains(text);
  }


  @ParameterizedTest()
  @ValueSource(strings = {"Отношения", "Флирт", "Встреча, свидание"})
  public void testDatingPurpose(String text) throws InterruptedException {
    toGenderPage().toPurposePage();
    WebElement textBlock = driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main"));
    assert textBlock.getText().contains(text);
  }


  @ParameterizedTest()
  @ValueSource(strings = {"до 170 см", "от 170 до 190 см", "выше 190 см", "любого"})
  public void testHeightPage(String text) throws InterruptedException {
    toGenderPage().toPurposePage().toHeightPage();
    WebElement textBlock = driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main"));
    assert textBlock.getText().contains(text);
  }


  @ParameterizedTest()
  @ValueSource(strings = {"до 60 кг", "от 60 до 80 кг", "от 80 до 100 кг", "больше 100 кг", "любого"})
  public void testWeightPage(String text) throws InterruptedException {
    toGenderPage().toPurposePage().toHeightPage().toWeightPage();
    WebElement textBlock = driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main"));
    assert textBlock.getText().contains(text);
  }

  @Test
  public void testInvalidName() throws InterruptedException {
    toGenderPage().toPurposePage().toHeightPage().toWeightPage().toNamePage().toAgePage("");
  }

  @Test
  public void testInvalidAgeLower18() throws InterruptedException{
    toGenderPage().toPurposePage().toHeightPage().toWeightPage().toNamePage().toAgePage("aboba");
    WebElement yearSelectorElement = driver.findElement(
            new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[2]/div/div[5]/label/select")
    );
    WebElement monthSelectorElement = driver.findElement(
            new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[2]/div/div[3]/label/select")
    );
    WebElement daySelectorElement = driver.findElement(
            new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[2]/div/div[1]/label/select")
    );

    Select yearSelector = new Select(yearSelectorElement);
    Select monthSelector = new Select(monthSelectorElement);
    Select daySelector = new Select(daySelectorElement);
    yearSelector.selectByValue("2010");
    monthSelector.selectByValue("1");
    daySelector.selectByValue("1");
    Thread.sleep(400);
    WebElement submitButton = driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[4]/div"));
    submitButton.click();
    Thread.sleep(1000);
    List<WebElement> errors = driver.findElements(new By.ByXPath("/html/body/div[2]/section"));
    assert errors.size() > 0;
    WebElement error = errors.get(0);
    assert error.getText().contains("только с 18 лет");

  }

  @Test
  public void testInvalidAgeGreater80() throws InterruptedException{
    toGenderPage().toPurposePage().toHeightPage().toWeightPage().toNamePage().toAgePage("aboba");
    WebElement yearSelectorElement = driver.findElement(
            new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[2]/div/div[5]/label/select")
    );
    WebElement monthSelectorElement = driver.findElement(
            new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[2]/div/div[3]/label/select")
    );
    WebElement daySelectorElement = driver.findElement(
            new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[2]/div/div[1]/label/select")
    );

    Select yearSelector = new Select(yearSelectorElement);
    Select monthSelector = new Select(monthSelectorElement);
    Select daySelector = new Select(daySelectorElement);
    yearSelector.selectByValue("1850");
    monthSelector.selectByValue("1");
    daySelector.selectByValue("1");
    Thread.sleep(400);
    WebElement submitButton = driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[4]/div"));
    submitButton.click();
    Thread.sleep(1000);
    List<WebElement> errors = driver.findElements(new By.ByXPath("/html/body/div[2]/section"));
    assert errors.size() > 0;
    WebElement error = errors.get(0);
    assert error.getText().contains("до 80 лет");

  }

  @Test
  public void testValidAge() throws InterruptedException{
    toGenderPage().toPurposePage().toHeightPage().toWeightPage().toNamePage().toAgePage("aboba");
    WebElement yearSelectorElement = driver.findElement(
            new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[2]/div/div[5]/label/select")
    );
    WebElement monthSelectorElement = driver.findElement(
            new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[2]/div/div[3]/label/select")
    );
    WebElement daySelectorElement = driver.findElement(
            new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[2]/div/div[1]/label/select")
    );

    Select yearSelector = new Select(yearSelectorElement);
    Select monthSelector = new Select(monthSelectorElement);
    Select daySelector = new Select(daySelectorElement);
    yearSelector.selectByValue("1990");
    monthSelector.selectByValue("1");
    daySelector.selectByValue("1");
    Thread.sleep(400);
    WebElement submitButton = driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[4]/div"));
    submitButton.click();
    Thread.sleep(1000);
    List<WebElement> errors = driver.findElements(new By.ByXPath("/html/body/div[2]/section"));
    assert errors.size() == 0;

  }

  private TestAuthFirefox toGenderPage() throws InterruptedException {
    driver.get("https://www.mamba.ru/ru/");
    PageFactory.initElements(driver, this);
    manButton.click();
    Thread.sleep(2000);
    return this;
  }

  private TestAuthFirefox toPurposePage() throws InterruptedException {
    driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/div[1]/div/button")).click();
    Thread.sleep(2000);
    return this;
  }

  private TestAuthFirefox toHeightPage() throws InterruptedException {
    driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/div[2]/div/button")).click();
    Thread.sleep(2000);
    return this;
  }

  private TestAuthFirefox toWeightPage() throws InterruptedException {
    driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/div[2]/div/button")).click();
    Thread.sleep(2000);
    return this;
  }

  private TestAuthFirefox toNamePage() throws InterruptedException {
    driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/div[2]/div/button")).click();
    Thread.sleep(3000);
    return this;
  }

  private TestAuthFirefox toAgePage(String name) throws InterruptedException{

    WebElement textInput = driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[1]/div/input"));

    textInput.sendKeys(name);
    Thread.sleep(300);
    WebElement button = driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[3]/div/input"));
    button.click();
    Thread.sleep(300);

    boolean wasError = false;
    try {
      driver.findElement(new By.ByXPath("/html/body/div[2]/div[1]/div[2]/main/div/div/main/form/div[1]/div/div/div"));
      wasError = true;
    }
    catch (StaleElementReferenceException | NoSuchElementException ignored){

    }
    assertEquals((name.length() == 0), wasError);

    return this;
  }





}
