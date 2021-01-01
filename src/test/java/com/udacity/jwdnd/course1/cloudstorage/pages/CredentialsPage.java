/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2021.
 */

package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsPage {

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button")
    private WebElement editCredentialsButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a")
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

    @FindBy(xpath = "//*[@id=\"credentialModal\"]/div/div/div[3]/button[2]")
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
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", modalCredentialsPassword);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + pwd + "';",modalCredentialsPassword);
    }

    public void saveModalChangesButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", modalSaveChangesButton);
    }

    public void clickTheContinueButtonOnResultPageOnSuccess() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", theContinueButtonOnSuccess);
    }

    public void clickOnAddNewCredentialsButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", addNewCredentialsButton);
    }

    public void clickOnDeleteCredentialsButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", deleteCredentialsButton);
    }

    public String getModalPasswordValue() {
        return webDriver.findElement(By.id("credential-password")).getAttribute("value");
    }
}
