package com.example.agrirestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crop {
    private Long id;
    private String name;
    private int energyValue;
    private String characteristics;
    private List<LandCrop> landCrops = new ArrayList<>();
}

