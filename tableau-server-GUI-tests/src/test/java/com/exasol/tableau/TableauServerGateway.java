package com.exasol.tableau;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Gateway for interacting with Tableau Server.
 */
public class TableauServerGateway {
    private final WebDriver driver;

    private TableauServerGateway() {
        WebDriverManager.firefoxdriver().setup();
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
        this.getElement("button", "tb-test-id", "button-signin").click();
    }

    public void logout() {
        waitForPageLoading();
        this.getElement("div", "title", "Close").click();
        this.getElement("button", "data-tb-test-id", "tab-confirmation-deny-Button").click();
        this.driver.switchTo().window(new ArrayList<>(this.driver.getWindowHandles()).get(0));
        waitForPageLoading();
        this.getElement("button", "data-tb-test-id", "flyout-list-menu-Button").click();
        this.getElement("div", "data-tb-test-id", "flyout-list-menu-signOut-MenuItem").click();
    }

    private WebElement getElement(final String type, final String attribute, final String id) {
        return this.driver.findElement(By.xpath("//" + type + "[@" + attribute + "='" + id + "']"));
    }

    private void waitForPageLoading() {
        try {
            Thread.sleep(3000);
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
        // this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2)); // This doesn't work, we need to find
        // another way
    }

    public void createWorkbookForConnector(final String connectorName, final String sourceHostname,
            final String sourceUsername, final String sourcePassword) {
        waitForPageLoading();
        this.getElement("button", "data-tb-test-id", "explorer-create-content-MenuButton").click();
        this.getElement("div", "data-tb-test-id", "create-workbook-button").click();
        waitForPageLoading();
        switchToNewPage();
        waitForPageLoading();
        this.getElement("div", "data-test-id", "server").click();
        getElement("button", "data-tb-test-id", "connection-" + connectorName + "-Button").click();
        waitForPageLoading();
        getElement("input", "data-tb-test-id", "server-textfield-TextInput").sendKeys(sourceHostname);
        getElement("input", "data-tb-test-id", "username-textfield-TextInput").sendKeys(sourceUsername);
        getElement("input", "data-tb-test-id", "password-textfield-TextInput").sendKeys(sourcePassword);
        getElement("button", "data-tb-test-id", "signIn-button-Button").click();
    }

    private void switchToNewPage() {
        final String currentWindow = this.driver.getWindowHandle();
        final Set<String> windowHandles = this.driver.getWindowHandles();
        for (final String windowHandle : windowHandles) {
            if (!windowHandle.equals(currentWindow)) {
                this.driver.switchTo().window(windowHandle);
            }
        }
    }

    public void closeConnection() {
        this.driver.quit();
    }

    public String getEstablishedConnectionName() {
        waitForPageLoading();
        return this.getElement("div", "class", "JoinArea__tabAuthTextEditingLabel__1xj1V tabAuthTextEditingLabel")
                .getText();
    }
}