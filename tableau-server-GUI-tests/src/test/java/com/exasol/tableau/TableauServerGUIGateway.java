package com.exasol.tableau;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

import java.time.Duration;
import java.util.*;
import java.util.logging.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Gateway for interacting with Tableau Server.
 */
public class TableauServerGUIGateway {
    private static final Logger LOGGER = Logger.getLogger(TableauServerGUIGateway.class.getName());
    private final WebDriver driver;
    private final String httpHostAddress;

    private TableauServerGUIGateway(final String httpHostAddress) {
        this.httpHostAddress = httpHostAddress;
        WebDriverManager.chromedriver().setup();
        final ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        this.driver = new ChromeDriver(options);
    }

    public static TableauServerGUIGateway connectTo(final String httpHostAddress) {
        LOGGER.info(() -> "Connecting to '" + httpHostAddress + "'");
        final TableauServerGUIGateway gateway = new TableauServerGUIGateway(httpHostAddress);
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
        return this.getElements(type, attribute, id, 5);
    }

    private List<WebElement> getElements(final String type, final String attribute, final String id,
            final long secondsToWait) {
        final By xpath = By.xpath("//" + type + "[@" + attribute + "='" + id + "']");
        final Wait<WebDriver> wait = new FluentWait<>(this.driver).withTimeout(Duration.ofSeconds(secondsToWait))
                .ignoring(ElementClickInterceptedException.class);
        wait.until(elementToBeClickable(xpath));
        return this.driver.findElements(xpath);
    }

    public void logout() {
        this.driver.navigate().to(this.httpHostAddress);
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

    public Optional<String> createWorkbook(final Workbook workbook) {
        this.getElement("button", "data-tb-test-id", "explorer-create-content-MenuButton", 30).click();
        this.getElement("div", "data-tb-test-id", "create-workbook-button").click();
        this.switchToNewPage();
        this.getElement("div", "data-test-id", "server", 30).click();
        this.getElement("button", "data-tb-test-id", "connection-" + workbook.getConnectorName() + "-Button").click();
        explicitWait(2);
        this.getElement("input", "data-tb-test-id", "server-textfield-TextInput").sendKeys(workbook.getHostname());
        for (int i = 0; i < 4; ++i) { // workaround for chrome browser as clear() method doesn't work there
            this.getElement("input", "data-tb-test-id", "port-textfield-TextInput").sendKeys(Keys.BACK_SPACE);
        }
        this.getElement("input", "data-tb-test-id", "port-textfield-TextInput").sendKeys(workbook.getPort());
        this.getElement("input", "data-tb-test-id", "username-textfield-TextInput").sendKeys(workbook.getUsername());
        this.getElement("input", "data-tb-test-id", "password-textfield-TextInput").sendKeys(workbook.getPassword());
        this.getElement("button", "data-tb-test-id", "signIn-button-Button").click();
        this.explicitWait(2);
        return this.getElementIfExists("div", "data-tb-test-id", "modular-dialog-error-section-error")
                .map(WebElement::getText);
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
        return this.getElement("div", "class", "JoinArea__tabAuthTextEditingLabel__1xj1V tabAuthTextEditingLabel", 5)
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
        this.explicitWait(2);
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
        this.getElement("button", "data-tb-test-id", "tabDataTabExtractCreationOKTestId-Button", 20).click();
        final WebElement successBanner = this.getElement("div", "data-tb-test-id", "banner-success-toast-widget", 25);
        return successBanner.getText();
    }

    public void updateExtract(final String schemaName, final String tableName) {
        this.clickUpperMenuData("dataMenu");
        this.clickUpperMenuInnerButton(tableName + " (" + schemaName + ")");
        this.clickUpperMenuInnerButton("Refresh");
    }

    public void openSchema(final String schemaName) {
        final WebElement button = this.getElement("button", "data-tb-test-id", "dataTab-schema-selector-Dropdown", 5);
        this.explicitWait(3);
        button.click();
        final List<WebElement> schemas = this.getElements("span", "class", "ftmd0dp");
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
                new Actions(TableauServerGUIGateway.this.driver).contextClick(element).perform();
                final List<WebElement> menu = TableauServerGUIGateway.this.getElements("span", "data-test-id",
                        "tabMenuItemName");
                TableauServerGUIGateway.this.clickElementIfTextMatches("Add to Sheet", menu);
            });
        }
    }

    public void saveWorkbook(final Workbook workbook) {
        this.clickUpperMenuData("fileMenu");
        this.clickUpperMenuInnerButton("Save As...");
        this.explicitWait(3);
        this.getElement("input", "class", "tabAuthSaveTextInput tab-selectable", 5)
                .sendKeys(workbook.getWorkbookName());
        this.explicitWait(1);
        this.getElement("button", "data-tb-test-id", "save-dialog-save-Button", 5).click();
        this.explicitWait(3);
        this.getElement("input", "data-tb-test-id", "auth-component-password-text-field-TextInput", 5)
                .sendKeys(workbook.getPassword());
        this.getElement("button", "data-tb-test-id", "auth-component-sign-in-button-Button").click();
    }

    public boolean workbookExists(final Workbook workbook) {
        this.openWorkbooksList();
        try {
            this.driver.findElement(By.linkText(workbook.getWorkbookName()));
            return true;
        } catch (final NoSuchElementException exception) {
            return false;
        }
    }

    private void openWorkbooksList() {
        this.explicitWait(2);
        this.driver.navigate().to(this.httpHostAddress + "#/explore");
        this.getElement("button", "data-tb-test-id", "site-filter-by-Button").click();
        this.getElement("div", "data-tb-test-id", "site-filter-by-workbook-MenuItem").click();
        this.getElement("button", "data-tb-test-id", "view-mode-toggle-Button").click();
        this.getElement("div", "data-tb-test-id", "view-mode-toggle-list-MenuItem").click();
    }

    public void deleteWorkbookIfExists(final Workbook workbook) {
        this.openWorkbooksList();
        try {
            this.driver.findElement(By.linkText(workbook.getWorkbookName())).click();
            explicitWait(2);
            this.getElement("button", "data-tb-test-id", "action-menu-Button").click();
            explicitWait(2);
            this.clickElementIfTextMatches("Delete…",
                    this.getElements("div", "data-tb-test-id", "action-menu-TextMenuItem"));
            this.getElement("button", "data-tb-test-id", "confirm-action-dialog-confirm-Button").click();
            this.driver.navigate().to(this.httpHostAddress);
        } catch (final NoSuchElementException exception) {
            // ignoring the exception
        }
    }
}