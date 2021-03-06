package com.udacity.jwdnd.course1.cloudstorage.page;

import lombok.SneakyThrows;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

  public static final String HOME_PATH = "/home";
  public static final String PAGE_LOAD_MARKER = "logoutButton";

  @FindBy(id = "logoutButton")
  private WebElement logoutButton;

  @FindBy(id = "nav-files-tab")
  private WebElement filesTab;

  @FindBy(id = "nav-notes-tab")
  private WebElement notesTab;

  @FindBy(id = "nav-credentials-tab")
  private WebElement credentialsTab;

  public HomePage(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void logout() {
    logoutButton.submit();
  }

  // utility to check if page loaded, existence of logout button is used to check
  public boolean pageLoaded() {
    return (logoutButton.getText() != null);
  }

  public void chooseFilesTab(WebDriver driver) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", filesTab);
  }

  public void chooseNotesTab(WebDriver driver) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", notesTab);
  }

  @SneakyThrows
  public void chooseCredentialsTab(WebDriver driver) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", credentialsTab);
  }

  public boolean hasFilesTab() {
    return (filesTab.getText().contains("Files"));
  }

  public boolean hasNotesTab() {
    return (notesTab.getText().contains("Notes"));
  }

  public boolean hasCredentialsTab() {
    return (credentialsTab.getText().contains("Credentials"));
  }
}
