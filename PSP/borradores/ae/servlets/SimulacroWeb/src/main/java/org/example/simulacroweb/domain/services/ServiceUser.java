package org.example.simulacroweb.domain.services;

import org.example.simulacroweb.dao.DaoUser;
import org.example.simulacroweb.domain.model.User;
import org.springframework.stereotype.Service;

@Service
public class ServiceUser {
    private DaoUser daoUser;

    public ServiceUser(DaoUser daoUser) {
        this.daoUser = daoUser;
    }

    public boolean register (User user){
        return daoUser.register(user);
    }

    public boolean authenticateUser(String username, String password) {
        User credential = daoUser.findByUsername(username);
        return credential != null  && credential.getPassword().equals(password);
    }
}
