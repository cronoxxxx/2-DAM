package org.example.webexamenadriansaavedra.dao;

import lombok.Data;
import lombok.Getter;
import org.example.webexamenadriansaavedra.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class Database {
    private final List<User> users;

    public Database(List<User> users) {
        this.users = users;
    }
}
