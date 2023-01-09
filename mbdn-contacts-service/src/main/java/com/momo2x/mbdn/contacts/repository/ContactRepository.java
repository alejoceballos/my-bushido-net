package com.momo2x.mbdn.contacts.repository;

import com.momo2x.mbdn.contacts.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
