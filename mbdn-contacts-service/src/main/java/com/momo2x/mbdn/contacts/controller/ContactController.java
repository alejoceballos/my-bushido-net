package com.momo2x.mbdn.contacts.controller;

import com.momo2x.mbdn.contacts.mapping.ContactDto;
import com.momo2x.mbdn.contacts.mapping.ContactMapper;
import com.momo2x.mbdn.contacts.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/contacts")
@RequiredArgsConstructor
class ContactController {

    private final ContactService service;

    private final ContactMapper mapper;

    @GetMapping(value = "/{id}")
    public ContactDto getById(@PathVariable final UUID id) {
        return mapper.toContactDto(service.findById(id));
    }

}