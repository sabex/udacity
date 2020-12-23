package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.udacity.jwdnd.course1.cloudstorage.TestUtils.setupNoteForUser;
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
    TestUtils.registerUser(driver, appUrl, "addNoteUser");
    TestUtils.loginUser(driver,appUrl, "addNoteUser");
    NotesTab notesTab = setupNoteForUser(driver);
    // now delete it
    notesTab.deleteNote(driver);
    // verify deleted
    HomePage homePage = new HomePage(driver);
    homePage.chooseNotesTab(driver);
    // assert changes show in page
    notesTab = new NotesTab(driver);
    assertFalse(notesTab.isNoteInPage(NotesTab.DUMMY_NOTE_TITLE, NotesTab.DUMMY_NOTE_DESC));
  }

  @Test
  public void userCanEditNote() {
    /**
     * Write a Selenium test that logs in an existing user with existing notes, clicks the edit note
     * button on an existing note, changes the note data, saves the changes, and verifies that the
     * changes appear in the note list.
     */
    TestUtils.registerUser(driver, appUrl, "editNoteUser");
    TestUtils.loginUser(driver,appUrl, "editNoteUser");
    NotesTab notesTab = setupNoteForUser(driver);
    // can edit note
    // edit modal
    notesTab.editNoteModal(driver);
    // reload page from driver
    notesTab = new NotesTab(driver);
    assertTrue(notesTab.addNoteModalLoaded());
    // add values
    notesTab.editDummyNote(driver);
    // submit  modal
    notesTab.saveNote(driver);
    // redirects to home so need to reselect the Notes tab
    HomePage homePage = new HomePage(driver);
    homePage.chooseNotesTab(driver);
    // assert changes show in page
    notesTab = new NotesTab(driver);
    assertTrue(notesTab.isNoteInPage(NotesTab.EDITED_NOTE_TITLE, NotesTab.EDITED_NOTE_DESC));
    TestUtils.pause(5000);
  }
}
