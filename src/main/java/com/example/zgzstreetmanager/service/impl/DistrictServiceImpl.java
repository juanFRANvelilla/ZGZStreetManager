package com.example.zgzstreetmanager.service.impl;

import com.example.zgzstreetmanager.model.DistrictEnum;
import com.example.zgzstreetmanager.model.GeoJSONObject;
import com.example.zgzstreetmanager.service.DistrictService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;

import java.awt.geom.Path2D;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DistrictServiceImpl implements DistrictService {

    private static final String DISTRICTJSON_PATHS = "src/main/java/com/example/zgzstreetmanager/jsonData/DistrictJSON";

    private  static final Map<String, Path2D> DISTRICT_POLYGONS = new HashMap<>();


    public static List<GeoJSONObject.Feature> extractFeatures(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<GeoJSONObject.Feature> features = new ArrayList<>();

        try {
            JsonNode root = objectMapper.readTree(jsonData);

            JsonNode featuresList = root.path("features");

            features = objectMapper.convertValue(featuresList, new TypeReference<>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        return features;
    }


    private static void createDistrictPolygon(GeoJSONObject.Feature feature) {
        Path2D polygon = new Path2D.Double();
        List<List<List<Double>>> coordinates = feature.getGeometry().getCoordinates();
        List<List<Double>> coords = coordinates.get(0);

        // Mover a la primera coordenada
        polygon.moveTo(coords.get(0).get(1), coords.get(0).get(0));

        // Dibujar líneas a las siguientes coordenadas
        for (int i = 1; i < coords.size(); i++) {
            polygon.lineTo(coords.get(i).get(1), coords.get(i).get(0));
        }
        polygon.closePath();

        DISTRICT_POLYGONS.put(feature.getProperties().getName(), polygon);
    }



    private static void defineDistricts() {

        try {
            String json = new String(Files.readAllBytes(Path.of(DISTRICTJSON_PATHS)));

            //obtener las features
            List<GeoJSONObject.Feature> featureList = extractFeatures(json);

            //crear polígonos para cada distrito
            for (GeoJSONObject.Feature feature : featureList) {
                createDistrictPolygon(feature);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    static {
        defineDistricts();
    }

    @Override
    public Path2D getDistrictPolygon(String districtName) {
        return DISTRICT_POLYGONS.get(districtName);
    }

    @Override
    public boolean isWithinDistrict(String districtName, double latitude, double longitude) {
        Path2D polygon = DISTRICT_POLYGONS.get(districtName);
        if (polygon == null) {
            throw new IllegalArgumentException("The district " + districtName + " does not exist.");
        }
        return polygon.contains(latitude, longitude);
    }

    @Override
    public DistrictEnum getDistrictForCoordinates(double latitude, double longitude) {
        for (Map.Entry<String, Path2D> entry : DISTRICT_POLYGONS.entrySet()) {
            String districtName = entry.getKey();
            Path2D polygon = entry.getValue();
            if (polygon.contains(latitude, longitude)) {
                return DistrictEnum.valueOf(districtName);
            }
        }
        return DistrictEnum.UNDEFINED;
    }
}
