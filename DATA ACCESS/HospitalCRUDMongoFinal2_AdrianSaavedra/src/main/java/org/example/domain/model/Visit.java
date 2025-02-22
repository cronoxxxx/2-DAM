package org.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.dao.mappers.xml.LocalDateAdapter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Visit {
    @XmlElement(name = "AnimalID")
    private int AnimalID;
    @XmlElement(name = "VisitorID")
    private int VisitorID;
    @XmlElement(name = "VisitDate")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate VisitDate;

    public Visit() {
    }
}
