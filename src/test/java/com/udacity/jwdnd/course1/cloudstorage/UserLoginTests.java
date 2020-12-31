package com.udacity.jwdnd.course1.cloudstorage;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserLoginTests {
    @LocalServerPort
    private Integer port;

    private static WebDriver webDriver;
    private static WebDriverWait webDriverWait;
    private LoginPage loginPage;
    private SignupPage signupPage;

    private final String username = "John";
    private final String password = "yolo";
    private final String firstName = "John";
    private final String lastName = "Doe";


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
        signupPage = new SignupPage(webDriver);
    }

    @Test
    public void testLogin() {
        loginPage.enterCredentials(username, password);
        loginPage.submit();

        WebElement fileUpload = webDriverWait.until(webDriver1 -> webDriver1.findElement(By.id("fileUpload")));
        assertNotNull(fileUpload);

    }

    @Test
    public void testLoginWithWrongCredentials() {
        loginPage.enterCredentials(username, "test123");
        loginPage.submit();
        // Wait for page load times
        WebElement errorMessage = webDriverWait.until(webDriverTemp -> webDriverTemp.findElement(By.id("errorMessage")));
        assertEquals(errorMessage.getText(), "Invalid username or password");
    }

    @Test
    public void testSignupAndBackToLoginLinks() {
        loginPage.goToSignupLink();
        signupPage.backToLogin();
        WebElement inputUserName = webDriverWait.until(webDriver1 -> webDriver1.findElement(By.id("inputUsername")));
        assertNotNull(inputUserName);
    }

    @Test
    public void testSignupNewUserAndLogin() {
        loginPage.goToSignupLink();
        signupPage.enterCredentials("Peter", "Wolfgang", "Peter", "pet123");
        signupPage.submit();

        WebElement successMessage = webDriverWait.until(webDriver1 -> webDriver1.findElement(By.id("successMessage")));
        assertEquals(successMessage.getText(), "You successfully signed up! Please continue to the login page.");
        signupPage.backToLogin();

        WebElement inputUserName = webDriverWait.until(webDriver1 -> webDriver1.findElement(By.id("inputUsername")));
        loginPage.enterCredentials("Peter", "pet123");
        loginPage.submit();

        WebElement fileUpload = webDriverWait.until(webDriver1 -> webDriver1.findElement(By.id("fileUpload")));
        assertNotNull(fileUpload);

    }
}
