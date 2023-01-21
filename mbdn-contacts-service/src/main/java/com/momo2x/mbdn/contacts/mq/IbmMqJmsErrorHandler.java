package com.momo2x.mbdn.contacts.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
public class IbmMqJmsErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        log.warn("==========> Message: %s".formatted(t.getMessage()));
    }
}
