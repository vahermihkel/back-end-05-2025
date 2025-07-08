package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.model.Supplier1Product;
import ee.mihkel.webshop.model.Supplier2Product;
import ee.mihkel.webshop.model.Supplier3Product;
import ee.mihkel.webshop.model.Supplier3Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SupplierController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("supplier1")
    public List<Supplier1Product> getSupplier1Products() {

        String url = "https://fakestoreapi.com/products";
        Supplier1Product[] products = restTemplate.exchange(url, HttpMethod.GET, null, Supplier1Product[].class).getBody();
        List<Supplier1Product> productList = List.of(products);

        List<Supplier1Product> productListFiltered = productList.stream()
                .filter(product -> product.getRating().getRate() > 3.5)
                .map(product -> {
                    double retailPrice = Math.round((product.getPrice() * 1.2) * 100.0) / 100.0;
                    product.setRetailPrice(retailPrice);
                    return product;
                })
                .collect(Collectors.toList());
        return productListFiltered;
    }

    @GetMapping("supplier2/{findByCategory}")
    public List<Supplier2Product> getSupplier2Products(@PathVariable String findByCategory) {
        String url = "https://api.escuelajs.co/api/v1/products";
        Supplier2Product[] products = restTemplate.exchange(url, HttpMethod.GET, null, Supplier2Product[].class).getBody();
        List<Supplier2Product> productList = List.of(products);
        List<Supplier2Product> productListFiltered = productList.stream()
                .filter(product -> product.getCategory().getSlug().equals(findByCategory.toLowerCase()))
                .map(product -> {
                    double retailPrice = Math.round((product.getPrice() * 1.5) * 100.0) / 100.0;
                    product.setRetailPrice(retailPrice);
                    return product;
                })
                .collect(Collectors.toList());

        return productListFiltered;
    }

    @GetMapping("supplier3")
    public List<Supplier3Product> getSupplier3Products() {

        String url = "https://dummyjson.com/products";
        Supplier3Response response = restTemplate.exchange(url, HttpMethod.GET, null, Supplier3Response.class).getBody();
        return response.getProducts();
    }
}
