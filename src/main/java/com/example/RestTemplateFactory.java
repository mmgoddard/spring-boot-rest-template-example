package com.example;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateFactory.class);

    @Value("${connection.timeout}")
    private int timeout;
    
    private ClientHttpRequestFactory httpRequestFactory;

    private RestTemplateFactory() {}

    /**
     * Builds and configures the RestTemplate object for consuming a External Web Service/API
     *
     * @param username the username used to connect to the External Service
     * @param password the password used to connect to the External Service
     * @return the configured RestTemplate
     */
    public RestTemplate create(final String username, final String password) {

        LOGGER.trace("Build REST template | Username: '{}' | Password: '{}'", username, password);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        HttpClientBuilder clientBuilder = HttpClientBuilder.create();
        clientBuilder.setDefaultCredentialsProvider(credentialsProvider);

        HttpClient httpClient = clientBuilder.build();

        ClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        ((HttpComponentsClientHttpRequestFactory) httpRequestFactory).setReadTimeout(timeout);
        ((HttpComponentsClientHttpRequestFactory) httpRequestFactory).setConnectTimeout(timeout);
        ((HttpComponentsClientHttpRequestFactory) httpRequestFactory).setConnectionRequestTimeout(timeout);

        return new RestTemplate(httpRequestFactory);
    }
}