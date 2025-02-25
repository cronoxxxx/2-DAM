package org.example.restexamenadriansaavedra.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Land {
    private Long id;
    private String name;
    private String latitude;
    private String longitude;
    private double squareMeters;
    private List<LandCrop> landCrops = new ArrayList<>();
}

