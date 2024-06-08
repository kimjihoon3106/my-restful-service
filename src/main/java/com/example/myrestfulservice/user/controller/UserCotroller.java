package com.example.myrestfulservice.user.controller;

import com.example.myrestfulservice.user.dao.UserDaoService;
import com.example.myrestfulservice.user.domain.User;
import com.example.myrestfulservice.user.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserCotroller {

    private UserDaoService service;

    public UserCotroller(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = service.findOne(id);
        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        } else {
            return user;
        }
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid  @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        User deleteUser = service.deleteById(id);

        if(deleteUser == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return ResponseEntity.noContent().build();
    }

}
