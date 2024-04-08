package com.dh.ClinicMVC.exception;


//creamos nuestra propia excepción y la extendemos
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
