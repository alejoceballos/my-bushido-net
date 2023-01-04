package com.momo2x.mbdn.graphql.http;

import org.apache.hc.client5.http.impl.auth.BasicAuthCache;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

/**
 * A complete copy of a helper class for authenticating when requesting data to a basic authenticated Rest API.
 * <p>
 * Reference: https://www.baeldung.com/how-to-use-resttemplate-with-basic-authentication-in-spring
 */
public class HttpComponentsClientHttpRequestFactoryBasicAuth extends HttpComponentsClientHttpRequestFactory {

    private final HttpHost host;

    public HttpComponentsClientHttpRequestFactoryBasicAuth(final HttpHost host) {
        super();
        this.host = host;
    }

    protected HttpContext createHttpContext(final HttpMethod httpMethod, final URI uri) {
        return createHttpContext();
    }

    private HttpContext createHttpContext() {
        var authCache = new BasicAuthCache();

        var basicAuth = new BasicScheme();
        authCache.put(host, basicAuth);

        var localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);

        return localContext;
    }
}

