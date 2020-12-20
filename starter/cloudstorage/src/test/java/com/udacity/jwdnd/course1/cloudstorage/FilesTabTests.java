package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.CredentialsTab;
import com.udacity.jwdnd.course1.cloudstorage.page.FilesTab;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilesTabTests {

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
  public void userCanAddAndDeleteFile() {
    String addFileUser = "addFileUser";
    TestUtils.registerUser(driver, appUrl, addFileUser);
    TestUtils.loginUser(driver,appUrl,addFileUser);
    // go to Credentials tab
    HomePage homePage = new HomePage(driver);
    // pause so i can see flow
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
    // try upload same file again to show duplicate check
    filesTab.upload();
    // redirect so need to reselect tab
    homePage = new HomePage(driver);
    homePage.chooseFilesTab(driver);
    filesTab = new FilesTab(driver);
    assertTrue(filesTab.hasErrorMessage());
    // verify can delete
    filesTab.deleteFile(driver);
// redirect so need to reselect tab
    homePage = TestUtils.getHomePage(driver,appUrl);
    homePage.chooseFilesTab(driver);
    filesTab = new FilesTab(driver);
    assertFalse(filesTab.isFileUploaded());
  }

  @Test
  public void userCanDownloadFile() {
    assertTrue(false);
    // assert success message
  }
}
