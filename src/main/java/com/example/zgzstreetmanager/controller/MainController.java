package com.example.zgzstreetmanager.controller;

import com.example.zgzstreetmanager.service.DistrictService;
import com.example.zgzstreetmanager.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.geom.Path2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    @Autowired
    private StreetService streetService;

    @Autowired
    private DistrictService districtService;

    @PostMapping("/uploadTxtFile")
    public List<String> uploadTxtFile(@RequestParam("file") MultipartFile file) throws IOException {
        List<String> streets = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                streets.add(line);
            }
        }
        return streetService.saveStreets(streets);
    }
}
