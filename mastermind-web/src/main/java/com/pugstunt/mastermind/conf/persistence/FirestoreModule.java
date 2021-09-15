package com.pugstunt.mastermind.conf.persistence;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.pugstunt.mastermind.store.GameStore;
import com.pugstunt.mastermind.store.firestore.FirestoreGameStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirestoreModule extends AbstractModule {

    static final Logger logger = LoggerFactory.getLogger(FirestoreModule.class);

    @Override
    protected void configure() {
        bind(GameStore.class).to(FirestoreGameStore.class).in(Scopes.SINGLETON);
    }

    @Provides
    @Singleton
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    @Provides
    @Singleton
    public Firestore connection() throws Exception {

        final String projectId = System.getenv("firestore.connection.projectId");
        logger.info("firestore.connection.projectId={}", projectId);

        FirestoreOptions firestoreOptions =
                FirestoreOptions.getDefaultInstance()
                    .toBuilder()
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();

        return firestoreOptions.getService();
    }

}
