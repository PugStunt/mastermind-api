package com.pugstunt.mastermind.store.firestore;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.inject.Inject;
import com.pugstunt.mastermind.core.entity.GameEntry;
import com.pugstunt.mastermind.store.GameStore;
import java.util.Optional;

public class FirestoreGameStore implements GameStore {

    private static final String GAMES_COLLECTION = "games";
    private final Firestore firestore;
    private final ObjectMapper mapper;

    @Inject
    public FirestoreGameStore(Firestore firestore, ObjectMapper mapper) {
        this.firestore = firestore;
        this.mapper = mapper;
    }

    @lombok.SneakyThrows
    @Override
    public void save(GameEntry game) {
        DocumentReference docRef =
                firestore.collection(GAMES_COLLECTION).document(game.getGameKey());
        docRef.set(game).get();
    }

    @lombok.SneakyThrows
    @Override
    public void remove(String gameKey) {
        DocumentReference docRef =
                firestore.collection(GAMES_COLLECTION).document(gameKey);
        docRef.delete().get();
    }

    @lombok.SneakyThrows
    @Override
    public Optional<GameEntry> findByKey(String gameKey) {

        ApiFuture<DocumentSnapshot> future =
                firestore.collection(GAMES_COLLECTION).document(gameKey).get();

        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return Optional.of(mapper.convertValue(document.getData(), GameEntry.class));
        } else {
            return Optional.empty();
        }
    }

}
