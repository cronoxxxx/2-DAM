package org.example.dao.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
public class Credential {
    private ObjectId id;
    private String username;
    private String password;
    private ObjectId patient;
    private ObjectId doctor;
}

