package com.example.zgzstreetmanager.model;

import lombok.Getter;

@Getter
public class GeoJSONObject {
    Geometry geometry;

    @Getter
    public static class Geometry {
        double[][][] coordinates;
        String type;
    }
}
