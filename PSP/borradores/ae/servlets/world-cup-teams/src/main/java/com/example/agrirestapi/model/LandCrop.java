package com.example.agrirestapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandCrop {
    private Long id;
    private Land land;
    private Crop crop;
}

