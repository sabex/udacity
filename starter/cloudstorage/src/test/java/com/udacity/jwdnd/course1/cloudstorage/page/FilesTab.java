package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FilesTab {

  public static final String PAGE_LOAD_MARKER = "uploadButton";

  @FindBy(id = "uploadButton")
  private WebElement uploadButton;

  public FilesTab(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void upload() {
    uploadButton.submit();
  }

  // utility to check if page loaded, existence of upload button is used to check
  public boolean pageLoaded() {
    return (uploadButton.getText().contains("Upload"));
  }
}
