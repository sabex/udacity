package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class NotesTab {

  public static final String PAGE_LOAD_MARKER = "uploadButton";

  @FindBy(id = "notesTable")
  private WebElement notesTable;

  @FindBy(id = "addNote")
  private WebElement addNote;

  @FindBy(id = "saveNote")
  private WebElement saveNote;

  @FindBy(id = "note-title")
  private WebElement noteTitle;

  @FindBy(id = "note-description")
  private WebElement noteDescription;

  @FindBy(id = "credential-password")
  private WebElement credentialPassword;

  public NotesTab(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public static final String DUMMY_NOTE_TITLE = "A dummy note";
  public static final String DUMMY_NOTE_DESC = "A dummy note description";
  public static final String EDITED_NOTE_TITLE = "A dummy note edited";
  public static final String EDITED_NOTE_DESC = "A dummy note description edited";

  public void addNoteModal(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(addNote)).click();
  }

  public void editNoteModal(WebDriver driver) {
    WebElement webElement = notesTable.findElement(By.xpath("//*[@id=\"notesTable\"]/tbody/tr/td[1]/button"));
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
  }

  // utility to check if page loaded, existence of credential table is used to check
  public boolean pageLoaded() {
    return (notesTable.isEnabled());
  }

  // utility to check if modal loaded, existence of submit button is used to check
  public boolean addNoteModalLoaded() {
    return (saveNote.isEnabled());
  }

  public void saveNote(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(saveNote)).click();
  }

  public void addDummyNote(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(DUMMY_NOTE_TITLE);
    wait.until(ExpectedConditions.elementToBeClickable(noteDescription))
        .sendKeys(DUMMY_NOTE_DESC);
  }

  public void editDummyNote(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).clear();
    wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys(EDITED_NOTE_TITLE);
    wait.until(ExpectedConditions.elementToBeClickable(noteDescription)).clear();
    wait.until(ExpectedConditions.elementToBeClickable(noteDescription))
            .sendKeys(EDITED_NOTE_DESC);
  }

  public boolean isNoteInPage(String title, String description) {
    // test uses a new user so note will be the first one in the list
    try {
      WebElement noteTitle = notesTable.findElement(By.xpath("//*[@id='notesTable']/tbody/tr/th"));
      WebElement noteDescription = notesTable.findElement(By.xpath("//*[@id='notesTable']/tbody/tr/td[2]"));
      log.warn(noteTitle.getAttribute("textContent"));
      log.warn(noteDescription.getAttribute("textContent"));
      return (noteTitle.getAttribute("textContent").equalsIgnoreCase(title)
      && noteDescription.getAttribute("textContent").equalsIgnoreCase(description));
    } catch (NoSuchElementException nse) {
      return false;
    }
  }

  public void deleteNote(WebDriver driver) {
    WebElement element = notesTable.findElement(By.xpath("//*[@id='notesTable']/tbody[1]/tr/td[1]/a"));
    JavascriptExecutor js= (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", element);
  }
}
