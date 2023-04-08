package com.example.javacursordtolombokmodelmapperhw9.exeptions;

public class ShopNotFound extends RuntimeException{
    public ShopNotFound(String message) {
        super(message);
    }
}