package com.example.hospitalcrud.dao.xml;

import com.example.hospitalcrud.dao.mappers.LocalDateAdapter;
import lombok.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MedRecordXML {
    @XmlElement
    private int id;
    @XmlElement
    private int idPatient;
    @XmlElement
    private int doctor;
    @XmlElement
    private String diagnosis;
    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;
    @XmlElementWrapper(name = "medications")
    @XmlElement(name = "medication")
    private List<String> medications;

    // Getters, setters, and builder methods
}