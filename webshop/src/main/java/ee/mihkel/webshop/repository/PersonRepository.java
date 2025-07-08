package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
}
