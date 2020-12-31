/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2020.
 */

package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NoteHomePage {

    @FindBy(id = "navNotesTab")
    private WebElement noteTab;

    @FindBy(id = "showNoteModalButton")
    private WebElement showNoteModalButton;

    @FindBy(id = "note-title")
    private WebElement noteTitelWithinModal;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionWithinModal;

    @FindBy(id = "submitNoteModal")
    private WebElement submitNoteModalButton;

    @FindBy(id = "theContinueButton")
    private WebElement theContinueButton;

    @FindBy(className = "btn-danger")
    private WebElement deleteNoteButton;

    @FindBy(className = "btn-success")
    private WebElement editNoteButton;

    private final WebDriver webDriver;

    public NoteHomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver,  this);
    }

    public void openEmptyNoteModal() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", showNoteModalButton);
    }

    public void enterNewNote(String noteTitle, String noteDescription) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteTitelWithinModal);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteTitle + "';",noteTitelWithinModal);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteDescriptionWithinModal);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteDescription + "';",noteDescriptionWithinModal);
    }

    public void submitModal() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", submitNoteModalButton);
    }

    public void makeSureYouAreOnNoteTab() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteTab);
    }

    public void clickOnDeleteNoteButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", deleteNoteButton);
    }

    public void clickOnEditNoteButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editNoteButton);
    }

    public void setNewTitle(String newTitle) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteTitelWithinModal);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + newTitle + "';",noteTitelWithinModal);
    }

}
