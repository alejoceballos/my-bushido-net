package com.momo2x.mbdn.contacts.mq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContactListener {

    @JmsListener(destination = "${mq.queue.contact.save}")
    public void onMessage(@NonNull String message) {
        log.warn("==========> Message: %s".formatted(message));
    }

}
