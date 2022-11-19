package com.springboot.DeskBooking.service;


import com.springboot.DeskBooking.dto.BookingDto;
import com.springboot.DeskBooking.dto.UserDto;
import com.springboot.DeskBooking.entity.AppUser;
import com.springboot.DeskBooking.entity.Booking;
import com.springboot.DeskBooking.exceptions.CrudOperationException;
import com.springboot.DeskBooking.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto addUser(UserDto userDto){
        AppUser user = new ModelMapper().map(userDto,AppUser.class);
        userRepository.save(user);
        userDto.setId(user.getId());
        return userDto;
    }

    public void removeUser(Long id){

         userRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("User does not exist");
        });

        userRepository.deleteById(id);
    }

    public UserDto getUserByID(Long id){
        AppUser user = userRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("User does not exist");
        });
        return new ModelMapper().map(user,UserDto.class);
    }

    public UserDto updateUser(Long id, UserDto newUser){
        AppUser user = userRepository.findById(id).orElseThrow(() -> {
            throw new CrudOperationException("User does not exist");
        });

        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        userRepository.save(user);

        return new ModelMapper().map(user,UserDto.class);
    }

    public List<UserDto> getAllUsers(){

        Iterable<AppUser> iterableUsers = userRepository.findAll();
        List<UserDto> users = new ArrayList<>();

        iterableUsers.forEach(user -> users.add(new ModelMapper().map(user,UserDto.class)));
        return users;

    }
}
