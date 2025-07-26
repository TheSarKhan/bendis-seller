package com.sarkhan.backend.bendisseller.exception;

public class NotEnoughQuantityException extends Exception{
    public NotEnoughQuantityException(String message) {
        super(message);
    }
}
