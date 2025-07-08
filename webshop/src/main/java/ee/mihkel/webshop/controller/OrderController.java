package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.model.EveryPayBody;
import ee.mihkel.webshop.model.EveryPayResponse;
import ee.mihkel.webshop.model.ParcelMachine;
import ee.mihkel.webshop.model.Supplier3Response;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.security.JwtService;
import ee.mihkel.webshop.service.EmailService;
import ee.mihkel.webshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    JwtService jwtService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    EmailService emailService;

    @GetMapping("orders/{token}")
    public List<Order> getOrders(@PathVariable String token) {
        String personEmail = jwtService.parseToken(token).getEmail();
        return orderRepository.findByPerson_Email(personEmail);
    }

//    @PostMapping("orders")
//    public Order addOrder(@RequestBody List<Product> products) {
//        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
//        return orderService.saveOrder(personId, products);
//    }

    @GetMapping("parcelmachines")
    public List<ParcelMachine> getParcelMachines(@RequestParam String country) {
        String url = "https://dummyjson.com/products";
        ParcelMachine[] response = restTemplate.exchange(url, HttpMethod.GET, null, ParcelMachine[].class).getBody();
        List<ParcelMachine> parcelMachines = List.of(response);
        return parcelMachines.stream().filter(e -> e.getA0_NAME().equals(country)).collect(Collectors.toList());
    }

    @PostMapping("payment")
    public String payment(@RequestBody List<Product> products) {
        Long personId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        Long orderId = orderService.saveOrder(personId, products).getId();
        return orderService.getPaymentLink(products, orderId);
    }

    @GetMapping("email")
    public String sendEmail() {
        emailService.sendEmail("","Pealkiri", "Sisu");
        return "Saadetud";
    }
}
