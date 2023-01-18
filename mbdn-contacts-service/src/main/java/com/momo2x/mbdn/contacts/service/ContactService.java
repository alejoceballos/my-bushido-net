package com.momo2x.mbdn.contacts.service;

import com.momo2x.mbdn.contacts.exception.ContactNotFoundException;
import com.momo2x.mbdn.contacts.model.Contact;
import com.momo2x.mbdn.contacts.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repository;

    public Contact findById(final UUID id) {
        return repository.findById(id).orElseThrow(ContactNotFoundException::new);
    }

}

