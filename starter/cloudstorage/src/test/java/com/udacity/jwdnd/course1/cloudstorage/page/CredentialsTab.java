package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.TestUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Slf4j
public class CredentialsTab {

  public static final String PAGE_LOAD_MARKER = "uploadButton";

  @FindBy(id = "credentialTable")
  private WebElement credentialTable;

  @FindBy(id = "addCredential")
  private WebElement addCredential;

  @FindBy(id="saveCredential")
  private WebElement saveCredential;

  @FindBy(id="credential-url")
  private WebElement credentialUrl;

  @FindBy(id="credential-username")
  private WebElement credentialUsername;

  @FindBy(id="credential-password")
  private WebElement credentialPassword;

  public CredentialsTab(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void addCredentialModal(WebDriver driver) {
//    TestUtils.pause(500);
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(addCredential)).click();
  }

  // utility to check if page loaded, existence of credential table is used to check
  public boolean pageLoaded() {
    return( credentialTable.isEnabled());
  }

  // utility to check if modal loaded, existence of submit button is used to check
  public boolean addCredModalLoaded() {
    return (saveCredential.isEnabled());
  }

  public void addCredential(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(saveCredential)).click();
  }

  public void addDummyCredentials(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).sendKeys("http://localhost:8080/chat");
    wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).sendKeys("chatbot");
    wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).sendKeys("chatPassword");
  }

  public boolean isCredentialInPage() {
    // test uses a new user so credential will be the first one in the list
    try{
      WebElement credential =
          credentialTable.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"));
      return (credential.getAttribute("textContent").equalsIgnoreCase("http://localhost:8080/chat"));
    }
    catch (NoSuchElementException nse) {
      return false;
    }
  }

  public boolean isPasswordEncrypted() {
    // test uses a new user so credential will be the first one in the list
    WebElement credential = credentialTable.findElement(By.xpath("//*[@id='credentialTable']/tbody[1]/tr/td[3]"));
    return !(credential.getAttribute("textContent").equalsIgnoreCase("chatPassword"));
  }

  public void deleteCredential(WebDriver driver) {
    WebElement element = credentialTable.findElement(By.xpath("//*[@id='credentialTable']/tbody[1]/tr/td[1]/a"));
    JavascriptExecutor js= (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", element);
  }
}
