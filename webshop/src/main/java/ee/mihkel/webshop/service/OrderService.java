package ee.mihkel.webshop.service;

import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.model.EveryPayBody;
import ee.mihkel.webshop.model.EveryPayResponse;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${everypay.url}")
    private String everyPayUrl;

    @Value("${everypay.customerUrl}")
    private String customerUrl;

    @Value("${everypay.username}")
    private String username;

    @Value("${everypay.password}")
    private String password;

    public Order saveOrder(Long personId, List<Product> products) {
        Order order = new Order(); // {id: 0, created: null, totalSum: 0, product: null, person: null}
        order.setCreated(new Date()); // {id: 0, created: 2025-...., totalSum: 0, product: null, person: null}
        order.setProducts(products); // {id: 0, created: 2025-...., totalSum: 0, product: [{},{},{}], person: null}
        order.setPerson(personRepository.findById(personId).orElseThrow());// {id: 0, created: 2025-...., totalSum: 0, product: [{},{},{}], person: {}}
        order.setTotalSum(calculateTotalSum(products));// {id: 0, created: 2025-...., totalSum: 123, product: [{},{},{}], person: {}}
        return orderRepository.save(order); // {id: 123, created: 2025-...., totalSum: 123, product: [{},{},{}], person: {}}
    }

    public double calculateTotalSum(List<Product> products) {
        double sum = 0;
        for (Product product: products) {
            Product dbProduct = productRepository.findById(product.getId()).orElseThrow();
            sum += dbProduct.getPrice();
        }
        return sum;
    }

    public String getPaymentLink(List<Product> products, Long orderId) {
            String url = everyPayUrl;
            EveryPayBody body = new EveryPayBody();
            body.setAccount_name("EUR3D1");
            body.setNonce("31sad" + Math.random() * 999999 + ZonedDateTime.now());
            body.setTimestamp(ZonedDateTime.now().toString());
            body.setAmount(calculateTotalSum(products));
            body.setOrder_reference("ab" + orderId);
            body.setCustomer_url(customerUrl);
            body.setApi_username(username);

            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<EveryPayBody> entity = new HttpEntity<>(body, headers);
            EveryPayResponse response = restTemplate.exchange(url, HttpMethod.POST, entity, EveryPayResponse.class).getBody();
            return response.getPayment_link();
    }
}
