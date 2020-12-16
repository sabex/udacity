package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotesTabTests {

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
  public void userCanAddNote() {
//    driver.findElement(By.id("nav-notes-tab")).click();
//    TestUtils.pause(2000);
//    WebDriverWait wait = new WebDriverWait(driver, 20);
//    wait.until(ExpectedConditions.elementToBeClickable(By.id("addNoteButton"))).click();
//    wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys("To-Do");
//    wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description")))
//        .sendKeys("Good work done here");
//    TestUtils.pause(2000);
//    wait.until(ExpectedConditions.elementToBeClickable(By.id("notesClick"))).click();
//    driver.navigate().to(appUrl + "/home");
//    driver.findElement(By.id("nav-notes-tab")).click();
//    TestUtils.pause(2000);
    assertTrue(false);
    /**
     * Write a Selenium test that logs in an existing user, creates a note and verifies that the
     * note details are visible in the note list.
     */
    // assert changes show in page
    // assert success message
  }

  @Test
  public void userCanEditNote() {
    assertTrue(false);
    /**
     * Write a Selenium test that logs in an existing user with existing notes, clicks the edit note
     * button on an existing note, changes the note data, saves the changes, and verifies that the
     * changes appear in the note list.
     */
    // assert changes show in page
    // assert success message
  }

  @Test
  public void userCanDeleteNote() {
    assertTrue(false);
    /**
     * Write a Selenium test that logs in an existing user with existing notes, clicks the delete
     * note button on an existing note, and verifies that the note no longer appears in the note
     * list.
     */
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
