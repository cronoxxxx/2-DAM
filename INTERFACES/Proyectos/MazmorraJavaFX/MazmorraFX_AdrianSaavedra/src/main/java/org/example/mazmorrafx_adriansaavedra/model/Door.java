package org.example.mazmorrafx_adriansaavedra.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Door {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String dest;
}
