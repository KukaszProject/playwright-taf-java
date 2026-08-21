package com.demo.taf.ui.pages;

import java.util.List;

import com.microsoft.playwright.Page;

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

    public ProductsPage navigateToProducts() {
        acceptCookiesIfPresent(); 
        page.locator(productsLink).click();

        return this;
    }

    public ProductsPage searchFor(String productName) {
        type(searchInput, productName);
        page.locator(searchButton).click();

        return this;
    }

    public boolean isSearchedProductsHeaderVisible() {
        try {
            page.locator(searchedProductsHeader).waitFor();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // NEW METHOD: Wait for default page to load
    public boolean isAllProductsHeaderVisible() {
        try {
            page.locator(allProductsHeader).waitFor();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // NEW METHOD: Click the first product details link
    public void clickFirstViewProduct() {
        page.locator(viewProductButton).first().click();
    }

    public List<String> getSearchResults() {
        page.locator(searchResults).first().waitFor();
        
        return page.locator(searchResults).allTextContents();
    }
}
