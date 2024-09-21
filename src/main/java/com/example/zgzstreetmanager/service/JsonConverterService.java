package com.example.zgzstreetmanager.service;

import com.example.zgzstreetmanager.model.Street;

public interface JsonConverterService {

    public Street convertJsonToStreet(String jsonData, Street street);
}
