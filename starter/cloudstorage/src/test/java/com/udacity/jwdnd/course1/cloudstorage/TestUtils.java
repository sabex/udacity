package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/** various utilities to allow re-use across multi step tests */
public class TestUtils {

  public static final String GOOD_PASSWORD = "password";
  public static final String FIRST_NAME = "Favorite";
  public static final String LAST_NAME = "User";

  public static SignupPage getSignupPage(WebDriver driver, String appUrl) {
    driver.get(appUrl + SignupPage.SIGNUP_PATH);
    return new SignupPage(driver);
  }

  public static LoginPage getLoginPage(WebDriver driver, String appUrl) {
    driver.get(appUrl + LoginPage.LOGIN_PATH);
    return new LoginPage(driver);
  }

  public static HomePage getHomePage(WebDriver driver, String appUrl) {
    driver.get(appUrl + HomePage.HOME_PATH);
    return new HomePage(driver);
  }

  public static void verifySignupPageLoads(SignupPage page) {
    assertTrue(page.pageLoaded());
  }

  public static void verifyLoginPageLoads(LoginPage page) {
    assertTrue(page.pageLoaded());
  }

  public static void waitForPageLoad(WebDriver driver, Integer timeout, String pageLoadMarker) {
    WebDriverWait wait = new WebDriverWait(driver, timeout);
    WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id(pageLoadMarker)));
  }

  public static void verifyHomePageLoads(HomePage page) {
    assertTrue(page.pageLoaded());
  }

  public static void registerUser(WebDriver driver, String appUrl, String userName) {
    SignupPage signupPage = getSignupPage(driver, appUrl);
    verifySignupPageLoads(signupPage);
    signupPage.signup(FIRST_NAME, LAST_NAME, userName, GOOD_PASSWORD);
  }

  public static void loginUser(WebDriver driver, String appUrl, String userName) {
    LoginPage loginPage = getLoginPage(driver, appUrl);
    loginPage.login(userName, GOOD_PASSWORD);
  }

  public static void pause(Integer millis) {
    try {
      Thread.sleep(millis);
    } catch (Exception e) {
    }
    ;
  }

  public static NotesTab setupNoteForUser(WebDriver driver) {
    // go to Notes tab
    HomePage homePage = new HomePage(driver);
    homePage.chooseNotesTab(driver);
    //
    NotesTab notesTab = new NotesTab(driver);
    assertTrue(notesTab.pageLoaded());
    // add modal
    notesTab.addNoteModal(driver);
    // reload page from driver
    notesTab = new NotesTab(driver);
    assertTrue(notesTab.addNoteModalLoaded());
    // add values
    notesTab.addDummyNote(driver);
    // submit  modal
    notesTab.saveNote(driver);
    // redirects to home so need to reselect the Notes tab
    homePage = new HomePage(driver);
    homePage.chooseNotesTab(driver);
    // assert changes show in page
    notesTab = new NotesTab(driver);
    assertTrue(notesTab.isNoteInPage(NotesTab.DUMMY_NOTE_TITLE, NotesTab.DUMMY_NOTE_DESC));
    return notesTab;
  }

  public static CredentialsTab setupCredentialForUser(WebDriver driver) {
    HomePage homePage = new HomePage(driver);
    homePage.chooseCredentialsTab(driver);
    //
    CredentialsTab credentialsTab = new CredentialsTab(driver);
    assertTrue(credentialsTab.pageLoaded());
    // add modal
    credentialsTab.addCredentialModal(driver);
    // reload page from driver
    credentialsTab = new CredentialsTab(driver);
    assertTrue(credentialsTab.addCredModalLoaded());
    // add values
    credentialsTab.addDummyCredentials(driver);
    // submit  modal
    credentialsTab.saveCredential(driver);
    // redirects to home so need to reselect the credentials tab
    homePage = new HomePage(driver);
    homePage.chooseCredentialsTab(driver);
    // assert changes show in page
    credentialsTab = new CredentialsTab(driver);
    assertTrue(
        credentialsTab.isCredentialInPage(CredentialsTab.DUMMY_URL, CredentialsTab.DUMMY_USERNAME));
    // assert password encrypted
    assertTrue(credentialsTab.isPasswordEncrypted());
    return credentialsTab;
  }

  public static FilesTab setupFileForUser(WebDriver driver) {
    HomePage homePage = new HomePage(driver);
    homePage.chooseFilesTab(driver);
    //
    FilesTab filesTab = new FilesTab(driver);
    assertTrue(filesTab.pageLoaded());
    // upload file
    filesTab.upload();
    // redirects to home so need to reselect the Files tab
    homePage = new HomePage(driver);
    homePage.chooseFilesTab(driver);
    // assert changes show in page
    filesTab = new FilesTab(driver);
    assertTrue(filesTab.isFileUploaded());
    return filesTab;
  }
}
