package com.lab9.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.lab9.core.BasePage;

/**
 * Minimal checkout overview page object.
 */
public class CheckoutOverviewPage extends BasePage {

    @FindBy(css = "span.title")
    private WebElement pageTitle;

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("checkout-step-two"));
            WebElement title = wait.until(ExpectedConditions.visibilityOf(pageTitle));
            return title.getText().trim().contains("Checkout: Overview");
        } catch (org.openqa.selenium.TimeoutException ex) {
            return false;
        }
    }
}
