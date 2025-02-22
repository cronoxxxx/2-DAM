package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Visitor {
    private int Visitor_ID;
    private String Name;
    private String Email;
    private int Tickets;
}
