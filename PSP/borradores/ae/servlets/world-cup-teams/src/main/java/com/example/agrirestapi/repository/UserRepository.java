package com.example.agrirestapi.repository;

import com.example.agrirestapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();

    public UserRepository() {
        // Inicializar algunos usuarios de prueba
        users.add(new User("admin", "password", "ADMIN"));
        users.add(new User("mexican", "password", "MEXICANO"));
        users.add(new User("colombian", "password", "COLOMBIANO"));
    }

    public Optional<User> findByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
}

