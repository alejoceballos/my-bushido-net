package com.momo2x.mbdn.contacts.mq;

import com.momo2x.mbdn.contacts.exception.ContactsServiceException;

public class QueueCreationException extends ContactsServiceException {

    public QueueCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
