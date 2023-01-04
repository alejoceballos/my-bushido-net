package com.momo2x.mbdn.graphql.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * This component is an aggregator of the different services properties responsible for the communication settings.
 * <p>
 * Each time a new service that is implemented, a new component wrapping its settings must be created and injected
 * here.
 */
@Getter
@Component
@RequiredArgsConstructor
public class EndpointProperties {

    private final MembersEndpointProperties members;

}
