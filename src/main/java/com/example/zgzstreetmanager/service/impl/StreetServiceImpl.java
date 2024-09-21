package com.example.zgzstreetmanager.service.impl;

import com.example.zgzstreetmanager.model.Street;
import com.example.zgzstreetmanager.repository.StreetRepository;
import com.example.zgzstreetmanager.service.GoogleMapsService;
import com.example.zgzstreetmanager.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class StreetServiceImpl implements StreetService {


    @Autowired
    private GoogleMapsService googleMapsService;

    @Autowired
    private StreetRepository streetRepository;



    @Override
    public List<String> saveStreets(List<String> listStringStreets) {
        List<String> streetsAdded = new ArrayList<>();
        for(String streetString : listStringStreets){
            Street street = googleMapsService.getStreet(streetString);
            streetRepository.save(street);
            streetsAdded.add(streetString);
        }
        return streetsAdded;
    }
}
