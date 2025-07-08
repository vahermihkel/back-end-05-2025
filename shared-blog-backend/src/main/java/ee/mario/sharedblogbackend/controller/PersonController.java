package ee.mario.sharedblogbackend.controller;

import ee.mario.sharedblogbackend.dto.PersonDTO;
import ee.mario.sharedblogbackend.entity.AccountType;
import ee.mario.sharedblogbackend.entity.Person;
import ee.mario.sharedblogbackend.entity.Post;
import ee.mario.sharedblogbackend.model.AuthToken;
import ee.mario.sharedblogbackend.model.EmailPassword;
import ee.mario.sharedblogbackend.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")

public class PersonController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PersonRepository personRepository;

    @PostMapping("signup")
    public Person signup(@RequestBody Person person) { // TODO: AuthToken tagasi
        AuthToken authToken = new AuthToken();
        authToken.setExpiration(new Date());
        authToken.setToken("12345abcde");
        if(person.getEmail() == null || person.getEmail().isEmpty()) {
            throw new RuntimeException("email_missing");
        }
        if(person.getName() == null || person.getName().isEmpty()) {
            throw new RuntimeException("name_missing");
        }
        if(person.getPassword() == null || person.getPassword().isEmpty()) {
            throw new RuntimeException("password_missing");
        }
        if(person.getPassword().length() < 7) {
            throw new RuntimeException("password_too_short");
        }
        person.setAccountType(AccountType.WRITER);
        return personRepository.save(person);
//        return authToken;
    }

    @PostMapping("login")
    public Person login(@RequestBody EmailPassword emailPassword) { // TODO: AuthToken tagasi
        AuthToken authToken = new AuthToken();
        authToken.setExpiration(new Date());
        authToken.setToken("12345abcde");
        if(emailPassword.getEmail() == null || emailPassword.getEmail().isEmpty()) {
            throw new RuntimeException("email_missing");
        }
        if(emailPassword.getPassword() == null || emailPassword.getPassword().isEmpty()) {
            throw new RuntimeException("password_missing");
        }
        Person person = personRepository.findByEmail(emailPassword.getEmail());
        if(person == null) {
            throw new RuntimeException("email_not_found");
        }
        if(!person.getPassword().equals(emailPassword.getPassword())) {
            throw new RuntimeException("password_incorrect");
        }
        return person;
//        return authToken;
    }

    @GetMapping("public-users")
    public ResponseEntity<Page<PersonDTO>> getPublicUsers(Pageable pageable) {
        Page<Person> people = personRepository.findAll(pageable);
        Page<PersonDTO> personDTOs = people.map(person -> modelMapper.map(person, PersonDTO.class));
        return ResponseEntity.ok().body(personDTOs);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<Person> getUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(personRepository.findById(id).orElseThrow());
    }

    @PutMapping("user")
    public ResponseEntity<Person> editUser(@RequestBody Person person) {
        if (person.getId() == null) {
            throw new RuntimeException("Cannot add without ID!");
        }
        if (person.getPassword() == null || person.getPassword().isEmpty()) {
            throw new RuntimeException("password_missing");
        }
        personRepository.save(person);
        return ResponseEntity.ok().body(personRepository.findById(person.getId()).orElseThrow());
    }



}
