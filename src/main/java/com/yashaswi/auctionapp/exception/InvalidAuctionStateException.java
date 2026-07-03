package com.yashaswi.auctionapp.exception;

public class InvalidAuctionStateException extends RuntimeException {
    public InvalidAuctionStateException(String message) {
        super(message);
    }
}
