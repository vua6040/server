package com.noteApp.noteApp.controller;

import com.noteApp.noteApp.exception.ResourceNotFoundException;
import com.noteApp.noteApp.model.Users;
import com.noteApp.noteApp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class UsersController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/users/{id}")
    public ResponseEntity<Users> getUser(@PathVariable(value = "id") Integer Id) throws ResourceNotFoundException {
        Users user = usersRepository.findById(Id).orElseThrow(()->new ResourceNotFoundException("not found"));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public Users addUser(@RequestBody Users user) {
        System.out.println("Server Side Created");
        return this.usersRepository.save(user);
    }

    @PutMapping("/users")
    public ResponseEntity<Users> updateUser(@RequestBody Users user) throws  ResourceNotFoundException{
        System.out.println("Server Side Editting" + user.getId());
        Users userCurrent = usersRepository.findById(user.getId()).orElseThrow(()->new ResourceNotFoundException(("not found")));
        userCurrent.setId(user.getId());
        userCurrent.setPassword(user.getPassword());
        userCurrent.setUsername(user.getUsername());
        return ResponseEntity.ok(this.usersRepository.save(userCurrent));
    }

    @DeleteMapping("/users/{Id}")
    public Map<String,Boolean> deleteUser(@PathVariable("Id") Integer Id) throws ResourceNotFoundException {
        System.out.println("Server Side Deleted" + Id);
        Users userCurrent = usersRepository.findById(Id).orElseThrow(()->new ResourceNotFoundException(("not found")));
        this.usersRepository.delete(userCurrent);

        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }

}
