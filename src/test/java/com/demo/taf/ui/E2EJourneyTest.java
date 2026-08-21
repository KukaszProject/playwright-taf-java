package com.demo.taf.ui;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.demo.taf.base.BaseTest;
import com.demo.taf.ui.pages.CartPage;
import com.demo.taf.ui.pages.ProductsPage;

public class E2EJourneyTest extends BaseTest {

    @Test
    @DisplayName("E2E: User can search for an item, add it to cart, and view it in checkout")
    void testSearchAndAddToCartJourney() {
        ProductsPage productsPage = new ProductsPage(page);
        CartPage cartPage = new CartPage(page);
        
        String targetProduct = "Blue Top";

        productsPage.navigateToProducts()
                    .searchFor(targetProduct)
                    .addFirstProductToCart()
                    .goToCartFromModal();

        List<String> cartItems = cartPage.getProductsInCart();
        
        assertThat(cartItems)
            .as("The cart should contain the item we just added")
            .anyMatch(name -> name.contains(targetProduct));
            
        assertThat(page.url())
            .as("User should be routed to the view_cart endpoint")
            .contains("/view_cart");
    }
}