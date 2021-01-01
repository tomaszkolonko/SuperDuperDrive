/*
 * Copyright (C) Schweizerische Bundesbahnen SBB, 2021.
 */

package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialsPage;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTests {
    @LocalServerPort
    private Integer port;

    private static WebDriver webDriver;
    private static WebDriverWait webDriverWait;
    private LoginPage loginPage;
    private NoteHomePage noteHomePage;
    private ResultPage resultPage;
    private CredentialsPage credentialsPage;

    @Autowired
    private CredentialsService credentialsService;

    private final String username = "John";
    private final String password = "yolo";
    private final Integer userId = 1;

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
        credentialsPage = new CredentialsPage(webDriver);
    }

    @Test
    public void testEditingCredentials() {
        String newURL = "https://www.google.ch";
        String newName = "Johnny";

        List<Credentials> johnsCredentialsList = credentialsService.getAllCredentialsByUserId(userId);
        assert(johnsCredentialsList.size() == 1);

        loginPage.enterCredentials(username, password);
        loginPage.submit();

        credentialsPage.makeSureRightCredentialsTabIsActive();
        credentialsPage.clickOnCredentialsEditButton();

        credentialsPage.changeModalCredentialsURL(newURL);
        credentialsPage.changeModalCredentialsUsernmae(newName);
        credentialsPage.saveModalChangesButton();
        credentialsPage.clickTheContinueButtonOnResultPageOnSuccess();

        johnsCredentialsList = credentialsService.getAllCredentialsByUserId(userId);
        assert(johnsCredentialsList.size() == 1);

        assert(johnsCredentialsList.get(0).getUrl().equals(newURL));
        assert(johnsCredentialsList.get(0).getUserName().equals(newName));

        webDriver.get("localhost:" + port + "/logout");


    }

    @Test
    public void testAddingAndDeletingNewCredentials() {
        List<Credentials> johnsCredentialsList = credentialsService.getAllCredentialsByUserId(userId);
        assert(johnsCredentialsList.size() == 1);

        loginPage.enterCredentials(username, password);
        loginPage.submit();

        credentialsPage.makeSureRightCredentialsTabIsActive();

        WebElement deleteButton = webDriverWait.until(webDriver1 -> webDriver1.findElement(By.id("addNewCredentialsButton")));
        credentialsPage.clickOnDeleteCredentialsButton();
        credentialsPage.clickTheContinueButtonOnResultPageOnSuccess();

        johnsCredentialsList.clear();
        johnsCredentialsList = credentialsService.getAllCredentialsByUserId(userId);
        assert(johnsCredentialsList.isEmpty());

        credentialsPage.clickOnAddNewCredentialsButton();

        credentialsPage.changeModalCredentialsURL("www.google.ch");
        credentialsPage.changeModalCredentialsUsernmae("John");
        credentialsPage.changeModalCredentialsPassword("test1234");

        credentialsPage.saveModalChangesButton();
        credentialsPage.clickTheContinueButtonOnResultPageOnSuccess();

        credentialsPage.makeSureRightCredentialsTabIsActive();

        johnsCredentialsList = credentialsService.getAllCredentialsByUserId(userId);
        assert(johnsCredentialsList.size() == 1);

    }
}
