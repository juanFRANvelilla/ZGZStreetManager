package com.example.zgzstreetmanager.service;

import com.example.zgzstreetmanager.model.DistrictEnum;

import java.awt.geom.Path2D;

public interface DistrictService {

    Path2D getDistrictPolygon(String districtName);

    boolean isWithinDistrict(String districtName, double latitude, double longitude);

    DistrictEnum getDistrictForCoordinates(double latitude, double longitude);
}
