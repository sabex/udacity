package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

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

  public void addNoteModal(WebDriver driver) {
    TestUtils.pause(500);
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(addNote)).click();
  }

  // utility to check if page loaded, existence of credential table is used to check
  public boolean pageLoaded() {
    return (notesTable.isEnabled());
  }

  // utility to check if modal loaded, existence of submit button is used to check
  public boolean addNoteModalLoaded() {
    return (saveNote.isEnabled());
  }

  public void addNote(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(saveNote)).click();
  }

  public void addDummyNote(WebDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, 5);
    wait.until(ExpectedConditions.elementToBeClickable(noteTitle)).sendKeys("A Note Title");
    wait.until(ExpectedConditions.elementToBeClickable(noteDescription))
        .sendKeys("A Note Description");
  }

  public boolean isNoteInPage() {
    // test uses a new user so note will be the first one in the list
    try {
      WebElement note = notesTable.findElement(By.xpath("//*[@id='notesTable']/tbody/tr/th"));
    log.warn(note.getAttribute("textContent"));
      log.warn(note.getAttribute("innerHTML"));
    return (note.getAttribute("textContent").equalsIgnoreCase("A Note Title"));
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
