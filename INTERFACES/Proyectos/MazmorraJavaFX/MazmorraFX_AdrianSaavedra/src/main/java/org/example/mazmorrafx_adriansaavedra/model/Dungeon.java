package org.example.mazmorrafx_adriansaavedra.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "dungeon")
@XmlAccessorType(XmlAccessType.FIELD)
public class Dungeon {
    @XmlElement(name = "room")
    private List<Room> rooms;
}

