package com.example.tictactoe.dao;

import com.example.tictactoe.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDao {
    private Map<String, User> users = new HashMap<>();

    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    public User findByUsername(String username) {
        return users.get(username);
    }

    public void updateScore(String username, int newScore) {
        User user = users.get(username);
        if (user != null) {
            user.setScore(newScore);
        }
    }
}

