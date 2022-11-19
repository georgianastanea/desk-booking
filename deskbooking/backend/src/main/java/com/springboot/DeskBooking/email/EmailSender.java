package com.springboot.DeskBooking.email;

public interface EmailSender {

    void send(String to, String email);
}
