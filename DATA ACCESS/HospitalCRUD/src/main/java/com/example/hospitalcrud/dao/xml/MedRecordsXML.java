package com.example.hospitalcrud.dao.xml;

import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@XmlRootElement(name = "medRecords")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedRecordsXML {
    @XmlElement(name = "medRecord")
    private List<MedRecordXML> medRecords;

    // Getters and setters
}