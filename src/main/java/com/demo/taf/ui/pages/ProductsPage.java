package com.demo.taf.ui.pages;

import java.util.List;

import com.microsoft.playwright.Page;

import io.qameta.allure.Step;

public class ProductsPage extends BasePage {
    
    private final String productsLink = "a[href='/products']";
    private final String searchInput = "input#search_product";
    private final String searchButton = "button#submit_search";
    private final String searchedProductsHeader = "h2.title.text-center:has-text('Searched Products')";
    private final String allProductsHeader = "h2.title.text-center:has-text('All Products')";
    private final String searchResults = "div.productinfo p";
    private final String viewProductButton = "ul.nav-pills a[href^='/product_details']";
    
    public ProductsPage(Page page) {
        super(page);
    }

    @Step("Navigating to the Products page")
    public ProductsPage navigateToProducts() {
        acceptCookiesIfPresent(); 
        page.locator(productsLink).click();
        page.waitForLoadState();

        return this;
    }

    @Step("Searching for product: {productName}")
    public ProductsPage searchFor(String productName) {
        type(searchInput, productName);
        page.locator(searchButton).click();

        return this;
    }

    @Step("Checking if 'Searched Products' header is visible")
    public boolean isSearchedProductsHeaderVisible() {
        page.locator(searchedProductsHeader).waitFor();
        return true;
    }

    @Step("Checking if 'All Products' header is visible")
    public boolean isAllProductsHeaderVisible() {
        page.locator(allProductsHeader).waitFor();
        return true;
    }

    @Step("Clicking the first 'View Product' button")
    public void clickFirstViewProduct() {
        page.locator(viewProductButton).first().click();
    }

    @Step("Retrieving search results")
    public List<String> getSearchResults() {
        page.locator(searchResults).first().waitFor();
        
        return page.locator(searchResults).allTextContents();
    }
}
