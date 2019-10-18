package com.example.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

@Component
public class VaultClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(VaultClient.class);

    @Autowired
    private RestTemplate restTemplate;

    public String getKeyStoreData(String gatewayId) {

        UriComponents url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("vault.bidstrading.local")
                .port(8200)
                .pathSegment("/bids/app")
                .path("test")
                .path(gatewayId)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("TOKEN", "value");

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        return restTemplate.exchange(url.toUri(), HttpMethod.GET, entity, String.class).getBody();
    }

}
