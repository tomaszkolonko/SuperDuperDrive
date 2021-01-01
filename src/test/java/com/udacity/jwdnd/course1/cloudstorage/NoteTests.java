package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import com.udacity.jwdnd.course1.cloudstorage.Services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NoteHomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {
    @LocalServerPort
    private Integer port;

    private static WebDriver webDriver;
    private static WebDriverWait webDriverWait;
    private LoginPage loginPage;
    private NoteHomePage noteHomePage;
    private ResultPage resultPage;

    @Autowired
    private NoteService noteService;

    private final String username = "John";
    private final String password = "yolo";

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriverWait = new WebDriverWait(webDriver, 2);
    }

    @AfterAll
    public static void afterAll() {
        webDriver.close();
    }

    @BeforeEach
    public void beforeEach() {
        webDriver.get("localhost:" + port + "/login");
        loginPage = new LoginPage(webDriver);
        noteHomePage = new NoteHomePage(webDriver);
        resultPage = new ResultPage(webDriver);

        // Prepare Database

    }

    @Test
    public void testAddingAndDeletingNote() {

        List<Note> listOfNotes = noteService.getAllNotes();
        long count = listOfNotes.stream().map((Note note) -> note.getNoteTitle()).filter(s -> s.contains("Clean Code")).count();
        assert(count == 0);

        webDriver.get("localhost:" + port + "/home");
        loginPage.enterCredentials(username, password);
        loginPage.submit();

        WebElement inputUserName = webDriverWait.until(webDriver1 -> webDriver1.findElement(By.id("navNotesTab")));
        assert(inputUserName != null);

        noteHomePage.makeSureYouAreOnNoteTab();
        noteHomePage.openEmptyNoteModal();


        noteHomePage.enterNewNote("Clean Code", "Note about SOLID principles");
        noteHomePage.submitModal();

        listOfNotes.clear();
        listOfNotes = noteService.getAllNotes();
        count = listOfNotes.stream().map((Note note) -> note.getNoteTitle()).filter(s -> s.contains("Clean Code")).count();
        assert(count == 1);

        resultPage.clickTheContinueButtonOnResultPageOnSuccess();
        noteHomePage.makeSureYouAreOnNoteTab();

        noteService.deleteNote(3);

        listOfNotes = noteService.getAllNotes();
        assert(listOfNotes.size() == 2);

        webDriver.get("localhost:" + port + "/logout");
    }

    @Test void testEditingANote() {
        noteService.deleteNote(2);
        Note specificNote = noteService.getNoteById(1);
        assertEquals(specificNote.getNoteTitle(), "TestNote");

        webDriver.get("localhost:" + port + "/home");
        loginPage.enterCredentials(username, password);
        loginPage.submit();

        noteHomePage.makeSureYouAreOnNoteTab();

        noteHomePage.clickOnEditNoteButton();
        noteHomePage.setNewTitle("TestNoteExtended");
        noteHomePage.submitModal();


        specificNote = noteService.getNoteById(1);
        assertEquals(specificNote.getNoteTitle(), "TestNoteExtended");

        webDriver.get("localhost:" + port + "/logout");

    }
}
