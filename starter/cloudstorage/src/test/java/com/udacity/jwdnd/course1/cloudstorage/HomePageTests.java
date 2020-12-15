package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HomePageTests {

  @LocalServerPort private int port;

  private WebDriver driver;

  private String appUrl;

  private String E2Eusername = "HomePageUser";
  private String E2Epassword = "password";

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
  public void userCanAccessHomePageWhenLoggedIn() {
    String homePageUser = "homePageUser";
    TestUtils.registerUser(driver, appUrl, homePageUser);
    TestUtils.loginUser(driver, appUrl, homePageUser);
    HomePage homePage = new HomePage(driver);
    TestUtils.verifyHomePageLoads(homePage);
    // verify all tabs are displayed, the render of each tab is tested separately
    verifyUserHasAllTabs(homePage);

    homePage.chooseCredentialsTab(driver);
    homePage = new HomePage(driver);   // hidden tabs now enabled so need to refresh page object
    verifyUserHasAllTabs(homePage);

    homePage.chooseNotesTab();
    homePage = new HomePage(driver);   // hidden tabs now enabled so need to refresh page object
    verifyUserHasAllTabs(homePage);

    homePage.chooseFilesTab();
    homePage = new HomePage(driver);   // hidden tabs now enabled so need to refresh page object
    verifyUserHasAllTabs(homePage);
  }

  @Test
  public void userCanNotAccessHomePageWhenNotLoggedIn() {
    // should auto redirect to login page when not logged in
    TestUtils.getHomePage(driver, appUrl);
    assertTrue(driver.getCurrentUrl().contains(LoginPage.LOGIN_PATH));
    LoginPage loginPage = new LoginPage(driver);
    assertTrue(loginPage.pageLoaded());
  }

  @Test
  public void userCanLogout() {
    String homePageUser4Logout = "homePageUser4Logout";
    TestUtils.registerUser(driver, appUrl, homePageUser4Logout);
    TestUtils.loginUser(driver, appUrl, homePageUser4Logout);
    TestUtils.waitForPageLoad(driver, 2, HomePage.PAGE_LOAD_MARKER);
    HomePage homePage = new HomePage(driver);
    homePage.logout();
    // should be redirected to login
    TestUtils.waitForPageLoad(driver, 2, LoginPage.PAGE_LOAD_MARKER);
    log.error(driver.getCurrentUrl());
    assertTrue(driver.getCurrentUrl().contains(LoginPage.LOGIN_PATH));
    LoginPage loginPage = new LoginPage(driver);
    TestUtils.verifyLoginPageLoads(loginPage);
    // ensure cannot get to home
    TestUtils.getHomePage(driver, appUrl);
    // should be redirecting to login
    TestUtils.waitForPageLoad(driver, 2, LoginPage.PAGE_LOAD_MARKER);
    assertTrue(driver.getCurrentUrl().contains(LoginPage.LOGIN_PATH));
    loginPage = new LoginPage(driver);
    TestUtils.verifyLoginPageLoads(loginPage);
  }

  public void verifyUserHasAllTabs(HomePage page) {
    assertTrue(page.hasFilesTab());
    assertTrue(page.hasCredentialsTab());
    assertTrue(page.hasNotesTab());
  }
}
