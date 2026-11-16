package com.doaatawfik.models;

import com.doaatawfik.exceptions.RecordAlreadyExist;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


// to add vehicles & trips
// vehicle => serialID, vehicle itself
// trip => tripID, trip itself
public class DataStore<K, V> {

    private final Map<K, V> store = new HashMap<>();

    public void addData(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("key or value cannot be null");
        }


        if (store.containsKey(key)) {
            throw new RecordAlreadyExist("Record already exists with key: " + key);
        } else {
            store.put(key, value);
        }
    }

    public Optional<V> getData(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        return Optional.ofNullable(store.get(key));
    }

}
