package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // JpaRepository: .findAll()  .findById(Long)   .save(Order)
    // List<Order>    Order

    List<Order> findByPersonId(Long id);
    List<Order> findByPerson_Email(String email);

}
