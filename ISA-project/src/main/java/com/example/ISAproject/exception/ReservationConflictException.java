package com.example.ISAproject.exception;

public class ReservationConflictException extends RuntimeException {
	public ReservationConflictException(String message) {
        super(message);
    }
}
