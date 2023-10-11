package com.espublico.apirest.controller;

import com.espublico.apirest.UserService;
import com.espublico.apirest.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Order createUser(@RequestBody Order user){
        return userService.createuser(user);
    }

    @GetMapping
    public List<Order> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("{id}")
    public Order searchUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }
    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable("id") Long id){
         userService.deleteUser(id);
    }

}
