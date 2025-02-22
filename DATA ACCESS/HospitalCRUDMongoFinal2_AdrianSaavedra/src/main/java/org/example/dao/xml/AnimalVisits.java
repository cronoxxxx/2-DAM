package org.example.dao.xml;

import lombok.Data;
import org.example.domain.model.Visit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@Data
@XmlRootElement(name = "AnimalVisits")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalVisits {
    @XmlElement(name = "Visit")
    List<Visit> AnimalVisits;

    public AnimalVisits(List<Visit> animalVisits) {
        AnimalVisits = animalVisits;
    }

    public AnimalVisits() {
    }
}
