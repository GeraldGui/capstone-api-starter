package org.yearup.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void search_withNoFilters_returnsAllProducts() {
        // Arrange
        Product godOfWar = new Product(1, "God Of War", 49.99, 1, "RPG", null, 5, true, null);
        Product mario = new Product(2, "Mario Kart", 89.99, 4, "Adventure", null, 5, true, null);
        Product forzaHorizon = new Product(3, "Forza Horizon 5", 59.99, 3, "Car", null, 5, true, null);
        when(productRepository.findAll()).thenReturn(List.of(mario, godOfWar, forzaHorizon));

        // Act
        List<Product> found = productService.search(null, null, null, null);

        // Assert
        assertEquals(3, found.size());
    }

    @Test
    void search_withFilters_returnsProductsWithFilter() {
        // Arrange
        Product godOfWar = new Product(1, "God Of War", 49.99, 1, "RPG", null, 5, true, null);
        Product mario = new Product(2, "Mario Kart", 89.99, 4, "Adventure", null, 5, true, null);
        Product forzaHorizon = new Product(3, "Forza Horizon 5", 59.99, 3, "Car", null, 5, true, null);
        when(productRepository.findAll()).thenReturn(List.of(mario, godOfWar, forzaHorizon));

        // Act
        List<Product> found = productService.search(null, 30.00, 60.00, null);

        // Assert
        assertEquals(2, found.size());
    }
}