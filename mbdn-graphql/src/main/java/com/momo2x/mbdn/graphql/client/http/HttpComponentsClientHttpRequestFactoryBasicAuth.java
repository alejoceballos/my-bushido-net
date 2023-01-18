package com.momo2x.mbdn.graphql.client.http;

import org.apache.hc.client5.http.impl.auth.BasicAuthCache;
import org.apache.hc.client5.http.impl.auth.BasicScheme;
import org.apache.hc.client5.http.protocol.HttpClientContext;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.protocol.BasicHttpContext;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;

import java.net.URI;

/**
 * An internet copy & paste of a helper class for authenticating when requesting data to a basic authenticated Rest API.
 * <p>
 * Reference:
 * <a href="https://www.baeldung.com/how-to-use-resttemplate-with-basic-authentication-in-spring">
 * How to use rest template with basic authentication in spring
 * </a>
 */
public class HttpComponentsClientHttpRequestFactoryBasicAuth extends HttpComponentsClientHttpRequestFactory {

    private final HttpHost host;

    public HttpComponentsClientHttpRequestFactoryBasicAuth(final HttpHost host) {
        super();
        this.host = host;
    }

    @Override
    protected HttpContext createHttpContext(@Nullable final HttpMethod httpMethod, @Nullable final URI uri) {
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

