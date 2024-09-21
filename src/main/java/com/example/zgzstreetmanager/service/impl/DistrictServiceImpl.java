package com.example.zgzstreetmanager.service.impl;

import com.example.zgzstreetmanager.model.DistrictEnum;
import com.example.zgzstreetmanager.service.DistrictService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.awt.geom.Path2D;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DistrictServiceImpl implements DistrictService {
//    private static final String ACTURJSON = "src/main/java/com/example/zgzstreetmanager/jsonData/ActurDistrictJSON";
//    private static final String RABALJSON = "src/main/java/com/example/zgzstreetmanager/jsonData/RabalJSON";
    private static final List<String> JSON_PATHS = List.of(
            "src/main/java/com/example/zgzstreetmanager/jsonData/ActurDistrictJSON",
            "src/main/java/com/example/zgzstreetmanager/jsonData/RabalJSON"
    );

    private  static final Map<String, Path2D> DISTRICT_POLYGONS = new HashMap<>();



    public static List<List<Double>> extractCoordinates(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root;

        try {
            root = objectMapper.readTree(jsonData);

            JsonNode coordinatesNode = root.path("features").path(0).path("geometry").path("coordinates").path(0);

            List<List<Double>> coords = new ArrayList<>();
            for (int i = 0; i < coordinatesNode.size(); i++) {
                JsonNode coordinatePair = coordinatesNode.path(i);
                List<Double> coordinate = new ArrayList<>();
                coordinate.add(coordinatePair.path(0).asDouble());
                coordinate.add(coordinatePair.path(1).asDouble());
                coords.add(coordinate);
            }
            return coords;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DistrictEnum extractDistrictEnum(String jsonData){
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root;

        try {
            root = objectMapper.readTree(jsonData);

            JsonNode districtNode = root.path("features").path(0).path("name");
            return DistrictEnum.valueOf(districtNode.asText());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //            Path2D polygon = new Path2D.Double();
//            polygon.moveTo(41.659406501547835, -0.8816846325500194);
//            polygon.lineTo(41.667257606264286, -0.8803551565459031);
//            polygon.lineTo(41.67688083416155, -0.8802018608488993);
//            polygon.lineTo(41.67952625554304, -0.8780860825566492);
//            polygon.lineTo(41.67973784395028, -0.865973768300222);
//            polygon.lineTo(41.67816518951233, -0.8444517787498569);
//            polygon.lineTo(41.68271892106762, -0.8462831025178446);
//            polygon.lineTo(41.68610331619357, -0.843810680292421);
//            polygon.lineTo(41.69186263168004, -0.8587935786136711);
//            polygon.lineTo(41.68927240561581, -0.8638458535109237);
//            polygon.lineTo(41.6929739933079, -0.8697403812373068);
//            polygon.lineTo(41.69002733578236, -0.8869319691426085);
//            polygon.lineTo(41.68787204768978, -0.8864956554945138);
//            polygon.lineTo(41.687731253349625, -0.8907247923686441);
//            polygon.lineTo(41.68962222467559, -0.9005128971057559);
//            polygon.lineTo(41.680437971610274, -0.8994972084822734);
//            polygon.lineTo(41.6745974721606, -0.9051884289043528);
//            polygon.lineTo(41.6716374830838, -0.9160759630670725);
//            polygon.lineTo(41.66613511871, -0.9201631887612223);
//            polygon.lineTo(41.666496454995325, -0.908432797139568);
//            polygon.lineTo(41.66779435154015, -0.902474018715111);
//            polygon.lineTo(41.66680380087976, -0.8964694704026783);
//            polygon.lineTo(41.66129928997009, -0.8889317049963097);
//            polygon.closePath();



    private static void defineActurReyFernando() {

        for(String path : JSON_PATHS){
            try {
                String json = new String(Files.readAllBytes(Paths.get(path)));

                Path2D polygon = new Path2D.Double();
                List<List<Double>> coords = extractCoordinates(json);
                DistrictEnum districtEnum = extractDistrictEnum(json);


                // Mover a la primera coordenada
                polygon.moveTo(coords.get(0).get(1), coords.get(0).get(0));

                // Dibujar líneas a las siguientes coordenadas
                for (int i = 1; i < coords.size(); i++) {
                    polygon.lineTo(coords.get(i).get(1), coords.get(i).get(0));
                }
                polygon.closePath();

                DISTRICT_POLYGONS.put(districtEnum.toString(), polygon);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }



//    private static void defineElRabal() {
//        Path2D acturReyFernando = new Path2D.Double();
//        acturReyFernando.moveTo(41.6769, -0.8947);
//        acturReyFernando.lineTo(41.6795, -0.8860);
//        acturReyFernando.lineTo(41.6830, -0.8702);
//        acturReyFernando.lineTo(41.6814, -0.8638);
//        acturReyFernando.lineTo(41.6738, -0.8701);
//        acturReyFernando.closePath();
//        DISTRICT_POLYGONS.put(DistrictEnum.EL_RABAL.toString(), acturReyFernando);
//    }
//
//    private static void defineCentro() {
//        Path2D centro = new Path2D.Double();
//        centro.moveTo(41.6488, -0.8891); // Example coordinates
//        centro.lineTo(41.6466, -0.8801);
//        centro.lineTo(41.6433, -0.8781);
//        centro.lineTo(41.6404, -0.8823);
//        centro.lineTo(41.6457, -0.8886);
//        centro.closePath();
//        DISTRICT_POLYGONS.put("Centro", centro);
//    }
//
//    private static void defineDelicias() {
//        Path2D delicias = new Path2D.Double();
//        delicias.moveTo(41.6554, -0.9102); // Example coordinates
//        delicias.lineTo(41.6511, -0.9008);
//        delicias.lineTo(41.6498, -0.8943);
//        delicias.lineTo(41.6531, -0.8847);
//        delicias.lineTo(41.6573, -0.8950);
//        delicias.closePath();
//        DISTRICT_POLYGONS.put("Delicias", delicias);
//    }
//
//    private static void defineTorrero() {
//        Path2D torrero = new Path2D.Double();
//        torrero.moveTo(41.6284, -0.8891); // Example coordinates
//        torrero.lineTo(41.6256, -0.8834);
//        torrero.lineTo(41.6232, -0.8791);
//        torrero.lineTo(41.6220, -0.8865);
//        torrero.lineTo(41.6265, -0.8895);
//        torrero.closePath();
//        DISTRICT_POLYGONS.put("Torrero", torrero);
//    }
//
//    private static void defineLasFuentes() {
//        Path2D lasFuentes = new Path2D.Double();
//        lasFuentes.moveTo(41.6532, -0.8632); // Example coordinates
//        lasFuentes.lineTo(41.6487, -0.8600);
//        lasFuentes.lineTo(41.6462, -0.8554);
//        lasFuentes.lineTo(41.6473, -0.8490);
//        lasFuentes.lineTo(41.6521, -0.8502);
//        lasFuentes.closePath();
//        DISTRICT_POLYGONS.put("Las Fuentes", lasFuentes);
//    }

    static {
        defineActurReyFernando();
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
