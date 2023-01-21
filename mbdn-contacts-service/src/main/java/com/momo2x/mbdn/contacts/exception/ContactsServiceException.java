package com.momo2x.mbdn.contacts.exception;

public class ContactsServiceException extends RuntimeException {

    public ContactsServiceException() {
    }

    public ContactsServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
