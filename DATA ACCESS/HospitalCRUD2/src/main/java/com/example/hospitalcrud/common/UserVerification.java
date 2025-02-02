package com.example.hospitalcrud.common;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserVerification {
    private final JsonConverter jsonConverter;

    public UserVerification(JsonConverter jsonConverter) {
        this.jsonConverter = jsonConverter;
    }

    public FetchType getCurrentUser() {
        List<FetchType> fetchTypes = jsonConverter.loadFetchTypes();
        if (fetchTypes != null && !fetchTypes.isEmpty()) {
            return fetchTypes.get(0);
        }
        return null;
    }
}
