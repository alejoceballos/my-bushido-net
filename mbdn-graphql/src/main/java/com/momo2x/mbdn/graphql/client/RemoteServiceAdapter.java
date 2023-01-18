package com.momo2x.mbdn.graphql.client;

import com.momo2x.mbdn.graphql.client.config.RemoteServiceType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
public @interface RemoteServiceAdapter {

    RemoteServiceType value();

}
