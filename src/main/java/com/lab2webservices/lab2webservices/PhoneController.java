package com.lab2webservices.lab2webservices;

//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.RepresentationModel;
import org.apache.coyote.Response;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RequestMapping("/api/v1/phones")
@RestController
public class PhoneController {

//    private List<Phone> phoneList = Collections.synchronizedList(new ArrayList<>());
//
//    private static final String template = "Hello, %s!";
//    private final AtomicLong counter = new AtomicLong();
//
//    @GetMapping("/greeting")
//    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
//        return new Greeting(counter.incrementAndGet(), String.format(template, name));
//    }

    private final PhoneDataModelAssembler phoneDataModelAssembler;
    private PhoneRepository repository;

    PhoneController(PhoneRepository repository, PhoneDataModelAssembler phoneDataModelAssembler) {
        this.repository = repository;
        this.phoneDataModelAssembler = phoneDataModelAssembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Phone>> all() {
        return phoneDataModelAssembler.toCollectionModel(repository.findAll());
    }

    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<EntityModel<Phone>> one(@PathVariable Long id) {
        return repository.findById(id)
                .map(phoneDataModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{phoneName:[\\D]+}")
    public ResponseEntity<EntityModel<Phone>> one(@PathVariable String phoneName) {
        return repository.findByPhoneName(phoneName)
                .map(phoneDataModelAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping(value = "/brand/{brandId}")
    public Optional<Phone> one(@PathVariable int brandId) {
        return repository.findById((long) brandId);
    }

    @PostMapping
    ResponseEntity<Phone> newPhone(@RequestBody Phone phone) {
        if(repository.existsPhoneByPhoneName(phone.getPhoneName()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        if(repository.findByPhoneName(phone.getPhoneName()).equals(phone.getPhoneName())) {
//            System.out.println("phone already exists");
//        }
        repository.save(phone);
        return new ResponseEntity<>(phone, HttpStatus.CREATED);
    }

//    public boolean containsName(PhoneRepository phoneRepository, final String name){
//        return phoneRepository.().map(MyObject::getName).filter(name::equals).findFirst().isPresent();
//    }

//    @PostMapping("/api/phones")
//    public ResponseEntity<Phone> createPhone(@RequestBody Phone phone) {
////        log.info("POST create Person " + person);
//        var p = phoneList.add(phone);
////        log.info("Saved to repository " + p);
//        HttpHeaders headers = new HttpHeaders();
////        headers.setLocation(linkTo(PersonsController.class).slash(p.getId()).toUri());
////        headers.add("Location", "/api/persons/" + phone.getId());
//        return new ResponseEntity<>(phone, headers, HttpStatus.CREATED);
//    }

}
