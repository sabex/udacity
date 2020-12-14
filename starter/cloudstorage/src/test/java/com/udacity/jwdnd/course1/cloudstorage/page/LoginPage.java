package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
  @FindBy(id = "inputUsername")
  private WebElement usernameField;

  @FindBy(id = "inputPassword")
  private WebElement passwordField;

  @FindBy(id = "loginButton")
  private WebElement submitButton;

  @FindBy(id = "loginFailed")
  private WebElement invalidCreds;

  @FindBy(className = "alert alert-dark")
  private WebElement loggedOut;

  @FindBy(id = "signUp")
  private WebElement signUp;

  public static final String LOGIN_PATH = "/login";
  public static final String PAGE_LOAD_MARKER = "loginButton";

  public LoginPage(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void login(String username, String password) {
    this.usernameField.sendKeys(username);
    this.passwordField.sendKeys(password);
    this.submitButton.click();
  }

  public void signup() {
    this.signUp.click();
  }

  // utility to check if page loaded, existence of submit button is used to check
  public boolean pageLoaded() {
      return (submitButton.getText().contains("Login"));
  }

  public boolean isLoginFailed() {
      return (invalidCreds.getText().contains("Invalid username or password"));
  }
}
