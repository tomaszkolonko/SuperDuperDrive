/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(id = "theContinueButtonOnSuccess")
    private WebElement theContinueButtonOnSuccess;

    @FindBy(id = "theContinueButtonOnError")
    private WebElement theContinueButtonOnError;

    private final WebDriver webDriver;

    public ResultPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,  this);
    }

    public void clickTheContinueButtonOnResultPageOnSuccess() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", theContinueButtonOnSuccess);
    }

    public void clickTheContinueButtonOnResultPageOnError() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", theContinueButtonOnError);
    }
}
