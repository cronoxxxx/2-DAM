package org.example.mazmorrafx_adriansaavedra.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Room {
    @XmlAttribute
    private String id;
    @XmlElement
    private String description;
    @XmlElement(name = "door")
    private List<Door> doors;
}