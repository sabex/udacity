package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.FilesTab;
import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.udacity.jwdnd.course1.cloudstorage.TestUtils.setupFileForUser;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
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
    // disable download prompts
    ChromeOptions options = new ChromeOptions();
    Map<String, Object> prefs = new HashMap();
    prefs.put("download.prompt_for_download", false);
    prefs.put("download.default_directory", System.getProperty("user.dir"));
    options.setExperimentalOption("prefs", prefs);
    this.driver = new ChromeDriver(options);
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
    TestUtils.loginUser(driver, appUrl, addFileUser);
    // go to Files tab
    FilesTab filesTab = setupFileForUser(driver);
    // try upload same file again to show duplicate check
    filesTab.upload();
    // redirect so need to reselect tab
    HomePage homePage = new HomePage(driver);
    homePage.chooseFilesTab(driver);
    filesTab = new FilesTab(driver);
    assertTrue(filesTab.hasErrorMessage());
    // verify can delete
    filesTab.deleteFile(driver);
    // redirect so need to reselect tab
    homePage = TestUtils.getHomePage(driver, appUrl);
    homePage.chooseFilesTab(driver);
    filesTab = new FilesTab(driver);
    assertFalse(filesTab.isFileUploaded());
  }

  @Test
  public void userCanDownloadFile() {
    String addFileUser = "downloadFileUser";
    TestUtils.registerUser(driver, appUrl, addFileUser);
    TestUtils.loginUser(driver, appUrl, addFileUser);
    // go to Files tab
    FilesTab filesTab = setupFileForUser(driver);
    // download
    filesTab.downloadFile(driver);
    // see if file found
    File folder = new File(System.getProperty("user.dir"));
    // List the files on that folder
    File[] listOfFiles = folder.listFiles();
    Optional<File> result =
        Arrays.stream(listOfFiles)
            .filter(file -> file.getName().equalsIgnoreCase("dummy.md"))
            .findFirst();
    assertTrue(result.isPresent());
    File file = result.get();
    file.deleteOnExit();
  }
}
