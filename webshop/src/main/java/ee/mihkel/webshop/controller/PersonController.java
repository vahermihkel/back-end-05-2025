package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.PersonRole;
import ee.mihkel.webshop.model.AuthCredentials;
import ee.mihkel.webshop.model.AuthToken;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.security.JwtService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    JwtService jwtService;

    @PostMapping("signup")
    public AuthToken signup(@RequestBody Person person) {
        person.setRole(PersonRole.CUSTOMER);
        Person dbPerson = personRepository.save(person);
        return jwtService.generateToken(dbPerson);
    }

    @PostMapping("login")
    public AuthToken login(@RequestBody AuthCredentials authCredentials) {
        Person person = personRepository.findByEmail(authCredentials.getEmail());
        if (person == null) {
            throw new RuntimeException("Email_not_correct");
        }
        if (!person.getPassword().equals(authCredentials.getPassword())) {
            throw new RuntimeException("Password_not_correct");
        }
        System.out.println(person.getEmail());
        log.info(person.getEmail());

        return jwtService.generateToken(person);
    }

    @GetMapping("person/{token}")
    public Person getPerson(@PathVariable String token) {
        return jwtService.parseToken(token);
    }

    @GetMapping("persons")
    public List<Person> getPerson() {
        return personRepository.findAll();
    }

    @PutMapping("person")
    public Person editPerson(@RequestBody Person person) {
        if (person.getId() == null || person.getId() == 0) {
            throw new RuntimeException("ID_must_be_present");
        }
        return personRepository.save(person);
    }

    // KODUS: PersonDTO  firstName, lastName
}
