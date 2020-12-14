package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialsTab {

  public static final String PAGE_LOAD_MARKER = "uploadButton";

  @FindBy(id = "nav-credentials")
  private WebElement navCredentials;

  @FindBy(id = "addCredential")
  private WebElement addCredential;

  @FindBy(id="credentialSubmit")
  private WebElement credentialSubmit;

  public CredentialsTab(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void addCredential() {
    addCredential.click();
  }

  // utility to check if page loaded, existence of credential table is used to check
  public boolean pageLoaded() {
      return( navCredentials.isDisplayed());
  }

  // utility to check if modal loaded, existence of submit button is used to check
  public boolean addCredModalLoaded() {
    return (credentialSubmit.isEnabled());
  }
}
