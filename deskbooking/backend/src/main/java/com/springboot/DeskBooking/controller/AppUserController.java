package com.springboot.DeskBooking.controller;

import com.springboot.DeskBooking.dto.BookingDto;
import com.springboot.DeskBooking.user.AppUser;
import com.springboot.DeskBooking.user.AppUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class AppUserController {

    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    private AppUserService appUserService;

    @GetMapping("/active")
    public ResponseEntity<List<AppUser>> getActiveUsers(){
        return ResponseEntity.ok(appUserService.activeUsers());
    }
}
