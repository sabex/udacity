package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class FilesTab {

  public static final String PAGE_LOAD_MARKER = "uploadButton";

  @FindBy(id = "uploadButton")
  private WebElement uploadButton;

  @FindBy(id = "fileTable")
  private WebElement fileTable;

  @FindBy(id = "fileUpload")
  private WebElement fileUpload;

  @FindBy(id = "message")
  private WebElement message;

  public FilesTab(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void upload() {
    String fileName = System.getProperty("user.dir") + "/src/test/resources/dummy.md";
    fileUpload.sendKeys(fileName);
    uploadButton.submit();
  }

  // utility to check if page loaded, existence of upload button is used to check
  public boolean pageLoaded() {
    return (uploadButton.getText().contains("Upload"));
  }

  public boolean isFileUploaded() {
    // test uses a new user so file will be the first one in the list
    try {
      WebElement file = fileTable.findElement(By.xpath("//tbody[1]/tr/th"));
      return (file.getAttribute("textContent").equalsIgnoreCase("dummy.md"));
    } catch (NoSuchElementException nse) {
      return false;
    }
  }

  public boolean hasErrorMessage() {
    return (message != null);
  }

  public void deleteFile(WebDriver driver) {
    WebElement element =
        fileTable.findElement(By.xpath("//*[@id='fileTable']/tbody[1]/tr/td/a[2]"));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", element);
  }

  public void downloadFile(WebDriver driver) {
    WebElement webElement = fileTable.findElement(By.xpath("//*[@id='fileTable']/tbody/tr/td/a[1]"));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].click();", webElement);
    TestUtils.pause(500);   // give time for download to finish
  }
}
