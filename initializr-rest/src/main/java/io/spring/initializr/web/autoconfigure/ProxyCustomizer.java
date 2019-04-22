package io.spring.initializr.web.autoconfigure;

import io.spring.initializr.metadata.InitializrProperties;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.protocol.HttpContext;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author Fernando Jordan Silva <fernando.jordan@renault.com>
 * @since 1.0.0
 */
public class ProxyCustomizer implements RestTemplateCustomizer {

    private final InitializrProperties initializrProperties;

    public ProxyCustomizer(InitializrProperties initializrProperties) {
        this.initializrProperties = initializrProperties;
    }

    /**
     * Callback to customize a {@link RestTemplate} instance.
     *
     * @param restTemplate
     *         the template to customize
     */
    @Override
    public void customize(RestTemplate restTemplate) {
        if (!initializrProperties.getProxy().isEnabled()) {
            return;
        }
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(initializrProperties.getProxy().getHost(), initializrProperties.getProxy().getPort()),
                new UsernamePasswordCredentials(initializrProperties.getProxy().getUsername(), initializrProperties.getProxy().getPassword()));

        HttpHost proxy = new HttpHost(initializrProperties.getProxy().getHost(), initializrProperties.getProxy().getPort());
        HttpClient httpClient = HttpClientBuilder
                .create()
                .useSystemProperties()
                .setDefaultCredentialsProvider(credsProvider)
                .setProxy(proxy)
                .setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy())
                .setRoutePlanner(new DefaultProxyRoutePlanner(proxy) {
                    @Override
                    public HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
                        return super.determineProxy(target, request, context);
                    }
                }).build();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}
