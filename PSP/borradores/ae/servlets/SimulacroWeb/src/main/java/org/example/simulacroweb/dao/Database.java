package org.example.simulacroweb.dao;

import lombok.Getter;
import org.example.simulacroweb.domain.model.Product;
import org.example.simulacroweb.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Getter
@Component
public class Database {
    private final List<Product> products;
    private final List<User> users;

    public Database() {
        this.products = new ArrayList<>();
        this.users = new ArrayList<>();
    }
}
