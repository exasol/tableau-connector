package com.exasol.tableau;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import java.time.Duration;
import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Gateway for interacting with Tableau Server.
 */
public class TableauServerGateway {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private TableauServerGateway() {
        WebDriverManager.firefoxdriver().setup();
        this.driver = new FirefoxDriver();
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(10));
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
        this.getElement("input", "tb-test-id", "textbox-username-input").sendKeys(username);
        this.getElement("input", "tb-test-id", "textbox-password-input").sendKeys(password);
        this.getElement("button", "tb-test-id", "button-signin").click();
    }

    public void logout() {
        this.explicitWait();
        this.getElement("div", "title", "Close").click();
        this.getElement("button", "data-tb-test-id", "tab-confirmation-deny-Button").click();
        this.driver.switchTo().window(new ArrayList<>(this.driver.getWindowHandles()).get(0));
        this.getElement("button", "data-tb-test-id", "flyout-list-menu-Button").click();
        this.getElement("div", "data-tb-test-id", "flyout-list-menu-signOut-MenuItem").click();
    }

    private WebElement getElement(final String type, final String attribute, final String id) {
        final By xpath = By.xpath("//" + type + "[@" + attribute + "='" + id + "']");
        this.wait.until(presenceOfElementLocated(xpath));
        return this.driver.findElement(xpath);
    }

    // Use it when waiting for the element doesn't work until further investigation
    private void explicitWait() {
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public void createWorkbookForConnector(final String connectorName, final String sourceHostname,
            final String sourceUsername, final String sourcePassword) {
        this.getElement("button", "data-tb-test-id", "explorer-create-content-MenuButton").click();
        this.getElement("div", "data-tb-test-id", "create-workbook-button").click();
        this.switchToNewPage();
        this.getElement("div", "data-test-id", "server").click();
        this.getElement("button", "data-tb-test-id", "connection-" + connectorName + "-Button").click();
        this.getElement("input", "data-tb-test-id", "server-textfield-TextInput").sendKeys(sourceHostname);
        this.getElement("input", "data-tb-test-id", "username-textfield-TextInput").sendKeys(sourceUsername);
        this.getElement("input", "data-tb-test-id", "password-textfield-TextInput").sendKeys(sourcePassword);
        this.getElement("button", "data-tb-test-id", "signIn-button-Button").click();
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
        return this.getElement("div", "class", "JoinArea__tabAuthTextEditingLabel__1xj1V tabAuthTextEditingLabel")
                .getText();
    }

    public void renameConnection(final String renamed_connection) {
        final WebElement connectionTab = this.getElement("div", "class",
                "JoinArea__tabAuthTextEditingLabel__1xj1V tabAuthTextEditingLabel");
        this.explicitWait();
        this.performDoubleClick(connectionTab);
        final WebElement input = this.getElement("input", "data-tb-test-id", "text-editing-TextInput");
        input.sendKeys(renamed_connection);
        input.sendKeys(Keys.ENTER);
    }

    private void performDoubleClick(final WebElement connectionTab) {
        new Actions(this.driver).doubleClick(connectionTab).perform();
    }

    public String duplicateDataSource() {
        this.clickUpperMenuData();
        this.clickUpperMenuInnerButton("Duplicate Data Source");
        return this.getElement("input", "data-tb-test-id", "renameDataSource-TextInput").getAttribute("value");
    }

    private void clickUpperMenuData() {
        final WebElement menu = this.getElement("div", "class", "tabAuthMenuBarMenu dataMenu");
        this.explicitWait();
        menu.click();
    }

    private List<WebElement> getElements(final String type, final String attribute, final String id) {
        final By xpath = By.xpath("//" + type + "[@" + attribute + "='" + id + "']");
        this.wait.until(presenceOfElementLocated(xpath));
        return this.driver.findElements(xpath);
    }

    public void createExtract(final String schemaName, final String tableName) {
        this.openSchema(schemaName);
        this.openTable(tableName);
        this.explicitWait();
        this.getElement("label", "data-tb-test-id", "tabDataTabExtractToggleTestId-Label").click();
        this.explicitWait();
        this.getElement("a", "data-tb-test-id", "extract-create-button-TextLink").click();
        this.explicitWait();
        this.getElement("button", "data-tb-test-id", "tabDataTabExtractCreationOKTestId-Button").click();
        this.longExplicitWait();
        this.switchSheet("Sheet 1");
        this.clickUpperMenuData();
        this.clickUpperMenuInnerButton(tableName + " (" + schemaName + ")");
        this.clickUpperMenuInnerButton("Refresh");
        this.explicitWait();
    }

    // Use it when waiting for the element doesn't work until further investigation
    private void longExplicitWait() {
        try {
            Thread.sleep(17000);
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    private void clickUpperMenuInnerButton(final String buttonName) {
        final List<WebElement> elements = this.getElements("span", "data-test-id", "tabMenuItemName");
        this.clickElementIfTextMatches(buttonName, elements);
    }

    private void switchSheet(final String sheetName) {
        final List<WebElement> sheets = this.getElements("span", "class", "tabAuthTabLabel");
        this.doubleClickElementIfTextMatches(sheetName, sheets);
    }

    private void openSchema(final String schemaName) {
        this.explicitWait();
        this.getElement("button", "data-tb-test-id", "dataTab-schema-selector-Dropdown").click();
        final List<WebElement> schemas = this.getElements("span", "class", "frvoegc");
        this.clickElementIfTextMatches(schemaName, schemas);
    }

    private void clickElementIfTextMatches(final String text, final List<WebElement> elements) {
        for (final WebElement element : elements) {
            if (element.getText().equals(text)) {
                element.click();
                break;
            }
        }
    }

    private void openTable(final String tableName) {
        final List<WebElement> tables = this.getElements("div", "class", "tabDataTabCSTableName");
        this.doubleClickElementIfTextMatches(tableName, tables);
    }

    private void doubleClickElementIfTextMatches(final String text, final List<WebElement> elements) {
        for (final WebElement schema : elements) {
            if (schema.getText().equals(text)) {
                this.performDoubleClick(schema);
                break;
            }
        }
    }
}