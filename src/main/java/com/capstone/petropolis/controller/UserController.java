package com.capstone.petropolis.controller;

import com.capstone.petropolis.entity.UserEntity;
import com.capstone.petropolis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping("")
    public UserEntity createUser(@RequestBody UserEntity user){
        return  userRepository.save(user);
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable long id){
        return userRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public UserEntity updateUser(@PathVariable int id, @PathVariable UserEntity user){
        user.setId(id);
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        userRepository.deleteById(id);
    }
}
