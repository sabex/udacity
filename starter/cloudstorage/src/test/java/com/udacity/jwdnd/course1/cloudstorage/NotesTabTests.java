package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
  public void userCanAddAndDeleteNote() {
    /**
     * Write a Selenium test that logs in an existing user, creates a note and verifies that the
     * note details are visible in the note list.
     */
    String addNoteUser = "addNoteUser";
    TestUtils.registerUser(driver, appUrl, addNoteUser);
    TestUtils.loginUser(driver,appUrl,addNoteUser);
    // go to Notes tab
    HomePage homePage = new HomePage(driver);
    homePage.chooseNotesTab(driver);
    //
    NotesTab notesTab = new NotesTab(driver);
    assertTrue(notesTab.pageLoaded());
    // add modal
    notesTab.addNoteModal(driver);
    // reload page from driver
    notesTab = new NotesTab(driver);
    assertTrue(notesTab.addNoteModalLoaded());
    // add values
    notesTab.addDummyNote(driver);
    // submit  modal
    notesTab.addNote(driver);
    // redirects to home so need to reselect the Notes tab
    homePage = new HomePage(driver);
    homePage.chooseNotesTab(driver);
    // assert changes show in page
    notesTab = new NotesTab(driver);
    assertTrue(notesTab.isNoteInPage());
    // now delete it
    notesTab.deleteNote(driver);
    // verify deleted
    homePage = new HomePage(driver);
    homePage.chooseNotesTab(driver);
    // assert changes show in page
    notesTab = new NotesTab(driver);
    assertFalse(notesTab.isNoteInPage());
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
}
