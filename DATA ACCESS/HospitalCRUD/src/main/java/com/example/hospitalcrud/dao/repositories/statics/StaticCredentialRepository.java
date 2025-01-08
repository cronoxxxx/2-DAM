package com.example.hospitalcrud.dao.repositories.statics;

import com.example.hospitalcrud.dao.model.Credential;
import com.example.hospitalcrud.dao.repositories.CredentialRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Profile("files")
@Repository
public class StaticCredentialRepository implements CredentialRepository {
    private final List<Credential> credentials = new ArrayList<>();

    public Credential get(Credential credential) {
        credentials.add(new Credential("root", "root"));
        return credentials.stream().filter(c -> c.getUserName().equals(credential.getUserName()) &&
                c.getPassword().equals(credential.getPassword())).findFirst().orElse(null);
    }

}
