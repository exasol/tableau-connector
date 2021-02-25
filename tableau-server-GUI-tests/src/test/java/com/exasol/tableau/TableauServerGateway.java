package com.exasol.tableau;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Gateway for interacting with Tableau Server.
 */
public class TableauServerGateway {
    private final WebDriver driver;

    private TableauServerGateway() {
        System.setProperty("webdriver.gecko.driver", "/home/mc/Downloads/geckodriver-v0.29.0-linux64/geckodriver");
        this.driver = new FirefoxDriver();
    }

    public static TableauServerGateway connectTo(final String httpHostAddress) {
        final TableauServerGateway gateway = new TableauServerGateway();
        gateway.openSession(httpHostAddress);
        return gateway;
    }

    private void openSession(final String httpHostAddress) {
        this.driver.get(httpHostAddress);
    }

    public void login(final String username, final String password) {
        this.waitForPageLoading();
        this.getElement("input", "tb-test-id", "textbox-username-input").sendKeys(username);
        this.getElement("input", "tb-test-id", "textbox-password-input").sendKeys(password);
        this.waitForPageLoading();
        this.getElement("input", "tb-test-id", "button-signin").sendKeys(password);
        this.driver.findElement(By.xpath("//button[@tb-test-id='button-signin']")).click();
    }

    public void logout() {
        this.getElement("div", "data-tb-test-id", "flyout-list-menu-Button").click();
        this.waitForPageLoading();
        this.getElement("div", "data-tb-test-id", "flyout-list-menu-signOut-MenuItem").click();
    }

    private WebElement getElement(final String type, final String attribute, final String id) {
        return this.driver.findElement(By.xpath("//" + type + "[@" + attribute + "='" + id + "']"));
    }

    private void waitForPageLoading() {
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }

    public void createWorkbookForConnector(final String connectorName) {
        this.driver.findElement(By.xpath("//button[@tb-test-id='button-signin']")).click();
//        data-tb-test-id="explorer-create-content-MenuButton"
    }

    public void selectConnector(final String connectorName) {
    }

    public void closeConnection() {
        this.driver.quit();
    }
}