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
    credentialsTab.addCredential(driver);
    // redirects to home so need to reselect the credentials tab
    homePage = new HomePage(driver);
    homePage.chooseCredentialsTab(driver);
    // assert changes show in page
    credentialsTab = new CredentialsTab(driver);
    assertTrue(credentialsTab.isCredentialInPage());
    // assert password encrypted
    assertTrue(credentialsTab.isPasswordEncrypted());
    // assert success message
    credentialsTab.deleteCredential(driver);
    // redirects to home so need to reselect the credentials tab
    homePage = new HomePage(driver);
    homePage.chooseCredentialsTab(driver);
    // assert changes show in page
    credentialsTab = new CredentialsTab(driver);
    assertFalse(credentialsTab.isCredentialInPage());
  }

  @Test
  public void userCanEditCredential() {
    assertTrue(false);
    // assert changes show in page
    // assert password not encrypted
    // assert success message
  }

}
