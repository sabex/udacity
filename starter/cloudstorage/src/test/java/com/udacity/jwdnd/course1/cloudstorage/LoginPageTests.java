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
class LoginPageTests {

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
  public void unauthorizedUserCanAccessLogin() {
    LoginPage loginPage = TestUtils.getLoginPage(driver, appUrl);
    TestUtils.verifyLoginPageLoads(loginPage);
  }

  @Test
  public void userCanGetToSignupFromLoginPage() {
    LoginPage loginPage = TestUtils.getLoginPage(driver, appUrl);
    TestUtils.verifyLoginPageLoads(loginPage);
    verifyLinkToSignupFromLoginWorks(loginPage);
  }

  @Test
  public void registeredUserCanLogin() {
    String loginPageTestUser = "LoginPageTestUser";
    // signup
    TestUtils.registerUser(driver, appUrl, loginPageTestUser);
    // login
    TestUtils.loginUser(driver, appUrl, loginPageTestUser);
    // successful login redirects to home so test url
    assertTrue(driver.getCurrentUrl().contains(HomePage.HOME_PATH));
  }

  @Test
  public void loginFailsIfNotSignedup() {
    String notRegisteredUser = "notRegisteredUser";
    TestUtils.loginUser(driver, appUrl, notRegisteredUser);
    LoginPage loginPage = new LoginPage(driver);
    assertTrue(loginPage.isLoginFailed());
  }

  private void verifyLinkToSignupFromLoginWorks(LoginPage page) {
    page.signup();
    assertTrue(driver.getCurrentUrl().contains(SignupPage.SIGNUP_PATH));
  }
}
