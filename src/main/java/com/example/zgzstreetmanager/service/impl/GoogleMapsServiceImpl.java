package com.example.zgzstreetmanager.service.impl;

import com.example.zgzstreetmanager.model.Street;
import com.example.zgzstreetmanager.service.GoogleMapsService;
import com.example.zgzstreetmanager.service.JsonConverterService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;

@Service
public class GoogleMapsServiceImpl implements GoogleMapsService {
    public static final String CONFIG_FILE = "src/main/java/com/example/zgzstreetmanager/config/ApiKey.json";
    private final RestTemplate restTemplate;

    @Autowired
    private JsonConverterService jsonConverterService;

    public GoogleMapsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Street getStreet(String streetName) {

        if(streetName.equals("Calle Conde De Aranda")){
            System.out.println("tamente");
        }
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(new File(CONFIG_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String apiKey = root.path("API_KEY_GOOGLE_MAPS").asText();
        String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";
        String url = baseUrl +
                "?address=" + streetName +
                "&components=locality:Zaragoza|country:ES" +
                "&key=" + apiKey;

        try {
            //obtener json con las coordenadas
            String jsonData = restTemplate.getForObject(url, String.class);

            Street street = Street.builder()
                    .name(streetName)
                    .build();

            //convertir json a objeto
            return jsonConverterService.convertJsonToStreet(jsonData, street);


        } catch (RestClientException e) {
            System.err.println("Error al hacer la llamada a la API: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            // Maneja cualquier otro tipo de error no previsto
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
