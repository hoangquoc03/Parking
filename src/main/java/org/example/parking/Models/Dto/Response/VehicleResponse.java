package org.example.parking.Models.Dto.Response;

import org.example.parking.Models.Enum.VehicleType;

public interface VehicleResponse {
    Long getId();
    String getLicensePlate();
    String getColor();
    VehicleType getVehicleType();
}