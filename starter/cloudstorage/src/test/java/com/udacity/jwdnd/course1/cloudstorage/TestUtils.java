package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.page.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.page.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.page.SignupPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * various utilities to allow re-use across multi step tests
 */
public class TestUtils {

    public static final String GOOD_PASSWORD = "password";
    public static final String FIRST_NAME = "Favorite";
    public static final String LAST_NAME = "User";

    public static SignupPage getSignupPage(WebDriver driver, String appUrl) {
        driver.get(appUrl + SignupPage.SIGNUP_PATH);
        return new SignupPage(driver);
    }

    public static LoginPage getLoginPage(WebDriver driver, String appUrl) {
        driver.get(appUrl + LoginPage.LOGIN_PATH);
        return new LoginPage(driver);
    }

    public static HomePage getHomePage(WebDriver driver, String appUrl) {
        driver.get(appUrl + HomePage.HOME_PATH);
        return new HomePage(driver);
    }

    public static void verifySignupPageLoads(SignupPage page) {
        assertTrue(page.pageLoaded());
    }

    public static void verifyLoginPageLoads(LoginPage page) {
        assertTrue(page.pageLoaded());
    }

    public static void waitForPageLoad(WebDriver driver, Integer timeout, String pageLoadMarker) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement marker = wait.until(webDriver -> webDriver.findElement(By.id(pageLoadMarker)));
    }

    public static void verifyHomePageLoads(HomePage page) {
        assertTrue(page.pageLoaded());
    }

    public static void registerUser(WebDriver driver, String appUrl, String userName) {
        SignupPage signupPage = getSignupPage(driver, appUrl);
        verifySignupPageLoads(signupPage);
        signupPage.signup(FIRST_NAME, LAST_NAME, userName, GOOD_PASSWORD);
    }

    public static void loginUser(WebDriver driver, String appUrl, String userName) {
        LoginPage loginPage = getLoginPage(driver, appUrl);
        loginPage.login(userName, GOOD_PASSWORD);
    }
}
