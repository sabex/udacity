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

  public static final String DUMMY_URL = "http://localhost:8080/chat";
  public static final String DUMMY_USERNAME = "chatbot";
  public static final String DUMMY_PASSWORD = "chatPassword";
  public static final String EDITED_URL = "http://localhost:8080/credentials";
  public static final String EDITED_USERNAME = "editbot";
  public static final String EDITED_PASSWORD = "editPassword";

  public CredentialsTab(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void addCredentialModal(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(addCredential)).click();
  }

  public void editCredentialModal(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    WebElement webElement = credentialTable.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[1]/button"));
    wait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
  }

  // utility to check if page loaded, existence of credential table is used to check
  public boolean pageLoaded() {
    return( credentialTable.isEnabled());
  }

  // utility to check if modal loaded, existence of submit button is used to check
  public boolean addCredModalLoaded() {
    return (saveCredential.isEnabled());
  }

  public void saveCredential(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(saveCredential)).click();
  }

  public void addDummyCredentials(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).sendKeys(DUMMY_URL);
    wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).sendKeys(DUMMY_USERNAME);
    wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).sendKeys(DUMMY_PASSWORD);
  }
  public void editDummyCredentials(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).clear();
    wait.until(ExpectedConditions.elementToBeClickable(credentialUrl)).sendKeys(EDITED_URL);
    wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).clear();
    wait.until(ExpectedConditions.elementToBeClickable(credentialUsername)).sendKeys(EDITED_USERNAME);
    wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).clear();
    wait.until(ExpectedConditions.elementToBeClickable(credentialPassword)).sendKeys(EDITED_PASSWORD);
  }

  public boolean isPasswordEncryptedInModal() {
    // if password on form matches the password saved, then its not encrypted
    return !(DUMMY_PASSWORD.equalsIgnoreCase(credentialPassword.getAttribute("value")));
  }

  public boolean isCredentialInPage(String url, String username) {
    // test uses a new user so credential will be the first one in the list
    try{
      WebElement credentialUrl =
          credentialTable.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"));
      WebElement credentialUsername =
              credentialTable.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[2]"));
      return (credentialUrl.getAttribute("textContent").equalsIgnoreCase(url)
      && credentialUsername.getAttribute("textContent").equalsIgnoreCase(username));
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
