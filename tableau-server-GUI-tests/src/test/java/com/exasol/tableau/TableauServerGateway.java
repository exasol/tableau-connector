package com.exasol.tableau;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

import java.time.Duration;
import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

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
        this.getElement("input", "tb-test-id", "textbox-username-input").sendKeys(username);
        this.getElement("input", "tb-test-id", "textbox-password-input").sendKeys(password);
        this.getElement("button", "tb-test-id", "button-signin").click();
    }

    private WebElement getElement(final String type, final String attribute, final String id) {
        return this.getElement(type, attribute, id, 2);
    }

    private WebElement getElement(final String type, final String attribute, final String id,
            final long secondsToWait) {
        final List<WebElement> matchingElements = this.getElements(type, attribute, id, secondsToWait);
        return matchingElements.stream().findFirst().orElse(null);
    }

    private List<WebElement> getElements(final String type, final String attribute, final String id) {
        return this.getElements(type, attribute, id, 2);
    }

    private List<WebElement> getElements(final String type, final String attribute, final String id,
            final long secondsToWait) {
        final By xpath = By.xpath("//" + type + "[@" + attribute + "='" + id + "']");
        final Wait<WebDriver> wait = new FluentWait<>(this.driver).withTimeout(Duration.ofSeconds(secondsToWait))
                .ignoring(ElementClickInterceptedException.class);
        wait.until(visibilityOfElementLocated(xpath));
        wait.until(elementToBeClickable(xpath));
        return this.driver.findElements(xpath);
    }

    public void logout() {
        this.explicitWait(2);
        this.getElement("div", "title", "Close").click();
        this.getElementIfExists("button", "data-tb-test-id", "tab-confirmation-deny-Button")
                .ifPresent(WebElement::click);
        this.driver.switchTo().window(new ArrayList<>(this.driver.getWindowHandles()).get(0));
        this.getElement("button", "data-tb-test-id", "flyout-list-menu-Button").click();
        this.getElement("div", "data-tb-test-id", "flyout-list-menu-signOut-MenuItem").click();
        this.explicitWait(1);
    }

    // Use it when waiting for the element doesn't work until further investigation
    private void explicitWait(final long seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (final InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    public Optional<String> createWorkbookForConnector(final String connectorName, final String sourceHostname,
            final String sourceUsername, final String sourcePassword) {
        this.getElement("button", "data-tb-test-id", "explorer-create-content-MenuButton").click();
        this.getElement("div", "data-tb-test-id", "create-workbook-button").click();
        this.switchToNewPage();
        this.getElement("div", "data-test-id", "server", 10).click();
        this.getElement("button", "data-tb-test-id", "connection-" + connectorName + "-Button").click();
        this.getElement("input", "data-tb-test-id", "server-textfield-TextInput").sendKeys(sourceHostname);
        this.getElement("input", "data-tb-test-id", "username-textfield-TextInput").sendKeys(sourceUsername);
        this.getElement("input", "data-tb-test-id", "password-textfield-TextInput").sendKeys(sourcePassword);
        this.getElement("button", "data-tb-test-id", "signIn-button-Button").click();
        this.getElementIfExists("button", "data-tb-test-id", "detailedErrorDialog-Dialog-CloseButton")
                .ifPresent(WebElement::click);
        final Optional<String> errorMessage = this
                .getElementIfExists("div", "data-tb-test-id", "modular-dialog-error-section-error")
                .map(WebElement::getText);
        if (errorMessage.isPresent()) {
            this.closeDialogs();
        }
        return errorMessage;
    }

    private void closeDialogs() {
        this.getElement("button", "data-tb-test-id", "modular-connection-hybrid-dialog-id-Dialog-CloseButton").click();
        this.explicitWait(2);
        this.explicitWait(2);
        this.getElement("div", "class", "tab-Icon tabConnectionDialogHeaderCloseIcon").click();
    }

    private Optional<WebElement> getElementIfExists(final String type, final String attribute, final String id) {
        try {
            return Optional.of(this.getElement(type, attribute, id));
        } catch (final TimeoutException | NoSuchWindowException exception) {
            return Optional.empty();
        }
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
        this.explicitWait(1);
        this.doubleClickOn(connectionTab);
        final WebElement input = this.getElement("input", "data-tb-test-id", "text-editing-TextInput");
        input.sendKeys(renamed_connection);
        input.sendKeys(Keys.ENTER);
    }

    private void doubleClickOn(final WebElement connectionTab) {
        new Actions(this.driver).doubleClick(connectionTab).perform();
    }

    public String duplicateDataSource() {
        this.clickUpperMenuData("dataMenu");
        this.clickUpperMenuInnerButton("Duplicate Data Source");
        return this.getElement("input", "data-tb-test-id", "renameDataSource-TextInput").getAttribute("value");
    }

    private void clickUpperMenuData(final String menuName) {
        final WebElement menu = this.getElement("div", "class", "tabAuthMenuBarMenu " + menuName);
        this.explicitWait(2);
        menu.click();
    }

    public String createExtract() {
        this.explicitWait(2);
        this.getElement("label", "data-tb-test-id", "tabDataTabExtractToggleTestId-Label").click();
        this.explicitWait(2);
        this.getElement("a", "data-tb-test-id", "extract-create-button-TextLink").click();
        this.getElement("button", "data-tb-test-id", "tabDataTabExtractCreationOKTestId-Button", 10).click();
        final WebElement successBanner = this.getElement("div", "data-tb-test-id", "banner-success-toast-widget", 17);
        return successBanner.getText();
    }

    public void updateExtract(final String schemaName, final String tableName) {
        this.clickUpperMenuData("dataMenu");
        this.clickUpperMenuInnerButton(tableName + " (" + schemaName + ")");
        this.clickUpperMenuInnerButton("Refresh");
    }

    public void openSchema(final String schemaName) {
        final WebElement button = this.getElement("button", "data-tb-test-id", "dataTab-schema-selector-Dropdown");
        this.explicitWait(2);
        button.click();
        final List<WebElement> schemas = this.getElements("span", "class", "frvoegc");
        this.clickElementIfTextMatches(schemaName, schemas);
    }

    private void clickElementIfTextMatches(final String text, final List<WebElement> elements) {
        this.getElementByText(text, elements).ifPresent(WebElement::click);
    }

    private Optional<WebElement> getElementByText(final String text, final List<WebElement> elements) {
        return elements.stream().filter(e -> text.equals(e.getText())).findFirst();
    }

    public void openTable(final String tableName) {
        this.explicitWait(2);
        final List<WebElement> tables = this.getElements("div", "class", "tabDataTabCSTableName");
        this.doubleClickElementIfTextMatches(tableName, tables);
    }

    private void doubleClickElementIfTextMatches(final String text, final List<WebElement> elements) {
        this.getElementByText(text, elements).ifPresent(this::doubleClickOn);
    }

    private void clickUpperMenuInnerButton(final String buttonName) {
        final List<WebElement> elements = this.getElements("span", "data-test-id", "tabMenuItemName");
        this.clickElementIfTextMatches(buttonName, elements);
    }

    public void switchSheet(final String sheetName) {
        this.explicitWait(2);
        final List<WebElement> sheets = this.getElements("span", "class", "tabAuthTabLabel");
        this.doubleClickElementIfTextMatches(sheetName, sheets);
    }

    public void addToSheet(final String... columns) {
        final List<WebElement> elements = this.getElements("div", "class", "tab-schema-field-label tab-unselectable");
        for (final String columnName : columns) {
            final Optional<WebElement> column = this.getElementByText(columnName, elements);
            column.ifPresent(element -> {
                this.explicitWait(2);
                element.click();
                new Actions(TableauServerGateway.this.driver).contextClick(element).perform();
                final List<WebElement> menu = TableauServerGateway.this.getElements("span", "data-test-id",
                        "tabMenuItemName");
                TableauServerGateway.this.clickElementIfTextMatches("Add to Sheet", menu);
            });
        }
    }

    public void saveWorkbook(final String workbookName) {
        this.clickUpperMenuData("fileMenu");
        this.clickUpperMenuInnerButton("Save As...");
        this.getElement("input", "class", "tab-selectable").sendKeys(workbookName);
        this.getElement("button", "data-tb-test-id", "save-dialog-save-Button").click();
    }
}