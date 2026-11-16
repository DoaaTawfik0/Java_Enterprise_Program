package com.doaatawfik.exceptions;

public class RecordAlreadyExist extends RuntimeException {
    public RecordAlreadyExist(String message) {
        super(message);
    }
}
