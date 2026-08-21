package com.demo.taf.ui;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.demo.taf.base.BaseTest;
import com.demo.taf.ui.pages.ProductsPage;

public class SearchProductsTest extends BaseTest {

    @Test
    @DisplayName("UI: User can successfully search for products")
    public void testProductSearch() {
        ProductsPage productsPage = new ProductsPage(page);
        String searchTarget = "Blue Top";

        productsPage.navigateToProducts()
                    .searchFor(searchTarget);

        assertThat(productsPage.isSearchedProductsHeaderVisible())
            .as("Searched Products header should be visible after search")
            .isTrue();

        List<String> searchResults = productsPage.getSearchResults();
        assertThat(searchResults)
            .as("Search results should contain the searched product name")
            .anyMatch(result -> result.contains(searchTarget));
    }

    @Test
    @DisplayName("UI: Default Products page loads a full catalog")
    void testDefaultProductsLoad() {
        ProductsPage productsPage = new ProductsPage(page);

        productsPage.navigateToProducts();

        assertThat(productsPage.isAllProductsHeaderVisible())
            .as("'All Products' header should be visible on initial load")
            .isTrue();

        assertThat(productsPage.getSearchResults().size())
            .as("The catalog should contain multiple products by default")
            .isGreaterThan(5);
    }

    @Test
    @DisplayName("UI: User can navigate to a specific Product Details page")
    void testNavigateToProductDetails() {
        ProductsPage productsPage = new ProductsPage(page);

        productsPage.navigateToProducts()
                    .clickFirstViewProduct();

        assertThat(page.url())
            .as("The URL should route the user to the product details endpoint")
            .contains("/product_details/");
    }
    
}
