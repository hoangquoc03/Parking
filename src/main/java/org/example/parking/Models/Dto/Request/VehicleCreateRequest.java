package org.example.parking.Models.Dto.Request;

import org.example.parking.Models.Enum.VehicleType;

public class VehicleCreateRequest {
    private String licensePlate;
    private String color;
    private VehicleType type;


    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
}
