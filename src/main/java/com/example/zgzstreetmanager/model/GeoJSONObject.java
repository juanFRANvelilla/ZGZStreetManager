package com.example.zgzstreetmanager.model;

import lombok.Getter;
import java.util.List;

@Getter
public class GeoJSONObject {
    List<Feature> features;

    @Getter
    public static class Feature {
        String type;
        Properties properties;
        Geometry geometry;
        int id;

        @Getter
        public static class Geometry {
            String type;
            List<List<List<Double>>> coordinates;
        }

        @Getter
        public static class Properties {
            String name;
        }
    }
}
