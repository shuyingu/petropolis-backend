package com.capstone.petropolis.controller;

import com.capstone.petropolis.entity.UserEntity;
import com.capstone.petropolis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserEntity> updateUser(@PathVariable long id, @RequestBody UserEntity user){
        UserEntity existingUser = userRepository.findById(id).orElse(null);
        if(existingUser == null){
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        UserEntity updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id){
        userRepository.deleteById(id);
    }
}
