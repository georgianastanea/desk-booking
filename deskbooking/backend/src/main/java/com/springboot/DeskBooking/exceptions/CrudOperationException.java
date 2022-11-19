package com.springboot.DeskBooking.exceptions;

public class CrudOperationException extends RuntimeException{
    public CrudOperationException(String message){
        super(message);
    }
}
