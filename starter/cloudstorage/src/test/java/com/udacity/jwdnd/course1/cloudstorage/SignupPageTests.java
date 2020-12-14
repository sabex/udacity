package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SignupPageTests {

  @LocalServerPort private int port;

  private WebDriver driver;

  private String appUrl;

  private static final String GOOD_USER_NAME = "goodUser";
  private static final String BAD_USER_NAME = "badUser";

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
  public void unauthorizedUserCanAccessSignup() {
    SignupPage signupPage = TestUtils.getSignupPage(driver, appUrl);
    TestUtils.verifySignupPageLoads(signupPage);
  }

  @Test
  public void canGoBackToLogin() {
    SignupPage signupPage = TestUtils.getSignupPage(driver, appUrl);
    signupPage.backToLogin();
    assertTrue(driver.getCurrentUrl().contains(LoginPage.LOGIN_PATH));
  }

  @Test
  public void signupSuccessful() {
    TestUtils.registerUser(driver,appUrl, GOOD_USER_NAME);
    verifySignup(true);
  }

  @Test
  public void signupFailsIfUsernameTaken() {
    TestUtils.registerUser(driver,appUrl, BAD_USER_NAME);
    verifySignup(true);
    // second attempt with same info fails
    TestUtils.registerUser(driver,appUrl, BAD_USER_NAME);
    verifySignup(false);
  }

  // signup page utility methods
  private void verifySignup(boolean successExpected) {
    SignupPage page = new SignupPage(driver);
    if (successExpected) {
      assertTrue(page.signupSuccessful());
      assertTrue(page.hasLinkToLogin());
      } else{
      assertTrue(page.signupFailed());
    }
  }
}
