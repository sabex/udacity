package com.udacity.jwdnd.course1.cloudstorage;

import static com.udacity.jwdnd.course1.cloudstorage.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

  @LocalServerPort private int port;

  private WebDriver driver;

  private String appUrl;

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void beforeEach() {
    this.driver = new ChromeDriver();
    this.appUrl = "http://localhost:" + this.port;
  }

  @AfterEach
  public void afterEach() {
    if (this.driver != null) {
      driver.quit();
    }
  }

  @Test
  public void userCanUseCapabilities() {
    String E2Eusername = "E2Eusername";
    // signup
    TestUtils.registerUser(driver, appUrl, E2Eusername);
    addUserData(E2Eusername);
    // verify logged out
    LoginPage loginPage = new LoginPage(driver);
    assertTrue(loginPage.pageLoaded());

    // verify cannot get to home page
    verifyNeedToBeLoggedOnToAccessHome();

    // user sees their data after logon again
    loginPage = TestUtils.getLoginPage(driver, appUrl);
    loginPage.login(E2Eusername,TestUtils.GOOD_PASSWORD);
    // check files
    HomePage homePage = TestUtils.getHomePage(driver, appUrl);
    homePage.chooseFilesTab(driver);
    FilesTab filesTab = new FilesTab(driver);
    assertTrue(filesTab.isFileUploaded());
    // check notes
    homePage = TestUtils.getHomePage(driver, appUrl);
    homePage.chooseNotesTab(driver);
    NotesTab notesTab = new NotesTab(driver);
    assertTrue(notesTab.pageLoaded());
    assertTrue(notesTab.isNoteInPage(NotesTab.DUMMY_NOTE_TITLE, NotesTab.DUMMY_NOTE_DESC));
    // check credentials
    homePage = TestUtils.getHomePage(driver, appUrl);
    homePage.chooseCredentialsTab(driver);
    CredentialsTab credentialsTab = new CredentialsTab(driver);
    assertTrue(credentialsTab.isCredentialInPage(CredentialsTab.DUMMY_URL, CredentialsTab.DUMMY_USERNAME));
  }

  private void addUserData(String E2Eusername) {
    // login with signed up user
    LoginPage loginPage = TestUtils.getLoginPage(driver, appUrl);
    TestUtils.verifyLoginPageLoads(loginPage);
    loginPage.login(E2Eusername,TestUtils.GOOD_PASSWORD);

    // verify redirect to Home / login successful
    assertTrue(driver.getCurrentUrl().contains(HomePage.HOME_PATH));
    HomePage homePage = new HomePage(driver);

    // user add file (tests to prove can add are in FilesTabTests.java
    FilesTab filesTab = setupFileForUser(driver);
    // add a note
    NotesTab notesTab = setupNoteForUser(driver);
    // user can add a credential
    CredentialsTab credentialsTab = setupCredentialForUser(driver);

    // redirects to home so need to reload page
    homePage = new HomePage(driver);
    // logout
    verifyCanLogout(driver, homePage);
  }

  @Test
  public void userCannotSeeOtherUsersData() {
    String username = "multiusername1";
    // register user, login and add file, cred and note. logout
    TestUtils.registerUser(driver, appUrl, username);
    addUserData(username);
    // register other user, login and verify no file, cred or note listed
    // register second user and login
    TestUtils.registerUser(driver,appUrl, "multiusername2");
    TestUtils.loginUser(driver, appUrl, "multiusername2");
    // expect no files
    HomePage homePage = TestUtils.getHomePage(driver, appUrl);
    homePage.chooseFilesTab(driver);
    FilesTab filesTab = new FilesTab(driver);
    assertFalse(filesTab.isFileUploaded());
    // expect no notes
    homePage = TestUtils.getHomePage(driver, appUrl);
    homePage.chooseNotesTab(driver);
    NotesTab notesTab = new NotesTab(driver);
    assertTrue(notesTab.pageLoaded());
    assertFalse(notesTab.isNoteInPage(NotesTab.DUMMY_NOTE_TITLE, NotesTab.DUMMY_NOTE_DESC));
    // expect no credentials
    homePage = TestUtils.getHomePage(driver, appUrl);
    homePage.chooseCredentialsTab(driver);
    CredentialsTab credentialsTab = new CredentialsTab(driver);
    assertFalse(credentialsTab.isCredentialInPage(CredentialsTab.DUMMY_URL, CredentialsTab.DUMMY_USERNAME));
  }

  // utility methods

  // home page utility methods
  private HomePage getHomePage(WebDriver driver) {
    driver.get(appUrl + HomePage.HOME_PATH);
    return new HomePage(driver);
  }

  private void verifyCanLogout(WebDriver driver, HomePage page) {
    page.logout();
    // show success by verify redirect to login page
    assertTrue(driver.getCurrentUrl().contains(LoginPage.LOGIN_PATH));
  }

  private void verifyNeedToBeLoggedOnToAccessHome() {
    HomePage page = getHomePage(driver);
    // show success by verify redirect to login page
    assertTrue(driver.getCurrentUrl().contains(LoginPage.LOGIN_PATH));
  }
}
