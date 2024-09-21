package com.example.zgzstreetmanager.service.impl;

import com.example.zgzstreetmanager.model.DistrictEnum;
import com.example.zgzstreetmanager.model.Street;
import com.example.zgzstreetmanager.service.DistrictService;
import com.example.zgzstreetmanager.service.JsonConverterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JsonConverterServiceImpl implements JsonConverterService {

    @Autowired
    private DistrictService districtService;

    @Override
    public Street convertJsonToStreet(String jsonData, Street street) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode root = null;
        try {
            root = objectMapper.readTree(jsonData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode locationNode = root.path("results")
                .path(0);

        double lat = locationNode.path("geometry").path("location").path("lat").asDouble();
        double lng = locationNode.path("geometry").path("location").path("lng").asDouble();


        DistrictEnum districtEnum;

        if(lat == 41.6474339 && lng == -0.8861451){
            System.out.println("Calle no encontrada correctamente");
            districtEnum = DistrictEnum.UNDEFINED;
        } else {
            districtEnum = districtService.getDistrictForCoordinates(lat, lng);
        }
        street.setDistrict(districtEnum);
        street.setLatitude(lat);
        street.setLongitude(lng);

        return street;
    }
}
