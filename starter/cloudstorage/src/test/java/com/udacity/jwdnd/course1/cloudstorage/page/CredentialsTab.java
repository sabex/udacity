package com.udacity.jwdnd.course1.cloudstorage.page;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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
//    addCredential.click(); causes ElementNotInteractable, using JSE as workaround
    JavascriptExecutor js= (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", addCredential);
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
//    saveCredential.click();    // this does not work
    JavascriptExecutor js= (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", saveCredential);
    try {Thread.sleep(5000);}catch (Exception e) {};
  }

  public void addDummyCredentials() {
    log.error("enabled" + credentialUrl.isEnabled());
    log.error(credentialUrl.getAttribute("id"));
    try {Thread.sleep(5000);}catch (Exception e) {};
    // org.openqa.selenium.ElementNotInteractableException: element not interactable - unless i sleep first
    credentialUrl.sendKeys("http://localhost:8080/chat");
    credentialUsername.sendKeys("chatbot");
    credentialPassword.sendKeys("chatPassword");
  }
}
