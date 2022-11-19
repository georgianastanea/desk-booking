package com.springboot.DeskBooking.service;


import com.springboot.DeskBooking.entity.AppUser;
import com.springboot.DeskBooking.exceptions.CrudOperationException;
import com.springboot.DeskBooking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AppUser addUser(AppUser user){
        userRepository.save(user);
        return user;
    }

    public void removeUser(Long id){

         userRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("User does not exist");
        });
        userRepository.deleteById(id);
    }

    public AppUser getUserByID(Long id){
        AppUser user = userRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("User does not exist");
        });
        return user;
    }

    public AppUser updateUser(Long id, AppUser newUser){
        AppUser user = userRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("User does not exist");
        });

        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        userRepository.save(user);

        return user;
    }

    public List<AppUser> getAllUsers(){
        return (List<AppUser>) userRepository.findAll();
    }
}
