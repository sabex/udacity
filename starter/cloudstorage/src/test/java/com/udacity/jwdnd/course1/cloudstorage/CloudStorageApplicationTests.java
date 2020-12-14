package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
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

    // login with signed up user
    LoginPage loginPage = TestUtils.getLoginPage(driver, appUrl);
    TestUtils.verifyLoginPageLoads(loginPage);
    loginPage.login(E2Eusername,TestUtils.GOOD_PASSWORD);

    // verify redirect to Home / login successful
    assertTrue(driver.getCurrentUrl().contains(HomePage.HOME_PATH));
    HomePage homePage = new HomePage(driver);

    // user can add a file
    // user can add a note
    // user can a credential

    // logout
    verifyCanLogout(driver, homePage);

    // verify logged out
    loginPage = new LoginPage(driver);
    assertTrue(loginPage.pageLoaded());

    // verify cannot get to home page
    verifyNeedToBeLoggedOnToAccessHome();

    // user sees their data after logon again
    loginPage = TestUtils.getLoginPage(driver, appUrl);
    loginPage.login(E2Eusername,TestUtils.GOOD_PASSWORD);
    // notes
    // files
    // credentials
  }

  @Test
  public void userCannotSeeOtherUsersData() {
    assertTrue(false);
    // register user, login and add file, cred and note. logout
    // register other user, login and verify no file, cred or note listed
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
