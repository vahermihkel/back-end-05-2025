package ee.mihkel.webshop.service;

import ee.mihkel.webshop.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void calculateSum() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(1L);
        products.add(product1);
        Product product2 = new Product();
        product2.setId(2L);
        products.add(product2);

        double totalSum = orderService.calculateTotalSum(products);
        assertEquals(901,totalSum);
    }
}