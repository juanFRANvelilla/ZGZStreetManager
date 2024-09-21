package com.example.zgzstreetmanager.service;

import com.example.zgzstreetmanager.model.Street;

public interface GoogleMapsService {
    public Street getStreet(String streetName);
}
