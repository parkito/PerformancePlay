package com.example.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyStore;

@Component
public class KeyStoreProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeyStoreProcessor.class);

    @Value("${gateway.id}")
    private String gatewayId;

    @Autowired
    private KeyStoreFactory keyStoreFactory;
    @Autowired
    private VaultClient vaultClient;
    @Autowired
    private KeyStoreConverter keyStoreConverter;

    public KeyStore getKeyStore() {
        return null;
    }


}
