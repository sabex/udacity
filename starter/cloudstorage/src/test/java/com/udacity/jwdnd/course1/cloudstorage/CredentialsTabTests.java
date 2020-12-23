package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.CredentialsTab;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.udacity.jwdnd.course1.cloudstorage.TestUtils.setupCredentialForUser;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialsTabTests {

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
  public void userCanAddAndRemoveCredential() {
    String addCredUser = "addCredUser";
    TestUtils.registerUser(driver, appUrl, addCredUser);
    TestUtils.loginUser(driver,appUrl,addCredUser);
    // go to Credentials tab
    CredentialsTab credentialsTab = setupCredentialForUser(driver);
    // assert success message
    credentialsTab.deleteCredential(driver);
    // redirects to home so need to reselect the credentials tab
    HomePage homePage = new HomePage(driver);
    homePage.chooseCredentialsTab(driver);
    // assert changes show in page
    credentialsTab = new CredentialsTab(driver);
    assertFalse(credentialsTab.isCredentialInPage(CredentialsTab.DUMMY_URL, CredentialsTab.DUMMY_USERNAME));
  }


  @Test
  public void userCanEditCredential() {
    /**
     * Write a Selenium test that logs in an existing user with existing credentials, clicks the edit note
     * button on an existing credential, changes the credential data, saves the changes, and verifies that the
     * changes appear in the credentials list.
     */
    String addCredUser = "editCredUser";
    TestUtils.registerUser(driver, appUrl, addCredUser);
    TestUtils.loginUser(driver,appUrl,addCredUser);
    // go to Credentials tab
    CredentialsTab credentialsTab = setupCredentialForUser(driver);
    // edit modal
    credentialsTab.editCredentialModal(driver);
    // reload page from driver
    credentialsTab = new CredentialsTab(driver);
    assertTrue(credentialsTab.addCredModalLoaded());
    // assert password not encrypted
    credentialsTab = new CredentialsTab(driver);
    assertFalse(credentialsTab.isPasswordEncryptedInModal());
    // add values
    credentialsTab.editDummyCredentials(driver);
    // submit
    credentialsTab.saveCredential(driver);
    // assert changes show in page
    HomePage homePage = new HomePage(driver);
    homePage.chooseCredentialsTab(driver);
    credentialsTab = new CredentialsTab(driver);
    assertTrue(credentialsTab.isCredentialInPage(CredentialsTab.EDITED_URL, CredentialsTab.EDITED_USERNAME));
  }

}
