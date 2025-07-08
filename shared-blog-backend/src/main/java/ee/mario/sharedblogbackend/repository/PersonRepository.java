package ee.mario.sharedblogbackend.repository;

import ee.mario.sharedblogbackend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByEmail(String email);
}
