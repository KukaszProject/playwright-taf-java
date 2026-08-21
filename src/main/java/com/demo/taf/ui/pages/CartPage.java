package com.demo.taf.ui.pages;

import java.util.List;

import com.microsoft.playwright.Page;

import io.qameta.allure.Step;

public class CartPage extends BasePage {

    private final String cartProductNames = "td.cart_description h4 a";

    public CartPage(Page page) {
        super(page);
    }

    @Step("Get a list of all product names currently in the cart")
    public List<String> getProductsInCart() {
        page.locator(cartProductNames).first().waitFor();
        return page.locator(cartProductNames).allTextContents();
    }
}