/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2021.
 */

package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsPage {

    @FindBy(className = "btn-success")
    private WebElement editCredentialsButton;

    @FindBy(className = "btn-danger")
    private WebElement deleteCredentialsButton;

    @FindBy(id = "navCredentialsTab")
    private WebElement navCredentialsTab;

    @FindBy(id = "addNewCredentialsButton")
    private WebElement addNewCredentialsButton;

    @FindBy(id = "credential-url")
    private WebElement modalCredentialsURL;

    @FindBy(id = "credential-username")
    private WebElement modalCredentialsUsername;

    @FindBy(id = "credential-password")
    private WebElement modalCredentialsPassword;

    @FindBy(className = "btn-primary")
    private WebElement modalSaveChangesButton;

    @FindBy(id = "theContinueButtonOnSuccess")
    private WebElement theContinueButtonOnSuccess;

    private final WebDriver webDriver;

    public CredentialsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    public void makeSureRightCredentialsTabIsActive() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navCredentialsTab);
    }

    public void clickOnCredentialsEditButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editCredentialsButton);
    }

    public void changeModalCredentialsURL(String url) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", modalCredentialsURL);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + url + "';",modalCredentialsURL);
    }

    public void changeModalCredentialsUsernmae(String username) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", modalCredentialsUsername);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + username + "';",modalCredentialsUsername);
    }

    public void changeModalCredentialsPassword(String pwd) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", theContinueButtonOnSuccess);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + pwd + "';",theContinueButtonOnSuccess);
    }

    public void saveModalChangesButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", modalSaveChangesButton);
    }

    public void clickTheContinueButtonOnResultPageOnSuccess() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", theContinueButtonOnSuccess);
    }
}
