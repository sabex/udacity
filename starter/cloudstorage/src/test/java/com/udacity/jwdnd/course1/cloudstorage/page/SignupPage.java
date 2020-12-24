package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

  public static final String SIGNUP_PATH = "/signup";
  public static final String PAGE_LOAD_MARKER = "signupButton";

  @FindBy(id = "inputFirstName")
  WebElement inputFirstName;

  @FindBy(id = "inputLastName")
  WebElement inputLastName;

  @FindBy(id = "inputUsername")
  WebElement inputUsername;

  @FindBy(id = "inputPassword")
  WebElement inputPassword;

  @FindBy(id = "signupButton")
  WebElement signupButton;

  @FindBy(id = "signupError")
  WebElement signupError;

  @FindBy(id = "signupSuccess")
  WebElement signupSuccess;

  @FindBy(id = "backToLoginLink")
  WebElement backToLoginLink;

  @FindBy(id = "goToLogonLink")
  WebElement goToLogonLink;

  public SignupPage(WebDriver webDriver) {
    PageFactory.initElements(webDriver, this);
  }

  public void signup(String firstName, String lastName, String username, String password) {
    inputFirstName.sendKeys(firstName);
    inputLastName.sendKeys(lastName);
    inputUsername.sendKeys(username);
    inputPassword.sendKeys(password);
    signupButton.click();
  }

  public boolean signupSuccessful() {
    return (signupSuccess.getText().contains("You successfully signed up!"));
  }

  public boolean signupFailed() {
    return ((signupError.getText() != null));
  }

  public void backToLogin() {
    backToLoginLink.click();
  }

  // this checks if the 'successful logon' message also has link to login
  public boolean hasLinkToLogin() {
    return (goToLogonLink.getText().contains("login"));
  }

  // utility to check if page loaded, existence of signup button is used to check
  public boolean pageLoaded() {
    return (signupButton.getText().contains("Sign Up"));
  }
}
