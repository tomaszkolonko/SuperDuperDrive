/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "loginButton")
    private WebElement submitButton;

    @FindBy(id = "signupLink")
    private WebElement signupLink;

    private final WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,  this);
    }

    public void enterCredentials(String username, String password) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", inputUsername);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + username + "';",inputUsername);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", inputPassword);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + password + "';",inputPassword);
    }

    public void submit() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitButton);
    }

    public void goToSignupLink() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", signupLink);
    }
}
