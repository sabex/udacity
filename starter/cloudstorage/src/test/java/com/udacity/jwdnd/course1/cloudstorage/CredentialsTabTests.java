package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.CredentialsTab;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
  public void userCanAddCredential() {
    String addCredUser = "addCredUser";
    TestUtils.registerUser(driver, appUrl, addCredUser);
    TestUtils.loginUser(driver,appUrl,addCredUser);
    // go to Credentials tab
    HomePage homePage = new HomePage(driver);
    homePage.chooseCredentialsTab();
    //
    CredentialsTab credentialsTab = new CredentialsTab(driver);
    // this line fails - assertion error
    assertTrue(credentialsTab.pageLoaded());
    // add modal
    credentialsTab.addCredential();
    // reload page from driver
    driver.switchTo().activeElement();
    credentialsTab = new CredentialsTab(driver);
    assertTrue(credentialsTab.addCredModalLoaded());
    // assert changes show in page
    // assert password encrypted
    // assert success message
  }

  @Test
  public void userCanEditCredential() {
    assertTrue(false);
    // assert changes show in page
    // assert password not encrypted
    // assert success message
  }

  @Test
  public void userCanDeleteCredential() {
    assertTrue(false);
    // assert changes show in page
    // assert success message
  }

  @Test
  public void errorMessageOn() {
    // will need multiple one we know the validations
    assertTrue(false);
    // assert changes show in page
    // assert success message
  }

}
