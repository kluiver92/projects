package com.espublico.apirest;

import com.espublico.apirest.model.Order;
import com.espublico.apirest.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UserService {
    @Autowired
    private OrderRepository userRepository;

    public Order createuser(Order user){
        return userRepository.save(user);
    }

    public Order getUserById(Long id){
        Optional<Order> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }
    public List<Order> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
